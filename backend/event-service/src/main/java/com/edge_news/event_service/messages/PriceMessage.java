package com.edge_news.event_service.messages;

import java.time.OffsetDateTime;

public class PriceMessage {
  private Long priceId;
  private String ticker;
  private double price;
  private OffsetDateTime time;

  public PriceMessage() {}

  public PriceMessage(Long priceId, String ticker, double price, OffsetDateTime time) {
    this.priceId = priceId;
    this.ticker = ticker;
    this.price = price;
    this.time = time;
  }

  public Long getPriceId() {
    return priceId;
  }

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public OffsetDateTime getTime() {
    return time;
  }

  public void setTime(OffsetDateTime time) {
    this.time = time;
  }
}
