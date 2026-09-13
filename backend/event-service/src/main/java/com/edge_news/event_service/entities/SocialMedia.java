package com.edge_news.event_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class SocialMedia {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long socialMediaId;

  @Column(unique = true)
  private String eventMessageId;

  @OneToOne
  @JoinColumn(name = "eventId", unique = true, nullable = false)
  private Event event;

  private String linkToMessage;
  @Column(columnDefinition = "TEXT")
  private String message;
  private String author;
  private String cardTitle;
  private String cardUrl;

  public SocialMedia() {}

  public SocialMedia(Event event, String eventMessageId, String linkToMessage, String message, String author, String cardTitle, String cardUrl) {
    this.event = event;
    this.eventMessageId = eventMessageId;
    this.linkToMessage = linkToMessage;
    this.message = message;
    this.author = author;
    this.cardTitle = cardTitle;
    this.cardUrl = cardUrl;
  }

  public Long getSocialMediaId() {
    return socialMediaId;
  }

  public Event getEvent() {
    return event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }

  public String getEventMessageId() {
    return eventMessageId;
  }

  public void setEventMessageId(String eventMessageId) {
    this.eventMessageId = eventMessageId;
  }

  public String getlinkToMessage() {
    return linkToMessage;
  }

  public void setlinkToMessage(String linkToMessage) {
    this.linkToMessage = linkToMessage;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getAuthor(){
    return author;
  }

  public void setAuthor(String author){
    this.author = author;
  }

  public String getCardTitle() {
    return cardTitle;
  }

  public void setCardTitle(String cardTitle) {
    this.cardTitle = cardTitle;
  }

  public String getCardUrl() {
    return cardUrl;
  }

  public void setCardUrl(String cardUrl) {
    this.cardUrl = cardUrl;
  }
}


