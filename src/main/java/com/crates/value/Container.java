package com.crates.value;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Arrays;

@Entity
public class Container {

    private @Id @GeneratedValue(strategy=GenerationType.AUTO) Long id;
    private String name;
    private String url;
    private String containerType;
    private double price;
    private String image;



    @OneToMany
    private  Reward[] rewards;

    private double roi;

    public Container(String name, String url, String image, String containerType, double price, Reward[] rewards) {
        this.name = name;
        this.url = url;
        this.image = image;
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
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public void setRoi(){
        double sum = 0.0;
        for( Reward reward : this.rewards){
            sum += reward.getValue();
        }
        double denom = (this.price + 2.5);
        System.out.println("HERE IS SUM: "+sum+" HERE IS PRICE+2.5 "+denom);
        this.roi = sum / (this.price + 2.5);
    }

    public double getRoi(){
        return roi;
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
