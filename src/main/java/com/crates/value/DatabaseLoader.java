package com.crates.value;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.*;

import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

import java.io.FileReader;

import java.math.BigDecimal;

@Component
public class DatabaseLoader implements CommandLineRunner {

    @Value("${example.host}")
    private String localhost;
    private final ContainerRepository containers;

    @Autowired
    public DatabaseLoader(ContainerRepository containerRepository) {

        this.containers = containerRepository;
    }

    @Override
    public void run(String... strings) throws Exception {

        //...
        boolean dontRunThis = false;
        if(dontRunThis){
            return;
        }

        //TODO ask
        JSONArray crates = (JSONArray) new JSONParser().parse(
                new FileReader("src/main/resources/cases.json"));

        PartitionWear partitionWear = new PartitionWear();
        RarityDenominators rarityDenominators = new RarityDenominators();
        RetrieveWebData retrieveWebData = new RetrieveWebData();

        Map<String, Double> rarityOdds = new HashMap<>(Map.ofEntries(
                entry("l", 0.032),
                entry("m",  0.1598),
                entry("r",  0.7992),
                entry("a",0.0064)
        ));

        //these are the float value definitions for weapon conditions, starting from factory new.
        //there is actually a 0.01 gap between the wears
        double[][] weaponWearBoundaries = {
                {0.0,0.07},
                {0.08,0.15},
                {0.16,0.37},
                {0.38,0.44},
                {0.45,1.0}
        };

        for(Object crateObj: crates){

            //TODO potentially fix this typecasting
            JSONObject crate = (JSONObject) crateObj;
            Container newContainer = new Container();
            //set new container in the db
            //TODO typecasting fix...
            newContainer.setName((String) crate.get("name"));
            newContainer.setUrl((String) crate.get("url"));
            newContainer.setType((String) crate.get("type"));

            BigDecimal containerPrice = retrieveWebData.getContainerPrice(newContainer.getUrl());
            newContainer.setPrice(containerPrice);

            JSONArray crateContains = (JSONArray) crate.get("contains");

            Map<String, Integer> denominators = rarityDenominators.getRarityDenominators(crateContains);

            Reward[] crateRewards = new Reward[crateContains.size()];

            int index = 0;
            for(Object rewardObj: crateContains) {
                JSONObject rewardJSON = (JSONObject) rewardObj;

                Reward reward = new Reward();

                //TODO same here, see above
                reward.setName((String) rewardJSON.get("name"));
                reward.setUrl((String) rewardJSON.get("url"));

                JSONObject rewardRarity = (JSONObject) rewardJSON.get("rarity");
                //todo this probably needs to be changed
                reward.setRarity(String.valueOf(rewardRarity.get("id").toString().charAt(7)));

                //the odds of an item are its rarity divided by how many of its rarity are in its container
                reward.setOdds(rarityOdds.get(reward.getRarity()) / denominators.get(reward.getRarity()));

                //the prices of an item are an array of its possible qualities, retrieved from the web
                String[] rewardPrices = retrieveWebData.getRewardPrice(reward.getUrl());
                reward.setPrices(rewardPrices);

                JSONArray floatCapsJSON = (JSONArray) rewardJSON.get("floatCaps");

                //better way to parse json than this? floatCaps in the original JSON is just something like: [0,0.8]
                double[] floatCaps = {Double.parseDouble(floatCapsJSON.getFirst().toString()),
                        Double.parseDouble(floatCapsJSON.getLast().toString())};

                int floatGaps = partitionWear.getFloatGaps(floatCaps);

                double[] wears = WearOdds.getWearOdds(weaponWearBoundaries, floatCaps, floatGaps);

                reward.setWearOdds(wears);

                //todo need to calculate the expected price of the reward

                crateRewards[index] = reward;
                index++;
            }
        }
    }
}
