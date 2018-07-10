package com.akimov.rssreadermvp.business.models;

import java.util.ArrayList;
import java.util.List;

public class RssChannel {
  private long mId;
  private String mLink;
  private String mTitle;
  private String mDescription;

  private List<RssPost> mChannelItems = new ArrayList<RssPost>();


  public RssChannel(long id, String link, String title, String description) {
    mId = id;
    mLink = link;
    mTitle = title;
    mDescription = description;
  }

  @Override
  public String toString() {
    return mTitle;
  }

  public boolean isValid() {
    return mTitle.isEmpty() && mDescription.isEmpty() && mLink.isEmpty();
  }

  public String getLink() {
    return mLink;
  }

  public String getTitle() {
    return mTitle;
  }

  public String getDescription() {
    return mDescription;
  }

  public long getId() {
    return mId;
  }

  public List<RssPost> getChannelItems() {
    return mChannelItems;
  }

  public void setId(long id) {
    mId = id;
  }

  public void setLink(String link) {
    mLink = link;
  }

  public void setTitle(String title) {
    mTitle = title;
  }

  public void setDescription(String description) {
    mDescription = description;
  }

  public void setChannelItems(List<RssPost> mChannelItems) {
    this.mChannelItems = mChannelItems;
  }
}