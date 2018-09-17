package com.akimov.rssreadermvp.presentation.main.view;

import com.akimov.rssreadermvp.business.models.RssChannel;
import com.akimov.rssreadermvp.business.models.RssPost;

import java.util.ArrayList;
import java.util.List;

public interface IMainView {
  void selectChannel();

  void renderChannelList(List<RssChannel> channelsList);

  void renderChannelItems(List<RssPost> channelItemsList);

  void showLoading();

  void hideLoading();

  void showAddChannelDialog();

  void showEditChannelDialog(int position);

  void showErrorMessage(String message);
}
