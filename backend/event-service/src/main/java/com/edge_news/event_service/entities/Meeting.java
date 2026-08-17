package com.edge_news.event_service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Meeting {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long meetingId;

  @OneToOne
  @JoinColumn(name = "eventId", unique = true, nullable = false)
  private Event event;

  private String linkToMeeting;
  private String description;

  public Meeting() {}

  public Meeting(Event event, String linkToMeeting, String description) {
    this.event = event;
    this.linkToMeeting = linkToMeeting;
    this.description = description;
  }

  public Long getMeetingId() {
    return meetingId;
  }

  public Event getEvent() {
    return event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }

  public String getLinkToMeeting() {
    return linkToMeeting;
  }

  public void setLinkToMeeting(String linkToMeeting) {
    this.linkToMeeting = linkToMeeting;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}

