package com.econ.edge_news.entity;
import java.time.Instant;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Bills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long bill_id;

    String bill_title;
    String bill_content;
    String bill_url;
    Instant bill_date;
}
