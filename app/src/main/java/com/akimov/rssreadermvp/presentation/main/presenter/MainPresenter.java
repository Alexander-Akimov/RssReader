package com.akimov.rssreadermvp.presentation.main.presenter;

import com.akimov.rssreadermvp.business.IMainInteractor;
import com.akimov.rssreadermvp.business.MainInteractor;
import com.akimov.rssreadermvp.business.models.RssChannel;
import com.akimov.rssreadermvp.business.models.RssPost;
import com.akimov.rssreadermvp.presentation.main.view.IMainView;


import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class MainPresenter implements IMainPresenter {

  private IMainView IMainView;

  @Inject
  public IMainInteractor mainInteractor;

  @Inject
  public MainPresenter() {

  }

  public void setView(IMainView view) {
    this.IMainView = view;
  }

  public void onDestroy() {
    this.IMainView = null;
  }

  public void addChannelClicked() {
    this.IMainView.showAddChannelDialog();
  }

  @Override
  public void editChannelClicked(int position) {
    this.IMainView.showEditChannelDialog(position);
  }

  @Override
  public void deleteChannelClicked() {

  }

  @Override
  public void deleteChannel(RssChannel channel) {
    // mRssChannelService.deleteChannel(channel.getId());
  }

  @Override
  public void updateChannel(RssChannel channel) {

  }

  @Override
  public void addChannel(RssChannel channel) {
    // mRssChannelService.addChannel(, this.onReloadChannels);
    Disposable getQuotesSubscription = mainInteractor.addChannel(channel)
        .subscribe(this::handleSuccessAddChannel, this::handleErrorAddChannel);

  }

  private void handleErrorAddChannel(Object o) {
  }

  private void handleSuccessAddChannel(Object o) {
  }

  public void initialize() {
    this.loadData();
  }

  private void loadData() {
    this.getChannelList();
    this.getItemsList();
  }

  private void hideViewLoading() {
    this.IMainView.hideLoading();
  }

  private void showViewLoading() {
    this.IMainView.showLoading();
  }

  private void showChannelsInView(ArrayList<RssChannel> channelList) {
    this.IMainView.renderChannelList(channelList);
  }

  private void showChannelItemsInView(ArrayList<RssPost> itemsList) {
    this.IMainView.renderChannelItems(itemsList);

  }

  private void getChannelList() { // TODO: USE CASE
//    ArrayList<RssChannel> channelsList = mRssChannelService.getChannels();
//    this.showChannelsInView(channelsList);
  }

  private void getItemsList() /*{}*/ {
    this.showViewLoading();
//    mRssChannelService.getChannelItems((boolean success) -> {
//          if (success) {
//            this.showChannelItemsInView(mRssChannelService.getChannelItems());
//
//          }
//          this.hideViewLoading();
//        }
//    );
  }


}
