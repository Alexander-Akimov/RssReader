package com.akimov.rssreadermvp.data;

import android.content.Context;

import com.akimov.rssreadermvp.business.IMainRepository;
import com.akimov.rssreadermvp.business.models.RssChannel;
import com.akimov.rssreadermvp.business.models.RssPost;
import com.akimov.rssreadermvp.data.db.DataBaseStoreImpl;
import com.akimov.rssreadermvp.data.db.IDataBaseStore;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lex on 7/8/18.
 */
public class MainRepositoryImpl implements IMainRepository {

  IDataBaseStore dataBaseStore;

  @Inject
  public MainRepositoryImpl(Context context) {
    dataBaseStore = new DataBaseStoreImpl(context);

  }

  @Override
  public Single addChannel(RssChannel channel) {
    return dataBaseStore.insertChannel(channel)
        .subscribeOn(Schedulers.io());
  }

  @Override
  public Observable<List<RssChannel>> getChannels() {
    return dataBaseStore.getRssChannels();
  }

  @Override
  public Observable<List<RssPost>> getChannelItems(String id) {
    return null;
  }
}
