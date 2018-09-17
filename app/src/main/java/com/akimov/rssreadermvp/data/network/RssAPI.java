package com.akimov.rssreadermvp.data.network;

import com.akimov.rssreadermvp.data.network.model.Rss;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by lex on 9/7/18.
 */
public interface RssAPI {

  @GET
  Single<Rss> getFeed(@Url String url);
}
