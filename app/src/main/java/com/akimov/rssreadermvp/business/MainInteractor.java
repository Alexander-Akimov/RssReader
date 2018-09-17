package com.akimov.rssreadermvp.business;

import android.content.Context;

import com.akimov.rssreadermvp.business.models.RssChannel;
import com.akimov.rssreadermvp.business.models.RssPost;
import com.akimov.rssreadermvp.data.MainRepositoryImpl;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by lex on 7/8/18.
 */
public class MainInteractor implements IMainInteractor {

  @Inject
  public IMainRepository mainRepository;

  @Inject
  public MainInteractor() {

  }

  @Override
  public Single<Long> addChannel(RssChannel rssChannel) {
    return mainRepository.addChannel(rssChannel)
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override
  public Observable<List<RssChannel>> getChannels() {
    return mainRepository.getChannels()
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override
  public Observable<List<RssPost>> getChannelItems(RssChannel channel) {
    return mainRepository.getChannelItems(channel)
        .observeOn(AndroidSchedulers.mainThread());
  }

}
