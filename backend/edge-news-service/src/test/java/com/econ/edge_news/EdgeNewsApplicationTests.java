package com.econ.edge_news;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.econ.edge_news.entity.Bill;
import com.econ.edge_news.entity.MetaData;
import com.econ.edge_news.repository.BillRepository;
import com.econ.edge_news.repository.MetaDataRepository;
import com.econ.edge_news.service.AddDataService;

@SpringBootTest
class EdgeNewsApplicationTests {
	@Autowired
	private AddDataService addDataService;

	@Autowired
	private BillRepository billRepository;

	@Autowired
	private MetaDataRepository metaRepository;


	@Test
	void contextLoads() {
	}
	
	@Test
    public void testAddBills() throws Exception {
        addDataService.addBills();
        List<Bill> bills = billRepository.findAll();
        assertFalse(bills.isEmpty(), "Bills should have been added");
        MetaData meta = metaRepository.findById(1L).orElse(null);
        assertNotNull(meta, "MetaData should exist");
        System.out.println("Latest ISO timestamp: " + meta.getBillUpdateTime());
    }

}
