package com.edge_news.price_service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.OffsetDateTime;

@Entity
public class Price {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long priceId;
  private String ticker;
  private double price;
  private OffsetDateTime time;

  public Price() {}

  public Price(String ticker, double price, OffsetDateTime time) {
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
