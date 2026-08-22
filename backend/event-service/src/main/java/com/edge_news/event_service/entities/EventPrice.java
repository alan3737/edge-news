package com.edge_news.event_service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class EventPrice {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long eventPriceId;

  @ManyToOne
  @JoinColumn(name = "eventId", nullable = false)
  private Event event;

  private String ticker;

  private double startPrice;
  private double currentPrice;

  public EventPrice() {}

  public EventPrice(Event event, double startPrice, double currentPrice, String ticker) {
    this.event = event;
    this.startPrice = startPrice;
    this.currentPrice = currentPrice;
    this.ticker = ticker;
  }

  public Long getEventPriceId() {
    return eventPriceId;
  }

  public Event getEvent() {
    return event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }

  public double getStartPrice() {
    return startPrice;
  }

  public void setStartPrice(double startPrice) {
    this.startPrice = startPrice;
  }

  public double getCurrentPrice() {
    return currentPrice;
  }

  public void setCurrentPrice(double currentPrice) {
    this.currentPrice = currentPrice;
  }

  public String getTicker(){
    return ticker;
  }

  public void setTicker(String ticker){
    this.ticker = ticker;
  }
}
