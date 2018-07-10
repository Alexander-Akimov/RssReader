package com.akimov.rssreadermvp.business.models;

public class RssPost {
  private Integer id;
  private String title;
  private String description;
  private String link;
  private Integer channelId;

  public RssPost(String title, String description, String link, Integer channelId) {
    this.title = title;
    this.link = link;
    this.description = description;
    this.channelId = channelId;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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

  public Integer getChannelId() {
    return channelId;
  }

  public void setChannelId(Integer channelId) {
    this.channelId = channelId;
  }
}
