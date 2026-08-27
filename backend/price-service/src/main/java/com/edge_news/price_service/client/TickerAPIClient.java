package com.edge_news.price_service.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import com.edge_news.price_service.dto.TickerPriceData;
import com.edge_news.price_service.entities.Price;

@Component
public class TickerAPIClient {

  private final RestClient restClient;
  private final String finnhubApiKey;

  public TickerAPIClient() {
    this.restClient = RestClient.builder().build();
    this.finnhubApiKey = System.getenv("FINNHUB_API_KEY");
  }

  public Price getTickerPriceData(String ticker){
    String url = "https://finnhub.io/api/v1/quote?symbol=" + ticker + "&token=" + finnhubApiKey;

    try{
      TickerPriceData tickerPriceData = restClient.get().uri(url).retrieve().body(TickerPriceData.class);
      OffsetDateTime timestamp = Instant.ofEpochSecond(tickerPriceData.getT())
        .atOffset(ZoneOffset.UTC)
        .withSecond(0)
        .withNano(0);
  
      return new Price(ticker, tickerPriceData.getC(), timestamp);
    }
    catch (Exception e) {
      throw new RuntimeException("Error fetching ticker price data for symbol: " + ticker, e);
    }
  }
  
  public Price[] getTickerPriceDataBatch(String[] tickers) {
    Price[] prices = new Price[tickers.length];
    for (int i = 0; i < tickers.length; i++) {
      prices[i] = getTickerPriceData(tickers[i]);
    }
    return prices;
  }
}