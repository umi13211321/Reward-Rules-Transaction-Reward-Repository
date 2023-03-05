package com.umesh.demo.service;


import com.umesh.demo.entity.DelayedReward;
import com.umesh.demo.entity.RewardRules;
import com.umesh.demo.entity.Transaction;
import com.umesh.demo.repository.DelayedRewardsRepository;
import com.umesh.demo.repository.RewardRulesRepository;
import com.umesh.demo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final List<String> giftForPlatinumCard = Arrays.asList("3000", "Wireless Earphone", "Cricket Bat", "Sports Shoes");
    private static final int DAYS_FACTOR_FOR_REWARD_ISSUE = 0;
    private static final int DAYS_FACTOR_FOR_REWARD_EXPIRATION = 0;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    RewardRulesRepository rewardRulesRepository;
    @Autowired
    DelayedRewardsRepository delayedRewardsRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<Transaction> findAllTransaction() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction addTransaction(Transaction transaction) {
        String theCardProgramme = transaction.getCardProgramme();

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        double reward = 0;
        String giftReward = null;
        Query query = new Query();
        query.addCriteria(Criteria.where("cardProgramme").is(theCardProgramme));
        RewardRules rewardRule = mongoTemplate.findOne(query, RewardRules.class);

        int thresoldAMount = rewardRule.getAmountThresold();
        double transactionAmount = transaction.getAmount();
        int maximumReward = rewardRule.getMaxRewardAmount();
        Timestamp dateTimeLowerLimit = rewardRule.getDateLowerLimit();
        Timestamp dateTimeUpperLimit = rewardRule.getDateUpperLimit();

        if ((transactionAmount >= thresoldAMount) && ((currentTimestamp.after(dateTimeLowerLimit) && (currentTimestamp.before(dateTimeUpperLimit))))) {
            double rewardPercentage = rewardRule.getRewardAmountPerchantage();
            double percentageReward = (transactionAmount * rewardPercentage) / 100;
            int flatReward = rewardRule.getRewardAmountFlat();
            int rewardCondition = rewardRule.getRewardCalculationCriteria();


            switch (rewardCondition) {
                case 1:
                    //max of pecentage and flat
                    reward = Math.max(percentageReward, flatReward);
                    break;

                case 2:
                    //add percentage discount and flat discount
                    reward = percentageReward + flatReward;
                    break;


                case 4:
                    //Platinum Customer and amount >= 100000
                    Random random1 = new Random();
                    giftReward = giftForPlatinumCard.get(random1.nextInt(giftForPlatinumCard.size()));
                    break;

                default:
                    //percentage discount or flat discount
                    if (percentageReward == 0) {
                        reward = flatReward;
                    } else {
                        reward = percentageReward;
                    }
                    break;
            }
        } else {
            reward = 0;
        }

        //Check for Additional 2% if merchant is preferred
        String preferredMerchantName = rewardRule.getPreferredMerchantName();
        if(reward!=0 &&  transaction.getMerchantName().equals(preferredMerchantName)) {
            reward += (transactionAmount * 2) / 100;
        }

        //Check for Additional Package Reward of 5%
        if (transaction.isAdditionalPackage() && reward != 0) {
            reward += (transactionAmount * 5) / 100;
        } else if (transaction.isAdditionalPackage() && reward == 0 && transactionAmount >= 7000) {
            reward += 500.0;
        }

        if (reward > maximumReward && maximumReward != 0) {
            reward = maximumReward;
        }

        //Round Off reward amount to two decimals places
        DecimalFormat e = new DecimalFormat("#.##");
        reward = Double.parseDouble(e.format(reward));

        transaction.setRewardAmount(reward);
        transaction.setGift(giftReward);
        transaction.setInstantReward(rewardRule.isInstantReward());

        if (reward == 0 && giftReward == null) {
            transaction.setRewardType(null);
        } else {
            transaction.setRewardType(rewardRule.getRewardType());
        }

        Transaction savedTransaction = transactionRepository.save(transaction);

        //Saving Delayed Reward
        if (!rewardRule.isInstantReward()) {
            DelayedReward delayedReward = new DelayedReward();
            delayedReward.setTransactionId(savedTransaction.getTransactionId());
            delayedReward.setTransactionDateTime(savedTransaction.getTransactionDateTime());
            delayedReward.setMerchantName(savedTransaction.getMerchantName());
            delayedReward.setRewardType(savedTransaction.getRewardType());
            delayedReward.setRewardAmount(savedTransaction.getRewardAmount());
            delayedReward.setGift(savedTransaction.getGift());
            delayedReward.setRewardValid(true);
            delayedReward.setRewardAwarded(false);

            //Reward Condition
            int rewardCondition = 1;
            if (rewardRule.isSameMerchantReward()) {
                rewardCondition = 2;
            }
            delayedReward.setRewardCondition(rewardCondition);

            //Reward Issue Date
            Timestamp rewardIssueDateTime = savedTransaction.getTransactionDateTime();
            if (rewardCondition == 1) {
                rewardIssueDateTime = addDays(rewardIssueDateTime, rewardRule.getDaysForReward() + DAYS_FACTOR_FOR_REWARD_ISSUE);
            } else {
                rewardIssueDateTime = null;
            }
            delayedReward.setRewardIssueDateTime(rewardIssueDateTime);

            //Reward Expiration Date
            Timestamp rewardExpirationDateTime = savedTransaction.getTransactionDateTime();
            if (rewardCondition == 1) {
                rewardExpirationDateTime = null;
            } else {
                rewardExpirationDateTime = addDays(rewardExpirationDateTime, rewardRule.getRewardExpirationDays() + DAYS_FACTOR_FOR_REWARD_EXPIRATION);
            }
            delayedReward.setRewardExpirationDateTime(rewardExpirationDateTime);

            delayedRewardsRepository.save(delayedReward);
        }
        System.out.println("Applied Reward Rule: " + rewardRule.getReward_rule_id());
        return savedTransaction;
    }

    @Override
    public Transaction findTransactionById(int id) {
        return transactionRepository.findById(id).orElse(null);
    }

    public static Timestamp addDays(Timestamp date, int days) {
        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DATE, days);
        return new Timestamp(calendar2.getTime().getTime());
    }
}
