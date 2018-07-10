package com.akimov.rssreadermvp.business;

import android.content.Context;

import com.akimov.rssreadermvp.business.models.RssChannel;
import com.akimov.rssreadermvp.data.MainRepositoryImpl;

import javax.inject.Inject;

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
  public Single addChannel(RssChannel rssChannel) {
    return mainRepository.addChannel(rssChannel)
        .observeOn(AndroidSchedulers.mainThread());
  }
}
