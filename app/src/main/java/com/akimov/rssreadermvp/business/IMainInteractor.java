package com.akimov.rssreadermvp.business;

import com.akimov.rssreadermvp.business.models.RssChannel;

import io.reactivex.Single;

/**
 * Created by lex on 7/8/18.
 */
public interface IMainInteractor {
  Single addChannel(RssChannel rssChannel);
}
