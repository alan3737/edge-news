package com.econ.edge_news.repository;

import com.econ.edge_news.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long>{
    public boolean existsByBillTitle(String billTitle);
    public void deleteByBillTitle(String billTitle);
    public Bill findByBillTitle(String billTitle);
}
