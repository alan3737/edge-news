package com.econ.edge_news.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class MetaData {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long dataId;
  String billUpdateTime;
  public MetaData(){

  }

  public MetaData(Long dataId, String billUpdateTime){
    this.dataId = dataId;
    this.billUpdateTime = billUpdateTime;
  }
}
