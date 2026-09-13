package com.edge_news.event_service.services;

import org.springframework.stereotype.Service;
import com.edge_news.event_service.repositories.MediaAttachmentRepository;
import com.edge_news.event_service.entities.MediaAttachment;

@Service
public class MediaAttachmentService {
  
  private final MediaAttachmentRepository mediaAttachmentRepository;

    public MediaAttachmentService(MediaAttachmentRepository mediaAttachmentRepository) {
        this.mediaAttachmentRepository = mediaAttachmentRepository;
    }

    public void saveMediaAttachment(MediaAttachment mediaAttachment) {
        mediaAttachmentRepository.save(mediaAttachment);
    }
}
