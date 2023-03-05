package com.umesh.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "reward_rules_6")
public class RewardRules {
    @Id
    private BigInteger id;
    private int reward_rule_id;
    private String cardProgramme;
    private int mcc;
    private int amountThresold;
    private String preferredMerchantName;
    private String rewardType;
    private double rewardAmountPerchantage;
    private int rewardAmountFlat;
    private int rewardCalculationCriteria;
    private int maxRewardAmount;
    private String network;
    private Timestamp dateLowerLimit;
    private Timestamp dateUpperLimit;
    private boolean instantReward;
    private int daysForReward;
    private boolean sameMerchantReward;

    private int rewardExpirationDays;
}