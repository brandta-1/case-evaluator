package com.crates.value;

import org.springframework.stereotype.Component;

@Component
public class ContainerSimple {
    private String name;
    private String image;
    private Double price;
    private Double roi;

    public ContainerSimple(){};

    public ContainerSimple(String name, String image, Double price, Double roi) {
        this.name = name;
        this.image = image;
        this.price = price;
        //TODO better way to do this?
        this.roi = Double.parseDouble(String.format("%.4f", roi));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getRoi() {
        return roi;
    }

    public void setRoi(Double roi) {
        //TODO see above
        this.roi = Double.parseDouble(String.format("%.4f", roi));
    }
}
