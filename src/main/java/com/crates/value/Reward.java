package com.crates.value;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Reward {
    private @Id @GeneratedValue(strategy=GenerationType.AUTO) Long id;
    private String name;
    private String url;
    private String rarity;
    private double odds;
    private double[] wearOdds;
    private String[] prices;


    public Reward(String name, String url, String rarity, double odds, double[]wearOdds, String[] prices) {
        this.name = name;
        this.rarity = rarity;
        this.url = url;
        this.odds = odds;
        this.wearOdds = wearOdds;
        this.prices = prices;
    }

    public Reward() {

    };
    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity){
        this.rarity = rarity;
    }

    public double getOdds(){
        return odds;
    }

    public void setOdds(double odds){
        this.odds = odds;
    }

    public String[] getPrices() {
        return prices;
    }

    public void setPrices(String[] prices) {
        this.prices = prices;
    }

    @Override
    public String toString() {
        return "Reward{" +
                ", name='" + name + '\'' +
                ", rarity='" + rarity + '\'' +
                ", odds='" + odds + '\'' +
                ", prices='" + prices + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double[] getWearOdds() {
        return wearOdds;
    }

    public void setWearOdds(double[] wearOdds) {
        this.wearOdds = wearOdds;
    }
}
