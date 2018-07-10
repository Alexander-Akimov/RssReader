package com.akimov.rssreadermvp.di.main;

import com.akimov.rssreadermvp.di.application.AppComponent;
import com.akimov.rssreadermvp.presentation.main.view.MainActivity;

import dagger.Component;

@ChannelListScope
@Component(dependencies = {AppComponent.class}, modules = {ActivityModule.class, DataModule.class, MainModule.class})
public interface RssReaderComponent {
    void inject(MainActivity mainActivity);
}
