package com.akimov.rssreader.adapters;


import com.akimov.rssreadermvp.business.models.RssPost;

public interface ItemViewClick {
    void handleClick(RssPost rssItem);
}