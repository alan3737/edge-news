package com.edge_news.price_service.schedulers;
import com.edge_news.price_service.services.PriceService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PriceScheduler {
  
  PriceService priceService;

  @Scheduled(fixedRate = 60000) // Run every 60 seconds
  public void pullAndSaveTickerPriceData(){
    priceService.pullAndSaveTickerPriceData();
  }

}
