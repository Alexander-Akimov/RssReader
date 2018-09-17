package com.akimov.rssreadermvp.presentation.main.presenter;

import com.akimov.rssreadermvp.business.models.RssChannel;
import com.akimov.rssreadermvp.presentation.main.view.IMainView;

public interface IMainPresenter {

  void setView(IMainView view);

  void onDestroy();

  void initialize();

  void addChannelClicked();

  void editChannelClicked(int position);

  void deleteChannelClicked();

  void deleteChannel(RssChannel channel);

  void updateChannel(RssChannel channel);

  void addChannel(RssChannel channel);

  void channelSelected(RssChannel channel);
}
