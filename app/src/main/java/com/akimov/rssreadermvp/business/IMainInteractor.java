package com.akimov.rssreadermvp.business;

import com.akimov.rssreadermvp.business.models.RssChannel;
import com.akimov.rssreadermvp.business.models.RssPost;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by lex on 7/8/18.
 */
public interface IMainInteractor {
  Single<Long> addChannel(RssChannel rssChannel);
  Observable<List<RssChannel>> getChannels();
  Observable<List<RssPost>> getChannelItems(RssChannel channel);
}
