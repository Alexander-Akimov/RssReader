package com.akimov.rssreadermvp.model;

public class RssItemModel {
    public String itemId;
    public String title;
    public String description;
    public String link;
    public String channelId;

    public RssItemModel(String title, String description, String link, String channelId) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.channelId = channelId;
    }
}
