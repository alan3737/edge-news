package com.econ.edge_news.repository;

import com.econ.edge_news.entity.MetaData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetaDataRepository extends JpaRepository<MetaData, Long>{
    
}
