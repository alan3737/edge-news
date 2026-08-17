package com.edge_news.event_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edge_news.event_service.entities.EconomicNews;

public interface EconomicNewsRepository extends JpaRepository<EconomicNews, Long>{
  
}
