package com.edge_news.price_service.services;

import org.springframework.stereotype.Service;
import com.edge_news.price_service.repositories.PriceRepository; 
import com.edge_news.price_service.client.TickerAPIClient;
import com.edge_news.price_service.entities.Price;
import java.util.List;
import java.util.ArrayList;

@Service
public class PriceService {
  private final PriceRepository priceRepository;
  private final String[] tickers;
  private final TickerAPIClient tickerAPIClient;

  public PriceService(PriceRepository priceRepository) {
    this.priceRepository = priceRepository;
    this.tickers = new String[]{"DIA", "SPY", "QQQ"};
    this.tickerAPIClient = new TickerAPIClient();
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
    }
    catch (Exception e) {
      throw new RuntimeException("Error pulling and saving ticker price data", e);
    }
  }
}
