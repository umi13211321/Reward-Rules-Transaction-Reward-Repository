package com.umesh.demo.service;

import com.umesh.demo.entity.RewardRules;
import com.umesh.demo.repository.RewardRulesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RewardRulesServiceImpl implements RewardRulesService{

    @Autowired
    RewardRulesRepository rewardRulesRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<RewardRules> findAllRewardRules() {
        return rewardRulesRepository.findAll();
    }

//    @Override
//    public RewardRules findRewardRule(String cardProgramme) {
//        return null;
//    }

    @Override
    public RewardRules addRewardRule(RewardRules rewardRules) {
        return rewardRulesRepository.save(rewardRules);
    }

    @Override
    public void deleteRewardRule(int rewardRuleId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("reward_rule_id").is(rewardRuleId));
        RewardRules rewardRule = mongoTemplate.findOne(query, RewardRules.class);
        rewardRulesRepository.delete(rewardRule);
    }
}
