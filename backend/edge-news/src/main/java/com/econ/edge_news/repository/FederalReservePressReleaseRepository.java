package com.econ.edge_news.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.econ.edge_news.entity.FederalReservePressRelease;

public interface FederalReservePressReleaseRepository extends JpaRepository<FederalReservePressRelease, Long>{
  public FederalReservePressRelease findByTitle(String title);
}
