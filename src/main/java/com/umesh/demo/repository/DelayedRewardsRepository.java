package com.umesh.demo.repository;

import com.umesh.demo.entity.DelayedReward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DelayedRewardsRepository extends JpaRepository<DelayedReward, Integer> {

}

