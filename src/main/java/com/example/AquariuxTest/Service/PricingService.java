package com.example.AquariuxTest.Service;

import com.example.AquariuxTest.Entity.Ticker;
import com.example.AquariuxTest.Repositories.TickerRepo;
import com.example.AquariuxTest.Response.BinanceResponse;
import com.example.AquariuxTest.Response.HuobiFullResponse;
import com.example.AquariuxTest.Response.HuobiResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.example.AquariuxTest.Constants.Constants.BTC_TICKER;
import static com.example.AquariuxTest.Constants.Constants.ETH_TICKER;

@Service
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class PricingService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private  TickerRepo tickerRepo;

    public void retrieveBinancePricing() {
        String url = "https://api.binance.com/api/v3/ticker/bookTicker";
        ResponseEntity<List<BinanceResponse>> response = restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        if (response.getBody() != null) {
            for (BinanceResponse ticker : response.getBody()) {
                if (ticker.getSymbol().equals(ETH_TICKER) || ticker.getSymbol().equals(BTC_TICKER)) {
                    storeBestPricing(ticker.getSymbol(), ticker.getBidPrice(), ticker.getBidQty(), ticker.getAskPrice(), ticker.getAskQty());
                }
            }
        } else {
            log.info("Null retrieved from binance api url. Please check to confirm.");
        }

    }

    public void storeBestPricing(String symbol, BigDecimal bidPrice, BigDecimal bidQty, BigDecimal askPrice, BigDecimal askQty) {
        Ticker ticker = new Ticker();
        ticker.setSymbol(symbol);
        ticker.setBidPrice(bidPrice);
        ticker.setBidQty(bidQty);
        ticker.setAskPrice(askPrice);
        ticker.setAskQty(askQty);

        Optional<Ticker> current = tickerRepo.findById(symbol);
        if (current.isEmpty()) {
            tickerRepo.save(ticker);
        } else {
            // Bid Price used for SELL order, Ask Price use for BUY order
            // so we want the minimum bid price because when a sell order is placed -> the seller is selling at bid price
            // and so we want maximum ask price because when a buy order is placed -> the buyer is buying at buy price
            if (current.get().getBidPrice().compareTo(ticker.getBidPrice()) < 0 ) {
                ticker.setBidPrice(current.get().getBidPrice());
                ticker.setBidQty(current.get().getBidQty());
            }

            if (current.get().getAskPrice().compareTo(ticker.getAskPrice()) > 0 ) {
                ticker.setAskPrice(current.get().getAskPrice());
                ticker.setAskQty(current.get().getAskQty());
            }
            tickerRepo.save(ticker);
        }
        log.info("Ticker with symbol {}, bidPrice {}, bidQty {}, askPrice {}, askQty {} has been saved/updated.",
                symbol, bidPrice.toString(), bidQty.toString(), askPrice.toString(), askQty.toString());
    }

    public void retrieveHuobiPricing() {
        String url = "https://api.huobi.pro/market/tickers";
        ResponseEntity<HuobiFullResponse> response = restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                HuobiFullResponse.class);
        if (response.getBody() != null && response.getBody().getData() != null) {
            for (HuobiResponse ticker : response.getBody().getData()) {
                if (ticker.getSymbol().equals(ETH_TICKER) || ticker.getSymbol().equals(BTC_TICKER)) {
                    storeBestPricing(ticker.getSymbol(), ticker.getBid(), ticker.getBidSize(), ticker.getAsk(), ticker.getAskSize());
                }
            }
        } else {
            log.info("Null retrieved from huobi api url. Please check to confirm.");

        }
    }

}
