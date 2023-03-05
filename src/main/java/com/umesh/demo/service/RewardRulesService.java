package com.umesh.demo.service;

import com.umesh.demo.entity.RewardRules;

import java.util.List;

public interface RewardRulesService {
    public List<RewardRules> findAllRewardRules();
//    public RewardRules findRewardRule(String cardProgramme);
    public RewardRules addRewardRule(RewardRules rewardRules);
    public void deleteRewardRule(int rewardRuleId);
}

