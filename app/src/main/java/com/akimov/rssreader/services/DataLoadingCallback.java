package com.akimov.rssreader.services;

import com.akimov.rssreader.model.RssItem;

public interface DataLoadingCallback {
    void complete(boolean success);
}
