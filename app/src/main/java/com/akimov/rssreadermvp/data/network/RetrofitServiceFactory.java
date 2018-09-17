package com.akimov.rssreadermvp.data.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by lex on 9/8/18.
 */
public class RetrofitServiceFactory {

  private static String apiBaseUrl = "https://www.yahoo.com/news/rss/politics/";



  private Retrofit retrofit = new Retrofit.Builder()

      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(SimpleXmlConverterFactory.create())
      .baseUrl(apiBaseUrl)
      .build();

  public RssAPI create() {
    return retrofit.create(RssAPI.class);
  }

}
