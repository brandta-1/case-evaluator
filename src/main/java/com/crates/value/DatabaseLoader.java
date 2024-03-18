package com.crates.value;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import org.json.simple.JSONArray;
import org.json.simple.parser.*;
import java.io.FileReader;

@Component
public class DatabaseLoader implements CommandLineRunner {
    private final ContainerRepository containers;
    private final RewardRepository rewards;

    @Autowired
    public DatabaseLoader(RewardRepository rewardRepository, ContainerRepository containerRepository) {
        this.rewards = rewardRepository;
        this.containers = containerRepository;
    }

    @Override
    public void run(String... strings) throws Exception {

        boolean dontRunThis = false;
        if(dontRunThis){
            return;
        }

        JSONArray crates = (JSONArray) new JSONParser().parse(
                new FileReader("src/main/resources/containersShort.json"));

        for(Object crateObj: crates){
            JSONObject crate = (JSONObject) crateObj;

            JSONArray rewards = (JSONArray) crate.get("contains");
            String crateName = crate.get("name").toString();
            System.out.println(crateName + "------------------------------------------------------------");

            //unreleased as of 3.18.24
            if(crateName.contains("Copenhagen 2024")){
                continue;
            }

            JSONObject firstReward = (JSONObject) rewards.getFirst();

            String testName = firstReward.get("name").toString();

            System.out.println(testName);
            //http://steamcommunity.com/market/priceoverview/?appid=730&currency=1&market_hash_name=testName
            String getPrice
                    = String.format("http://steamcommunity.com/market/priceoverview/?appid=730&currency=1&market_hash_name=%s", testName);


            /*
            for(Object rewardObj: rewards){
                JSONObject reward = (JSONObject) rewardObj;
                System.out.println(reward.get("name"));

            }
            */
        }
    }
}
