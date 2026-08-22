package com.edge_news.event_service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class EconomicNews {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long economicNewsId;

  @OneToOne
  @JoinColumn(name = "eventId", unique = true, nullable = false)
  private Event event;

  private String data;
  private String description;

  public EconomicNews() {}

  public EconomicNews(Event event, String data, String description) {
    this.event = event;
    this.data = data;
    this.description = description;
  }

  public Long getEconomicNewsId() {
    return economicNewsId;
  }

  public Event getEvent() {
    return event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
