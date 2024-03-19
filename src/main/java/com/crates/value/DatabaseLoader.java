package com.crates.value;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import org.json.simple.JSONArray;
import org.json.simple.parser.*;

import java.io.BufferedReader;
import java.io.FileReader;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

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

        //init wears
        Wear[] wears = new Wear[5];
        wears[0] = new Wear("Factory New", 0.07);
        wears[1] = new Wear("Minimal Wear", 0.08);
        wears[2] = new Wear("Field-Tested", 0.22);
        wears[3] = new Wear("Well-Worn", 0.07);
        wears[4] = new Wear("Battle-Scarred", 0.56);

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



            //if it has wear, add the wear conditions

            //for (Wear wear : wears) {

                String getPrice
                        = String.format(
                        "https://steamcommunity.com/market/priceoverview/" +
                                "?appid=730&currency=1&market_hash_name=%1$s (%2$s)",
                        testName.replace("|","%7C"),
                        wears[1].getName());
                getPrice = getPrice.replace(" ", "%20");
                System.out.println(getPrice);


            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .header("accept", "application/json")
                    .uri(URI.create(getPrice))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("made it here before ");
            System.out.println(response);
            System.out.println(response.body());
            System.out.println("made it here after ");
                //make this a URL, then when you get a response, if(success) -> if(lowest_price)
            //}

            /*
            for(Object rewardObj: rewards){
                JSONObject reward = (JSONObject) rewardObj;
                System.out.println(reward.get("name"));

            }
            */
        }
    }
}
