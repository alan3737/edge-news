package com.edge_news.event_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edge_news.event_service.entities.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
  
}
