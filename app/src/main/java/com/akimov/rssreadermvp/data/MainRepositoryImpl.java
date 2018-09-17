package com.akimov.rssreadermvp.data;

import android.content.Context;
import android.util.Log;

import com.akimov.rssreadermvp.business.IMainRepository;
import com.akimov.rssreadermvp.business.models.RssChannel;
import com.akimov.rssreadermvp.business.models.RssPost;
import com.akimov.rssreadermvp.data.db.DBStoreImpl;
import com.akimov.rssreadermvp.data.db.IDBStore;
import com.akimov.rssreadermvp.data.network.RetrofitServiceFactory;
import com.akimov.rssreadermvp.data.network.RssAPI;
import com.akimov.rssreadermvp.data.network.ServiceGenerator;
import com.akimov.rssreadermvp.data.network.model.Item;
import com.akimov.rssreadermvp.data.network.model.Rss;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lex on 7/8/18.
 */
public class MainRepositoryImpl implements IMainRepository {

  public static final String TAG = MainRepositoryImpl.class.getSimpleName();

  IDBStore dataBaseStore;
  RssAPI rssAPI;

  @Inject
  public MainRepositoryImpl(Context context) {
    dataBaseStore = new DBStoreImpl(context);
    //rssAPI = new RetrofitServiceFactory().create();
    rssAPI = ServiceGenerator.createService(RssAPI.class);

  }

  @Override
  public Single<Long> addChannel(RssChannel channel) {
    return dataBaseStore.insertChannel(channel)
        .subscribeOn(Schedulers.io());
  }

  @Override
  public Observable<List<RssChannel>> getChannels() {//get channels from db
    return dataBaseStore.getRssChannels()
        .subscribeOn(Schedulers.io());
  }

  @Override
  public Observable<List<RssPost>> getChannelItems(RssChannel channel) {//get data from db or url

    String channelURL = channel.getLink();

    //ServiceGenerator.changeApiBaseUrl("https://www.svoboda.org/api/zogqpoegmopo");
    // String apiBaseUrl = "https://www.svoboda.org/api/zogqpoegmopo";
    // String apiBaseUrl = "https://echo.msk.ru/programs/brother/rss-audio.xml";
    // String apiBaseUrl = "https://www.yahoo.com/news/rss/politics";

    return rssAPI.getFeed(channelURL) // похоже что не очень хороший способ
        .subscribeOn(Schedulers.io())
        .map(item -> item.channel.getItems())
        .flatMap(list -> Observable.fromIterable(list)
            .map(Mapper::toRssPost)
            .toList())
        .toObservable();
  }
}
