package com.edge_news.event_service.services;

import org.springframework.stereotype.Service;
import com.edge_news.event_service.repositories.SocialMediaRepository;
import com.edge_news.event_service.entities.SocialMedia;

@Service
public class SocialMediaService {
  
  private final SocialMediaRepository socialMediaRepository;

  public SocialMediaService(SocialMediaRepository socialMediaRepository) {
    this.socialMediaRepository = socialMediaRepository;
  }

  public void saveSocialMedia(SocialMedia socialMedia) {
    socialMediaRepository.save(socialMedia);
  }

}
