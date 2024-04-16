package com.crates.value;

import org.springframework.stereotype.Component;

@Component
public class Article {

    public Article(){};

    private String title;
    private String description;
    private String link;
    private String image;
    private int index;

    public Article(String title, String description, String link, String image, int index){
        this.title = title;
        this.description = description;
        this.link = link;
        this.image = image;
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
