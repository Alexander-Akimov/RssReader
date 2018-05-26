package com.akimov.rssreadermvp.di.main;


import com.akimov.rssreadermvp.services.RssLoader;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    @ChannelListScope
    @Provides
    public static RssLoader getRssLoader() {
        return new RssLoader();
    }
}
