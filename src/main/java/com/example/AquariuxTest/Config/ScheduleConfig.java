package com.example.AquariuxTest.Config;

import com.example.AquariuxTest.Service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configuration
public class ScheduleConfig {
    @Autowired
    private PricingService pricingService;

    @Scheduled(fixedRate = 10000)
    public void fetchPricingData() {
        pricingService.retrieveBinancePricing();
        pricingService.retrieveHuobiPricing();
    }
}
