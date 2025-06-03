package com.example.newsapp;

public class NewsItem {
    public String headline;
    public String addedTime;
    public String imageUrl;

    public NewsItem(String headline, String addedTime, String imageUrl) {
        this.headline = headline;
        this.addedTime = addedTime;
        this.imageUrl = imageUrl;
    }
}
