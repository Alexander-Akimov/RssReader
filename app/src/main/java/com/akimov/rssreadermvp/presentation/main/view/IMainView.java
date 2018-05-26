package com.akimov.rssreadermvp.presentation.main.view;

import com.akimov.rssreadermvp.model.ChannelModel;
import com.akimov.rssreadermvp.model.RssItemModel;

import java.util.ArrayList;

public interface IMainView {
    void selectChannel();

    void renderChannelList(ArrayList<ChannelModel> channelsList);

    void renderChannelItems(ArrayList<RssItemModel> channelItemsList);

    void showLoading();

    void hideLoading();

    void showAddChannelDialog();
}
