package com.umesh.demo.repository;

import com.umesh.demo.entity.RewardRules;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardRulesRepository extends MongoRepository<RewardRules, Integer> {
}
