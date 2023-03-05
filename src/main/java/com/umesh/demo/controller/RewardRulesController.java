package com.umesh.demo.controller;

import com.umesh.demo.entity.RewardRules;
import com.umesh.demo.service.RewardRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rewardRules")
public class RewardRulesController {

    @Autowired
    RewardRulesService rewardRulesService;

//    @GetMapping("/{cardProgramme}")
//    public List<RewardRules> findAllRewardRulesBasedOnCardProgramme(@PathVariable String cardProgramme){
//        return rewardRulesService.findAllRewardRulesBasedOnCardProgramme(cardProgramme);
//    }

    @GetMapping
    public List<RewardRules> findAllRewardRules(){
        System.out.println("***************inside contoller");
        return rewardRulesService.findAllRewardRules();
    }

    @PostMapping()
    public RewardRules addRewardRule(@RequestBody RewardRules rewardRules){
        return rewardRulesService.addRewardRule(rewardRules);
    }

    @DeleteMapping("/{rewardRuleId}")
    public String deleteRewardRule(@PathVariable int rewardRuleId){
        rewardRulesService.deleteRewardRule(rewardRuleId);
        return "Reward Rule is Deleted";
    }
}