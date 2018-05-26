package com.akimov.rssreadermvp.di.application;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context appContext;

    public AppModule(Context context) {
        this.appContext = context;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return appContext;
    }
}
