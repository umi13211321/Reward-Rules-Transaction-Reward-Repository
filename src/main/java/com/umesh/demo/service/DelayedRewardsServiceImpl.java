package com.umesh.demo.service;

import com.umesh.demo.entity.DelayedReward;
import com.umesh.demo.repository.DelayedRewardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DelayedRewardsServiceImpl implements DelayedRewardsService{

    @Autowired
    DelayedRewardsRepository delayedRewardsRepository;

    @Override
    public DelayedReward addDelayedReward(DelayedReward delayedReward){
        return delayedRewardsRepository.save(delayedReward);
    }

    @Override
    public List<DelayedReward> findAllDelayedReward() {
        return delayedRewardsRepository.findAll();
    }
}