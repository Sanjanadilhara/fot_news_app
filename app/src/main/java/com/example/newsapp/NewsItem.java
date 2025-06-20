package com.example.newsapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class NewsItem {
    public String id;
    public String title;
    public String date;
    public String image;
    public String description;

    // Empty constructor (needed for Firebase)
    public NewsItem() {}

    public NewsItem(String title, String date, String image, String description) {
        this.title = title;
        this.date = date;
        this.image = image;
        this.description = description;
        this.id="";
    }


    public String getTimeAgo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            Date past = sdf.parse(this.date);
            Date now = new Date();

            long seconds = TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
            long minutes = TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
            long hours = TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
            long days = TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());

            if (seconds < 60) {
                return "just now";
            } else if (minutes < 60) {
                return minutes + " minutes ago";
            } else if (hours < 24) {
                return hours + " hours ago";
            } else if (days < 7) {
                return days + " days ago";
            } else if (days < 30) {
                long weeks = days / 7;
                return weeks + " weeks ago";
            } else if (days < 365) {
                long months = days / 30;
                return months + " months ago";
            } else {
                long years = days / 365;
                return years + " years ago";
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

}
