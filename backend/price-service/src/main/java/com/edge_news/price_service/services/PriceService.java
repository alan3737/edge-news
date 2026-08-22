package com.edge_news.price_service.services;

import org.springframework.stereotype.Service;
import com.edge_news.price_service.repositories.PriceRepository;

@Service
public class PriceService {
  private final PriceRepository priceRepository;

  public PriceService(PriceRepository priceRepository) {
    this.priceRepository = priceRepository;
  }
}
