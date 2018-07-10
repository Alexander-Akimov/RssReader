package com.akimov.rssreadermvp.presentation.main.view;

import com.akimov.rssreadermvp.business.models.RssChannel;
import com.akimov.rssreadermvp.business.models.RssPost;

import java.util.ArrayList;

public interface IMainView {
  void selectChannel();

  void renderChannelList(ArrayList<RssChannel> channelsList);

  void renderChannelItems(ArrayList<RssPost> channelItemsList);

  void showLoading();

  void hideLoading();

  void showAddChannelDialog();

  void showEditChannelDialog(int position);
}
