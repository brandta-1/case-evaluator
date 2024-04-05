package com.crates.value;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;

public class RarityDenominators {

    public static Map<String, Integer> getRarityDenominators(JSONArray contains){

        int a = 0;
        int l = 0;
        int m = 0;
        int r = 0;

        for(Object rewardObj: contains){
           //TODO typecasting
           JSONObject reward = (JSONObject) rewardObj;
           JSONObject rarityJSON = (JSONObject) reward.get("rarity");


           String rarityId = rarityJSON.get("id").toString();
           String rarity = String.valueOf(rarityId.charAt(7));
           switch(rarity) {
               case "r":
                   r++;
                   break;
               case "m":
                   m++;
                   break;
               case "l":
                   l++;
                   break;
               case "a":
                   a++;
                   break;
           }
       }

        return new HashMap<>(Map.ofEntries(
                entry("l", l),
                entry("m",  m),
                entry("r",  r),
                entry("a",a)
        ));
    }
}
