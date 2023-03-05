package com.umesh.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="delayed_reward_6")
public class DelayedReward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int delayedRewardId;
    private int transactionId;
    private Timestamp transactionDateTime;
    private int rewardCondition;
    private Timestamp rewardIssueDateTime;
    private String merchantName;
    private Timestamp rewardExpirationDateTime;
    private String rewardType;
    private double rewardAmount;
    private String gift;
    private boolean isRewardValid;
    private boolean isRewardAwarded;
}
