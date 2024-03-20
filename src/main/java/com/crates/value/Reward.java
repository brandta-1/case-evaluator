package com.crates.value;

import jakarta.persistence.*;

@Entity
public class Reward {

    private @Id
    @GeneratedValue(strategy= GenerationType.AUTO) Long id;
    private String name;
    private String rarity;
    private double odds;
    private double price;
    private @ManyToOne Container container;

    public Reward(String name, String rarity, double odds, double price, Container container) {
        this.name = name;
        this.rarity = rarity;
        this.odds = odds;
        this.container = container;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Container getContainer(){
        return container;
    }

    public void setContainer(Container container){
        this.container = container;
    }

    @Override
    public String toString() {
        return "Reward{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rarity='" + rarity + '\'' +
                ", odds='" + odds + '\'' +
                ", price='" + price + '\'' +
                ", container='" + container +
                '}';
    }

}
