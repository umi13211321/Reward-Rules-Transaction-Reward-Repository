package com.umesh.demo.controller;

import com.umesh.demo.entity.DelayedReward;
import com.umesh.demo.service.DelayedRewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/delayedRewards")
public class DelayedRewardController {
    @Autowired
    DelayedRewardsService delayedRewardsService;

    @GetMapping
    public List<DelayedReward> findAllDelayedReward(){
        return delayedRewardsService.findAllDelayedReward();
    }
}
