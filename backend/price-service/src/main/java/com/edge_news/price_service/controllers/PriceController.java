package com.edge_news.price_service.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import com.edge_news.price_service.services.PriceService;
import java.time.OffsetDateTime;
import com.edge_news.price_service.entities.Price;
import java.util.List;

@RestController
public class PriceController {
  
  private final PriceService priceService;

  public PriceController(PriceService priceService) {
    this.priceService = priceService;
  }

  @GetMapping("/prices/latest")
  public List<Price> getLatestPriceForEveryTicker(){
    return priceService.getLatestPriceForEveryTicker();
  }

}
