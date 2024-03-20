package com.crates.value;

import jakarta.persistence.*;

import java.util.Arrays;

@Entity
public class Container {

    private @Id @GeneratedValue(strategy=GenerationType.AUTO) Long id;

    private String name;

    private String url;



    private String containerType;

    private double price;

    private @OneToMany Reward[] rewards;

    public Container(String name, String url, String containerType, double price, Reward[] rewards) {
        this.name = name;
        this.url = url;
        this.containerType = containerType;
        this.price = price;
        this.rewards = rewards;
    }

    public Container(){};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return containerType;
    }

    public void setType(String containerType){
        this.containerType = containerType;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public Reward[] getRewards(){
        return rewards;
    }

    public void setRewards(Reward[] rewards){
        this.rewards = rewards;
    }

    @Override
    public String toString() {
        return "Reward{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + containerType + '\'' +
                ", price='" + price + '\'' +
                ", rewards='" + Arrays.toString(rewards) +
                '}';
    }
}
