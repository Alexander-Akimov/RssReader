package com.akimov.rssreadermvp.database;

import com.akimov.rssreadermvp.model.ChannelModel;
import com.akimov.rssreadermvp.model.RssItemModel;

import java.util.ArrayList;

public interface ItemsRepository {

    ArrayList<ChannelModel> getChannels();

    void insertChannelItems(ArrayList<RssItemModel> rssItems, String channelId);

    ArrayList<RssItemModel> getChannelItems(String id);
}
