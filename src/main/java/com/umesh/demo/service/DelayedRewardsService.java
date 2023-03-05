package com.umesh.demo.service;

import com.umesh.demo.entity.DelayedReward;

import java.util.List;

public interface DelayedRewardsService {
    public DelayedReward addDelayedReward(DelayedReward delayedReward);

    public List<DelayedReward> findAllDelayedReward();
}
