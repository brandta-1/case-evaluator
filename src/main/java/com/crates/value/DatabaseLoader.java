package com.crates.value;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.crates.value.CsData.*;

import static java.util.Map.entry;

import java.io.FileReader;
import java.math.BigDecimal;

import static com.crates.value.CsData.rarityOdds;
import static com.crates.value.CsData.weaponWearBoundaries;
import static com.crates.value.CsData.statTrakOdds;

@Component
public class DatabaseLoader implements CommandLineRunner {

    @Value("${example.host}")
    private String localhost;
    private final ContainerRepository containers;

    @Autowired
    public DatabaseLoader(ContainerRepository containerRepository) {

        this.containers = containerRepository;
    }

    private Reward generateRewards(Object rewardObj, Map<String, Integer> denominators, boolean gold) throws IOException {
        JSONObject rewardJSON = (JSONObject) rewardObj;
        // call with a third parameter boolean: gold, if true then check if its vanilla
        Reward reward = new Reward();

        //TODO same here, see above
        reward.setName((String) rewardJSON.get("name"));
        reward.setUrl((String) rewardJSON.get("url"));

        JSONObject rewardRarity = (JSONObject) rewardJSON.get("rarity");

        //todo this probably needs to be simplified
        //todo if its rare you need to check if its vanilla
        reward.setRarity(String.valueOf(rewardRarity.get("id").toString().charAt(7)));

        //the odds of an item are its rarity divided by how many of its rarity are in its container
        reward.setOdds( rarityOdds.get(reward.getRarity()) / denominators.get(reward.getRarity()) );

        //the prices of an item are an array of its possible qualities, retrieved from the web
        String[] rewardPrices = RetrieveWebData.getRewardPrice(reward.getUrl());
        reward.setPrices(rewardPrices);

        JSONArray floatCapsJSON = (JSONArray) rewardJSON.get("floatCaps");

        //better way to parse json than this? floatCaps in the original JSON is just something like: [0,0.8]
        double[] floatCaps = {Double.parseDouble(floatCapsJSON.getFirst().toString()),
                Double.parseDouble(floatCapsJSON.getLast().toString())};

        double[][] rewardFloatTable = TransformTable.transformTable(weaponWearBoundaries, floatCaps);

        //these are the odds of an items possible wears
        double[] wears = WearOdds.getWearOdds(weaponWearBoundaries, rewardFloatTable);

        reward.setWearOdds(wears);

        //todo need to calculate the expected price of the reward
        double rewardValue = CalculateValue.calculateRewardValue(reward.getWearOdds(), reward.getPrices(), statTrakOdds);

        //the expected value of a reward is its odds times the sum of its wear odds times their prices
        reward.setValue(rewardValue * reward.getOdds());
        System.out.println(reward);
        System.out.println("----------------------------------------------------");
        return reward;
    }

    @Override
    public void run(String... strings) throws Exception {


        //...
        boolean dontRunThis = false;
        if(dontRunThis){
            System.out.println("Not running dbloader");
            return;
        }

        //TODO ask
        JSONArray crates = (JSONArray) new JSONParser().parse(
                new FileReader("src/main/resources/cases.json"));


        //these are the float value definitions for weapon conditions, starting from factory new.
        //there is actually a 0.01 gap between the wears

        boolean stopLoop = false;
        for(Object crateObj: crates){

            if(stopLoop){
                break;
            }
            stopLoop = true;
            //TODO potentially fix this typecasting
            JSONObject crate = (JSONObject) crateObj;
            Container newContainer = new Container();
            //set new container in the db
            //TODO typecasting fix...
            newContainer.setName((String) crate.get("name"));
            newContainer.setUrl((String) crate.get("url"));
            newContainer.setType((String) crate.get("type"));

            BigDecimal containerPrice = RetrieveWebData.getContainerPrice(newContainer.getUrl());
            newContainer.setPrice(containerPrice);

            JSONArray crateContains = (JSONArray) crate.get("contains");
            JSONArray crateContainsRare = (JSONArray) crate.get("contains_rare");

            Map<String, Integer> CommonDenominators = RarityDenominators.getRarityDenominators(crateContains);
            Map<String, Integer> RareDenominator = Map.of("g",crateContainsRare.size());

            Reward[] crateRewards = new Reward[crateContains.size() + crateContainsRare.size()];

            int index = 0;
            for(Object rewardObj: crateContains) {
                crateRewards[index] = generateRewards(rewardObj, CommonDenominators, false);
                index++;
            }

            /*
            for(Object rewardObj: crateContainsRare) {
                crateRewards[index] = generateRewards(rewardObj, RareDenominator,true);
                index++;
            }
            */

        }
    }
}
