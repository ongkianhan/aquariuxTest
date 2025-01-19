package com.example.AquariuxTest.controller;


import com.example.AquariuxTest.Entity.*;
import com.example.AquariuxTest.Repositories.TickerRepo;
import com.example.AquariuxTest.Repositories.TransactionRepo;
import com.example.AquariuxTest.Repositories.WalletCurrencyRepo;
import com.example.AquariuxTest.Repositories.WalletRepo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import static com.example.AquariuxTest.Constants.Constants.*;


@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class ApiController {

    @Autowired
    private TransactionRepo txnRepo;

    @Autowired
    private WalletCurrencyRepo walletCurrencyRepo;

    @Autowired
    private WalletRepo walletRepo;

    @Autowired
    private TickerRepo tickerRepo;

    @GetMapping("/getTxnHistory")
    public ResponseEntity<Object> getTxnHistoryByUserId(@RequestParam Long id) {
        try {
            List<Transaction> txnHist = new ArrayList<>();
            txnHist.addAll(txnRepo.findByUserId(id));
            return ResponseEntity.ok().body(txnHist);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getWallet")
    public ResponseEntity<Object> getWalletByUserId(@RequestParam Long id) {
        try {
            Optional<Wallet> wallet = walletRepo.findById(id);
            return ResponseEntity.ok().body(wallet);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/getBestPrice")
    public ResponseEntity<Object> retrieveBestPrice() {
        try {
            List<Ticker> tickerList = new ArrayList<>();
            Optional<Ticker> eth = tickerRepo.findById("ETHUSDT");
            Optional<Ticker> btc = tickerRepo.findById("BTCUSDT");
            if (eth.isPresent()) {
                Ticker ethRec = eth.get();
                tickerList.add(ethRec);
            }

            if (btc.isPresent()) {
                Ticker btcRec = btc.get();
                tickerList.add(btcRec);
            }

            return ResponseEntity.ok(tickerList);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/tradeCurrency")
    @Transactional
    public ResponseEntity<Object> tradeCurrency(@RequestParam(required = false) BigDecimal tradeDollarAmt,
                                                @RequestParam(required = false) BigDecimal coinQty,
                                                @RequestParam String ticker,
                                                @RequestParam Long walletId,
                                                @RequestParam String transactionType) {
        try {
            if ((tradeDollarAmt == null && coinQty == null) || (tradeDollarAmt != null && coinQty != null)) {
                throw new IllegalArgumentException("You must provide exactly one of 'tradeDollarAmt' or 'coinQty'.");
            } else if (tradeDollarAmt != null) {
                if (tradeDollarAmt.compareTo(BigDecimal.ZERO) == 0) {
                    throw new IllegalArgumentException("You cannot trade 0 USD.");

                }
            } else {
                if (coinQty.compareTo(BigDecimal.ZERO) == 0) {
                    throw new IllegalArgumentException("You cannot trade 0 coins.");
                }
            }
            log.info("tradeDollarAmt {} coinQty {} transactionType {}", tradeDollarAmt, coinQty, transactionType);
            Optional<Wallet> walletOpt = walletRepo.findById(walletId);
            Wallet wallet = new Wallet();
            if (walletOpt.isPresent()) {
                wallet = walletOpt.get();
            }

            WalletCurr usdCurr = new WalletCurr();
            for (WalletCurr curr : wallet.getWalletCurrList()) {
                if (StringUtils.equals(curr.getTicker(), USD_TICKER)) usdCurr = curr ;
            }

            Optional<Ticker> toSearch= tickerRepo.findById(ticker);
            Ticker toTrade = new Ticker();
            if (toSearch.isPresent()) {
                toTrade = toSearch.get();
            }

            WalletCurr coinNewBalance =  new WalletCurr();
            for (WalletCurr curr : wallet.getWalletCurrList()) {
                if (StringUtils.equals(curr.getTicker(), ticker)) coinNewBalance = curr;
            }

            BigDecimal coinTransacted = BigDecimal.ZERO;

            if (StringUtils.equals(transactionType, TRANSACTION_TYPE_BUY)) {
                log.info("I am here");
                BigDecimal usdRequired;
                if (coinQty != null) {
                    usdRequired = toTrade.getBidPrice().multiply(coinQty);

                } else {
                    usdRequired = tradeDollarAmt;
                }


                if (usdCurr.getVolume().compareTo(usdRequired) < 0) {
                    throw new IllegalArgumentException(String.format("You do not have enough USD to purchase %s. Please top up your USD.",ticker));
                } else {
                    usdCurr.setVolume(usdCurr.getVolume().subtract(usdRequired));
                    walletCurrencyRepo.save(usdCurr);
                    if (coinQty != null) {
                        coinNewBalance.setVolume(coinQty.add(coinNewBalance.getVolume()));
                        coinTransacted = coinQty;
                    }
                    else {
                        coinTransacted = usdRequired.divide(toTrade.getBidPrice(), 10, RoundingMode.CEILING);
                        log.info("amount of new coin obtained {}", coinTransacted);
                        coinNewBalance.setVolume(coinNewBalance.getVolume().add(coinTransacted));
                    }
                    walletCurrencyRepo.save(coinNewBalance);
                    return ResponseEntity.ok().body(String.format("Transaction complete. %s %s transacted.", coinTransacted, ticker));

                }

            } else if (StringUtils.equals(transactionType, TRANSACTION_TYPE_SELL)) {
                if (coinQty == null) {
                    throw new IllegalArgumentException("Null coinQty provided. Please check again.");
                }
                else if (coinNewBalance.getVolume().compareTo(coinQty) < 0) {
                    throw new IllegalArgumentException(String.format("You do not have enough coins to sell. You currently have %s %s.", coinNewBalance.getVolume(),ticker));
                } else {
                    coinNewBalance.setVolume(coinNewBalance.getVolume().subtract(coinQty));
                    usdCurr.setVolume(coinQty.multiply(toTrade.getAskPrice()));
                    walletCurrencyRepo.save(usdCurr);
                }
                return ResponseEntity.ok().body(String.format("Transaction complete. %s %s transacted.", coinTransacted, ticker));
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




}
