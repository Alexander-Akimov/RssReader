package com.akimov.rssreadermvp.data.db;

import android.content.Context;

import com.akimov.rssreadermvp.business.models.RssChannel;
import com.akimov.rssreadermvp.data.db.storio.StorIOFactory;
import com.pushtorefresh.storio3.sqlite.queries.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by lex on 6/9/18.
 */
public class DataBaseStoreImpl implements IDataBaseStore {

  private final Context context;

  public DataBaseStoreImpl(Context context) {
    this.context = context;
  }

  @Override
  public Single insertChannel(RssChannel rssChannel) {
    return StorIOFactory.get(this.context)
        .put()
        .object(rssChannel)
        .prepare()
        .asRxSingle();//executeAsBlocking()
  }

  @Override
  public Completable deleteChannel(RssChannel rssChannel) {
    return StorIOFactory.get(this.context)
        .delete()
        .object(rssChannel)
        .prepare()
        .asRxCompletable();
  }

  @Override
  public Observable<List<RssChannel>> getRssChannels() {
    return StorIOFactory.get(this.context)
        .get()
        .listOfObjects(RssChannel.class)
        .withQuery(Query.builder()
            .table(RssChannelTable.TABLE_NAME)
            //.orderBy("")
            .limit(50)
            .build())
        .prepare()
        .asRxSingle()
        .toObservable();
  }

  public Single insertData(List<RssChannel> rssChannelList) {
    return StorIOFactory.get(this.context)
        .put()
        .objects(rssChannelList)
        .prepare()
        .asRxSingle();//executeAsBlocking()
  }
}
