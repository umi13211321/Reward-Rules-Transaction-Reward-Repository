package com.umesh.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="transaction_6")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;
    private String cardProgramme;
    private String card;
    private double amount;
    private int mcc;
    private String merchantName;
    private String merchantId;
    private String terminalId;
    private boolean additionalPackage;
    @CreationTimestamp
    private Timestamp transactionDateTime;
    private String rrn;
    private String network;
    private String rewardType;
    private double rewardAmount;
    private String gift;
    private boolean instantReward;
}