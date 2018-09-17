package com.akimov.rssreadermvp.data.db.storio;

import android.content.Context;
import android.support.annotation.NonNull;

import com.akimov.rssreadermvp.business.models.RssChannel;
import com.akimov.rssreadermvp.data.db.DBHelper;
import com.pushtorefresh.storio3.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio3.sqlite.StorIOSQLite;
import com.pushtorefresh.storio3.sqlite.impl.DefaultStorIOSQLite;
import com.pushtorefresh.storio3.sqlite.operations.delete.DefaultDeleteResolver;
import com.pushtorefresh.storio3.sqlite.operations.delete.DeleteResolver;
import com.pushtorefresh.storio3.sqlite.queries.DeleteQuery;

public class StorIOFactory {
  private static StorIOSQLite INSTANCE;

  public synchronized static StorIOSQLite get(Context context) {
    if (INSTANCE != null) {
      return INSTANCE;
    }
    INSTANCE = DefaultStorIOSQLite.builder()
        .sqliteOpenHelper(new DBHelper(context))
        .addTypeMapping(RssChannel.class, SQLiteTypeMapping.<RssChannel>builder()
            .putResolver(new RssChannelPutResolver())
            .getResolver(new RssChannelGetResolver())
            .deleteResolver(new RssChannelDeleteResolver())//createDeleteResolver()
            .build())
      /*  .addTypeMapping(RssPost.class, SQLiteTypeMapping.<RssPost>builder()
            .putResolver()
            .getResolver()
            .deleteResolver()
            .build())*/
        .build();
    return INSTANCE;
  }

  private static DeleteResolver<RssChannel> createDeleteResolver() {

    return new DefaultDeleteResolver<RssChannel>() {
      @NonNull
      @Override
      protected DeleteQuery mapToDeleteQuery(@NonNull RssChannel object) {
        return null;
      }
    };
  }




}
