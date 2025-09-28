package com.econ.edge_news.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.econ.edge_news.repository.FederalReservePressReleaseRepository;
import org.springframework.web.bind.annotation.GetMapping;
import com.econ.edge_news.entity.FederalReservePressRelease;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/pressReleases")
public class FederalReservePressReleaseController {

  @Autowired
  private FederalReservePressReleaseRepository repository;

  @GetMapping
  public List<FederalReservePressRelease> getAllPressReleases(){
    return repository.findAll();
  }
  
  @PostMapping
  public void addPressRelease(@RequestBody FederalReservePressRelease pressRelease) {
      repository.save(pressRelease);
  }
  

}
