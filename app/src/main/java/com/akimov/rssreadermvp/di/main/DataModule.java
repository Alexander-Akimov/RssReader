package com.akimov.rssreadermvp.di.main;

import com.akimov.rssreadermvp.database.ItemsRepository;
import com.akimov.rssreadermvp.database.ItemsRepositoryImpl;
import com.akimov.rssreadermvp.di.main.ChannelListScope;

import dagger.Binds;
import dagger.Module;

@Module
public interface DataModule {

    @ChannelListScope
    @Binds
    ItemsRepository repository(ItemsRepositoryImpl repository);
}
