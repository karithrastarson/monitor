package com.karithrastarson.monitor.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class NewsItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String headline;
    private String url;
    private long timestamp;

    public NewsItem() {
    }

    public NewsItem(String url, String headline) {
        this.url = url;
        this.headline = headline;
        this.timestamp = System.currentTimeMillis();
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
