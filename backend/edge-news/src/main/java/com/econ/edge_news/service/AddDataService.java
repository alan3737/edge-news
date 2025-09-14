package com.econ.edge_news.service;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.econ.edge_news.entity.Bill;
import com.econ.edge_news.entity.MetaData;
import com.econ.edge_news.repository.BillRepository;
import com.econ.edge_news.repository.MetaDataRepository;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;

@Service
public class AddDataService {
  @Autowired
  BillRepository billRepository;
  @Autowired
  MetaDataRepository metaRepository;

@Transactional
public void addBills() throws Exception{
    String fromDateTime = metaRepository.findById(1L).map(MetaData::getBillUpdateTime).orElse("2025-07-01T00:00:00Z");
    WebClient client = WebClient.create("https://api.congress.gov/v3/");
    JsonNode summariesResponse = client.get()
    .uri(uriBuilder -> uriBuilder
    .path("/summaries")
    .queryParam("fromDateTime", fromDateTime)
    .queryParam("toDateTime", DateTimeFormatter.ISO_INSTANT.format(Instant.now().truncatedTo(ChronoUnit.SECONDS)))
    .queryParam("sort", "updateDate asc")
    .queryParam("api_key", "H9aeiYm8eIe1I4C7j4kSe4rcusvEGWQnNhl88lq4")
    .build())
    .retrieve()
    .bodyToMono(JsonNode.class)
    .block();
    JsonNode summariesArray = summariesResponse.get("summaries");
    MetaData metaData = metaRepository.findById(1L).orElse(new MetaData());
    String latestDate = fromDateTime;
    for(JsonNode summary: summariesArray){
      String billEndpoint = summary.get("bill").get("url").asText();
      JsonNode billResponse = client.get()
      .uri(billEndpoint + "&api_key=H9aeiYm8eIe1I4C7j4kSe4rcusvEGWQnNhl88lq4")
      .retrieve()
      .bodyToMono(JsonNode.class)
      .block();
      String billTitle = summary.get("bill").get("title").asText();
      String billContent = summary.get("text").asText();
      String billUrl = billResponse.get("bill").get("legislationUrl").asText();
      String billDate = summary.get("updateDate").asText();
      String billDateOnly = billDate.split("T")[0];
      Bill repoBill = billRepository.findByBillTitle(billTitle)
      ;
      if(repoBill == null){
        billRepository.save(new Bill(billTitle, billContent, billUrl, billDateOnly));
      }
      else if(!repoBill.getBillContent().equals(billContent) || !repoBill.getBillUrl().equals(billUrl) || !repoBill.getBillDate().equals(billDateOnly) || !repoBill.getBillTitle().equals(billTitle)){
        repoBill.setBillContent(billContent);
        repoBill.setBillUrl(billUrl);
        repoBill.setBillDate(billDateOnly);
        repoBill.setBillTitle(billTitle);
        billRepository.save(repoBill);
      }
      latestDate = billDate.compareTo(latestDate) > 0 ? billDate : latestDate;
    }
    metaData.setBillUpdateTime(latestDate);
    metaRepository.save(metaData);
  }

  public void addTweets() throws Exception {

  }

  @Scheduled(fixedRate = 360000)
  public void addData() throws Exception{  
    addBills();
  }
}

