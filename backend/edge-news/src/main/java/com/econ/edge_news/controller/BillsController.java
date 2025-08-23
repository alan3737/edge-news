package com.econ.edge_news.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.econ.edge_news.entity.Bills;
import com.econ.edge_news.repository.BillsRepository;

@RestController
@RequestMapping("/bills")
public class BillsController {

    @Autowired
    private BillsRepository repository;

    @GetMapping
    public List<Bills> getAllBills(){
        return repository.findAll();
    }

    @PostMapping
    public void addBill(@RequestBody Bills bill){
        repository.save(bill);
    }
}
