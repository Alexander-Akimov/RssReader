package com.akimov.rssreader.model;

public class RssItem {
    public String itemId;
    public String title;
    public String description;
    public String link;
    public String channelId;

    public RssItem(String title, String description, String link, String channelId) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.channelId = channelId;
    }
}
