package com.edge_news.price_service.services;

import org.springframework.stereotype.Service;
import com.edge_news.price_service.repositories.PriceRepository; 
import com.edge_news.price_service.client.TickerAPIClient;
import com.edge_news.price_service.entities.Price;
import java.util.List;
import java.util.ArrayList;
import com.edge_news.price_service.publishers.KafkaPricePublisher;

@Service
public class PriceService {
  private final PriceRepository priceRepository;
  private final String[] tickers;
  private final TickerAPIClient tickerAPIClient;
  private final KafkaPricePublisher kafkaEventPublisher;

  public PriceService(PriceRepository priceRepository, TickerAPIClient tickerAPIClient, KafkaPricePublisher kafkaEventPublisher) {
    this.priceRepository = priceRepository;
    this.tickers = new String[]{"DIA", "SPY", "QQQ"};
    this.tickerAPIClient = tickerAPIClient;
    this.kafkaEventPublisher = kafkaEventPublisher;
  }

  public void savePrice(Price price) {
    priceRepository.save(price);
  }

  public void pullAndSaveTickerPriceData() {
    try {
      Price [] prices = tickerAPIClient.getTickerPriceDataBatch(tickers);
      List<Price> latestPrices = priceRepository.findLatestForEveryTicker();
      List<Price> newPrices = new ArrayList<>();
      for(Price price: prices){
        boolean isNewPrice = true;
        for(Price latestPrice: latestPrices){
          if(price.getTicker().equals(latestPrice.getTicker()) && price.getTime().isEqual(latestPrice.getTime())){
            isNewPrice = false;
            break;
          }
        }
        if(isNewPrice){
          savePrice(price);
          newPrices.add(price);
        }
      }
      if(!newPrices.isEmpty()){
        kafkaEventPublisher.publish(newPrices);
      }
    }
    catch (Exception e) {
      throw new RuntimeException("Error pulling and saving ticker price data", e);
    }
  }
}
