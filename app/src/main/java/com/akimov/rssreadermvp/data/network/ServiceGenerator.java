package com.akimov.rssreadermvp.data.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by lex on 9/7/18.
 */
public class ServiceGenerator {

  private static String apiBaseUrl =  "https://www.yahoo.com/news/rss/politics/";


  //private static OkHttpClient client = new OkHttpClient.Builder().build();


  private static Retrofit retrofit = new Retrofit.Builder()
      // .client(client)
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(SimpleXmlConverterFactory.create())
      .baseUrl(apiBaseUrl).build();

  public static void changeApiBaseUrl(String newApiBaseUrl) {
    apiBaseUrl = newApiBaseUrl;

    retrofit = new Retrofit.Builder()
        // .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .baseUrl(apiBaseUrl).build();
  }

  public static <S> S createService(Class<S> serviceClass) {
    return retrofit.create(serviceClass);
  }
}
