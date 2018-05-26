package com.akimov.rssreadermvp.di.application;

import android.content.Context;

import com.akimov.rssreadermvp.di.application.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    Context context();
    //void inject();
}
