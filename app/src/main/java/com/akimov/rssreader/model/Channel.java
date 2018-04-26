package com.akimov.rssreader.model;

import java.util.UUID;

public class Channel {
    private String mId;
    private String mLink;
    private String mTitle;
    private String mDescription;

    public Channel(String id, String link, String title, String description) {
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

    public String getId() {
        return mId;
    }


    public void setId(String id) {
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
}
