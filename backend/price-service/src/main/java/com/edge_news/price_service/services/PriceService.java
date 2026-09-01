package com.edge_news.price_service.services;

import org.springframework.stereotype.Service;
import com.edge_news.price_service.repositories.PriceRepository; 
import com.edge_news.price_service.client.TickerAPIClient;
import com.edge_news.price_service.entities.Price;
import com.edge_news.price_service.producers.KafkaPriceProducer;
import com.edge_news.price_service.messages.PriceMessage;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class PriceService {
  private final PriceRepository priceRepository;
  private final String[] tickers;
  private final TickerAPIClient tickerAPIClient;
  private final KafkaPriceProducer kafkaPriceProducer;

  public PriceService(PriceRepository priceRepository, TickerAPIClient tickerAPIClient, KafkaPriceProducer kafkaPriceProducer) {
    this.priceRepository = priceRepository;
    this.tickers = new String[]{"DIA", "SPY", "QQQ"};
    this.tickerAPIClient = tickerAPIClient;
    this.kafkaPriceProducer = kafkaPriceProducer;
  }

  public void savePrice(Price price) {
    priceRepository.save(price);
  }

  public void pullAndSaveTickerPriceData() {
    try {
      Price [] prices = tickerAPIClient.getTickerPriceDataBatch(tickers);
      List<Price> latestPrices = priceRepository.findLatestForEveryTicker();
      List<PriceMessage> newPrices = new ArrayList<>();
      Map<String, Price> latestPriceMap = new HashMap<>();

      for(Price price : latestPrices) {
        latestPriceMap.put(price.getTicker(), price);
      }
      
      for(Price price: prices){

        boolean isNewPrice = true;
        Price latestPrice = latestPriceMap.get(price.getTicker());

        if(latestPrice != null && latestPrice.getTime().isEqual(price.getTime())){
            isNewPrice = false;
        }
        if(isNewPrice){
          savePrice(price);
          PriceMessage priceMessage = new PriceMessage(price.getPriceId(), price.getTicker(), price.getPrice(), price.getTime());
          newPrices.add(priceMessage);
        }
      }
      if(!newPrices.isEmpty()){
        kafkaPriceProducer.publish(newPrices);
      }
    }
    catch (Exception e) {
      throw new RuntimeException("Error pulling and saving ticker price data", e);
    }
  }
}
