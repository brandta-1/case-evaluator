package com.crates.value;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.HashMap;

@Component
public class DatabaseLoader implements CommandLineRunner {

    @Value("${example.host}")
    private String localhost;
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



        //init wears
        HashMap<String, double[]> wears = new HashMap<String, double[]>();

        wears.put("Factory New", new double[]{0,0.7});
        wears.put("Minimal Wear", new double[]{0.7,0.15});
        wears.put("Field-Tested", new double[]{0.15,0.37});
        wears.put("Well-Worn", new double[]{0.37,0.44});
        wears.put("Battle-Scarred", new double[]{0.44,1});



        //TODO ask
        JSONArray crates = (JSONArray) new JSONParser().parse(
                new FileReader("src/main/resources/cases.json"));

        // String nameParam = URLEncoder.encode(name, StandardCharsets.UTF_8);

        for(Object crateObj: crates){
            JSONObject crate = (JSONObject) crateObj;

            Container newContainer = new Container();

            newContainer.setName((String) crate.get("name"));
            newContainer.setUrl((String) crate.get("url"));
            newContainer.setType((String) crate.get("type"));

            JSONArray crateContains = (JSONArray) crate.get("contains");

            for(Object rewardObj: crateContains) {
                JSONObject reward = (JSONObject) rewardObj;

                Reward newReward = new Reward();

                newReward.setName((String) reward.get("name"));
                System.out.println(newReward.getName());
            }
        }


        /*
        for(Object crateObj: crates){
            JSONObject crate = (JSONObject) crateObj;

            String crateName = crate.get("name").toString();

            String linebreak = "-".repeat(90);
            System.out.println("\n" + linebreak + "\n" );
            System.out.println(crate);


            //http://steamcommunity.com/market/priceoverview/?appid=730&currency=1&market_hash_name=testName



            //if it has wear, add the wear conditions

            /*
            for (Wear wear : wears) {

                String getPrice
                        = String.format(
                        "https://steamcommunity.com/market/priceoverview/" +
                                "?appid=730&currency=1&market_hash_name=%1$s (%2$s)",
                        testName.replace("|","%7C"),
                        wear.getName());
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
                System.out.println(response.statusCode());
                System.out.println(response.body());
                System.out.println("made it here after ");

                ObjectMapper mapper = new ObjectMapper();

                SteamResponse steamResponse = mapper.readValue(response.body(), new TypeReference<SteamResponse>() {
                });

                System.out.println(steamResponse.getLowest_price());

                //rate limit of 3 per minute
                Thread.sleep(20000);
                //make this a URL, then when you get a response, if(success) -> if(lowest_price)

            }
            */


        //}


    }
}
