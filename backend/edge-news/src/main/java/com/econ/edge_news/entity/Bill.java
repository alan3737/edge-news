package com.econ.edge_news.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long billId;
    String billTitle;
    @Column(columnDefinition = "TEXT")
    String billContent;
    String billUrl;
    String billDate;

    public Bill(){

    }

    public Bill(String billTitle, String billContent, String billUrl, String billDate) {
       this.billTitle = billTitle;
       this.billContent = billContent;
       this.billUrl = billUrl;
       this.billDate = billDate;
    }
}
