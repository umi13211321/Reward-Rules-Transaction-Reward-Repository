package com.umesh.demo.repository;

import com.umesh.demo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

//    @Query(value="SELECT COUNT(transaction_id) FROM transaction_5",nativeQuery = true)
//    public int totalTransaction();
}
