package com.akimov.rssreadermvp.presentation.main.view;

import com.akimov.rssreadermvp.business.models.RssPost;

public interface ItemViewClick {
    void handleClick(RssPost rssItem);
}