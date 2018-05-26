package com.akimov.rssreadermvp.presentation.main.presenter;

import com.akimov.rssreader.model.Channel;
import com.akimov.rssreadermvp.presentation.main.view.IMainView;

public interface IMainPresenter {

    void setView(IMainView view);

    void onDestroy();

    void initialize();

    void addChannelClicked();

    void addChannel(Channel channel);
}
