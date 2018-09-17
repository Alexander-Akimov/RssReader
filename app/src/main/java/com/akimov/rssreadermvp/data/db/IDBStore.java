package com.akimov.rssreadermvp.data.db;

import com.akimov.rssreadermvp.business.models.RssChannel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by lex on 6/9/18.
 */
public interface IDBStore {
  Single<Long> insertChannel(RssChannel rssChannel);

  Completable deleteChannel(RssChannel rssChannel);

  Observable<List<RssChannel>> getRssChannels();

}
