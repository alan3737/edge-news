package com.econ.edge_news.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.econ.edge_news.entity.Bill;
import com.econ.edge_news.repository.BillRepository;

@RestController
@RequestMapping("/bills")
public class BillController {

    @Autowired
    private BillRepository repository;

    @GetMapping
    public List<Bill> getAllBills(){
        return repository.findAll();
    }

    @PostMapping
    public void addBill(@RequestBody Bill bill){
        repository.save(bill);
    }
}
