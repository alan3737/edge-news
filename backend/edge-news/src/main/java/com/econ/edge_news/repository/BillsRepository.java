package com.econ.edge_news.repository;

import com.econ.edge_news.entity.Bills;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillsRepository extends JpaRepository<Bills, Long>{
    
}
