package com.akimov.rssreadermvp.di.main;

import com.akimov.rssreadermvp.business.IMainRepository;
import com.akimov.rssreadermvp.data.MainRepositoryImpl;
import com.akimov.rssreadermvp.services.RssLoader;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public interface DataModule {

  @ChannelListScope
  @Binds
  IMainRepository repository(MainRepositoryImpl repository);
}
