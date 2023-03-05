package com.umesh.demo.service;

import com.umesh.demo.entity.Transaction;

import java.util.List;

public interface TransactionService {

    public List<Transaction> findAllTransaction();

    public Transaction addTransaction(Transaction transaction);

    public Transaction findTransactionById(int id);
}
