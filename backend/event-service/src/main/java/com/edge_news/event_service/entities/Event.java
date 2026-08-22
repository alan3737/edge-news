package com.edge_news.event_service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.time.OffsetDateTime;

@Entity
public class Event {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long eventId;

  @OneToOne(mappedBy = "event")
  private EconomicNews economicNews;

  @OneToOne(mappedBy = "event")
  private Meeting meeting;

  @OneToOne(mappedBy = "event")
  private SocialMedia socialMedia;

  private String title;
  private String source;
  private OffsetDateTime pubDateTime;
  private String aiSummary;
  private String status;
  private String sourceType;

  public Event() {}   

  public Event(String title, String source, OffsetDateTime pubDateTime, String aiSummary, String status, String sourceType) {
    this.title = title;
    this.source = source;
    this.pubDateTime = pubDateTime;
    this.aiSummary = aiSummary;
    this.status = status;
    this.sourceType = sourceType;
  }

  public Long getEventId() {
    return eventId;
  }

  public EconomicNews getEconomicNews() {
    return economicNews;
  }

  public void setEconomicNews(EconomicNews economicNews) {
    this.economicNews = economicNews;
  }

  public Meeting getMeeting() {
    return meeting;
  }

  public void setMeeting(Meeting meeting) {
    this.meeting = meeting;
  }

  public SocialMedia getSocialMedia() {
    return socialMedia;
  }

  public void setSocialMedia(SocialMedia socialMedia) {
    this.socialMedia = socialMedia;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }             

  public OffsetDateTime getPubDateTime() {
    return pubDateTime;
  }

  public void setTime(OffsetDateTime pubDateTime) {
    this.pubDateTime = pubDateTime;
  }

  public String getAiSummary() {
    return aiSummary;
  }

  public void setAiSummary(String aiSummary) {
    this.aiSummary = aiSummary;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getSourceType() {
    return sourceType;
  }

  public void setSourceType(String sourceType) {
    this.sourceType = sourceType;
  }
}

