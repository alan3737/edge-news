package com.edge_news.event_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edge_news.event_service.entities.SocialMedia;

public interface SocialMediaRepository extends JpaRepository<SocialMedia, Long>{
  
}
