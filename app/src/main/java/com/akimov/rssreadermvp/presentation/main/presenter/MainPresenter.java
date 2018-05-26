package com.akimov.rssreadermvp.presentation.main.presenter;

import com.akimov.rssreader.model.Channel;
import com.akimov.rssreadermvp.model.ChannelModel;
import com.akimov.rssreadermvp.model.RssItemModel;
import com.akimov.rssreadermvp.services.RssChannelService;
import com.akimov.rssreadermvp.presentation.main.view.IMainView;


import java.util.ArrayList;

import javax.inject.Inject;

import dagger.Binds;

public class MainPresenter implements IMainPresenter {

    private IMainView IMainView;

    @Inject
    public RssChannelService mRssChannelService;

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
    public void addChannel(Channel channel) {
       // mRssChannelService.addChannel(, this.onReloadChannels);


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

    private void showChannelsInView(ArrayList<ChannelModel> channelList) {
        this.IMainView.renderChannelList(channelList);
    }

    private void showChannelItemsInView(ArrayList<RssItemModel> itemsList) {
        this.IMainView.renderChannelItems(itemsList);

    }

    private void getChannelList() { // TODO: USE CASE
        ArrayList<ChannelModel> channelsList = mRssChannelService.getChannels();
        this.showChannelsInView(channelsList);
    }

    private void getItemsList() /*{}*/ {
        this.showViewLoading();
        mRssChannelService.getChannelItems((boolean success) -> {
                    if (success) {
                        this.showChannelItemsInView(mRssChannelService.getChannelItems());

                    }
                    this.hideViewLoading();
                }
        );
    }


}
