package com.edge_news.event_service.entities;

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

  @OneToOne
  @JoinColumn(name = "eventId", unique = true, nullable = false)
  private Event event;

  private String linkToMessage;
  private String message;
  private String author;

  public SocialMedia() {}

  public SocialMedia(Event event, String linkToMessage, String message, String author) {
    this.event = event;
    this.linkToMessage = linkToMessage;
    this.message = message;
    this.author = author;
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
}


