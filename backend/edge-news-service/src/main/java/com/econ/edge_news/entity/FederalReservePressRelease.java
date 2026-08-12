package com.econ.edge_news.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class FederalReservePressRelease {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long pressReleaseId;
  String title;
  String date;
  String link;
  public FederalReservePressRelease(){

  }
  public FederalReservePressRelease(String title, String date, String link){
    this.title = title;
    this.date = date;
    this.link = link;
  }
}
