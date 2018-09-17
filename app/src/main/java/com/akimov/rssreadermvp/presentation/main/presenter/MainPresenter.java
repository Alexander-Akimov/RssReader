package com.akimov.rssreadermvp.presentation.main.presenter;

import android.util.Log;

import com.akimov.rssreadermvp.business.IMainInteractor;
import com.akimov.rssreadermvp.business.MainInteractor;
import com.akimov.rssreadermvp.business.models.RssChannel;
import com.akimov.rssreadermvp.business.models.RssPost;
import com.akimov.rssreadermvp.presentation.main.view.IMainView;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class MainPresenter implements IMainPresenter {

  public static final String TAG = MainPresenter.class.getSimpleName();

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
    Disposable getAddChannelSubscription = mainInteractor.addChannel(channel)
        .subscribe(this::handleSuccessAddChannel, this::handleErrorAddChannel);

  }

  @Override
  public void channelSelected(RssChannel channel) {
    getItemsList(channel);
  }

  private void handleSuccessAddChannel(Long aLong) {
  }


  private void handleErrorAddChannel(Object o) {
  }

  public void initialize() {
    this.loadData();
  }

  private void loadData() {
    this.getChannelList();
  }

  private void hideViewLoading() {
    this.IMainView.hideLoading();
  }

  private void showViewLoading() {
    this.IMainView.showLoading();
  }

  private void showChannelsInView(List<RssChannel> channelList) {
    this.IMainView.renderChannelList(channelList);
  }

  private void showErrorMessage(String message) {
    this.IMainView.showErrorMessage(message);
  }


  private void showChannelPosts(List<RssPost> itemsList) {
    this.IMainView.renderChannelItems(itemsList);
  }

  private void getChannelList() {
    Disposable getChannelsSubscription = mainInteractor.getChannels()
        .subscribe(this::successGetChannels, this::errorGetChannels);
  }

  private void successGetChannels(List<RssChannel> rssChannelList) {
    this.showChannelsInView(rssChannelList);

    if (rssChannelList.size() > 0) {
      RssChannel channel = rssChannelList.get(0);
      getItemsList(channel);
    }
  }

  private void errorGetChannels(Throwable throwable) {
    Log.d(TAG, throwable.getMessage());
    showErrorMessage("Some Error occurred");
  }

  private void getItemsList(RssChannel channel) {
    this.showViewLoading();
    Disposable subscription = mainInteractor.getChannelItems(channel)
        .subscribe(this::successGetPosts, this::errorGetPosts);
  }

  private void successGetPosts(List<RssPost> rssPosts) {
    this.showChannelPosts(rssPosts);
    this.hideViewLoading();
  }

  private void errorGetPosts(Throwable throwable) {
    this.hideViewLoading();
    Log.d(TAG, throwable.getMessage());
    showErrorMessage("Some Error occurred");
  }
}
