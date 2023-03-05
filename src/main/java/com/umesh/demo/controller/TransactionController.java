package com.umesh.demo.controller;

import com.umesh.demo.entity.Transaction;
import com.umesh.demo.service.DelayedRewardsService;
import com.umesh.demo.service.RewardRulesService;
import com.umesh.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    RewardRulesService rewardRulesService;
    @Autowired
    DelayedRewardsService delayedRewardsService;

    @GetMapping()
    public List<Transaction> getAllTransaction() {
        return transactionService.findAllTransaction();
    }

    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable int id){
        return transactionService.findTransactionById(id);
    }

    @PostMapping()
    public Transaction addTransaction(@RequestBody Transaction transaction){
        return transactionService.addTransaction(transaction);
    }
}
