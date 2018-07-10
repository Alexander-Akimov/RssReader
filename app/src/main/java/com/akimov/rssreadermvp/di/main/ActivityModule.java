package com.akimov.rssreadermvp.di.main;


import android.app.Activity;
import android.content.Context;

import com.akimov.rssreadermvp.services.RssLoader;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
  private final Activity _context;

  public ActivityModule(Activity activity) {
    this._context = activity;
  }

  @ChannelListScope
  @Provides
  Activity context() {
    return this._context;
  }

}
