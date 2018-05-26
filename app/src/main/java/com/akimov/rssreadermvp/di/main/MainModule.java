package com.akimov.rssreadermvp.di.main;

import com.akimov.rssreadermvp.presentation.main.presenter.IMainPresenter;
import com.akimov.rssreadermvp.presentation.main.presenter.MainPresenter;

import dagger.Binds;
import dagger.Module;

@Module
public interface MainModule {

    @ChannelListScope
    @Binds
    IMainPresenter provideMainPresenter(MainPresenter mainPresenter);

}
