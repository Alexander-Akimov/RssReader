package com.akimov.rssreader.database;

import com.akimov.rssreadermvp.business.models.RssChannel;
import com.akimov.rssreadermvp.business.models.RssPost;

import java.util.List;

public interface ItemsRepository {

    List<RssChannel> getChannels();

    void insertChannelItems(List<RssPost> rssItems, long channelId);

    List<RssPost> getChannelItems(long id);
}
