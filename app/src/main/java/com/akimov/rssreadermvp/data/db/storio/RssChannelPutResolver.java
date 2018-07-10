package com.akimov.rssreadermvp.data.db.storio;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.akimov.rssreadermvp.business.models.RssChannel;
import com.akimov.rssreadermvp.data.db.RssChannelTable;
import com.pushtorefresh.storio3.sqlite.operations.put.DefaultPutResolver;
import com.pushtorefresh.storio3.sqlite.queries.InsertQuery;
import com.pushtorefresh.storio3.sqlite.queries.UpdateQuery;

public class RssChannelPutResolver extends DefaultPutResolver<RssChannel> {

  @NonNull
  @Override
  protected InsertQuery mapToInsertQuery(@NonNull RssChannel entity) {
    return InsertQuery.builder()
        .table(RssChannelTable.TABLE_NAME)
        .build();
  }

  @NonNull
  @Override
  protected UpdateQuery mapToUpdateQuery(@NonNull RssChannel entity) {
    return UpdateQuery.builder()
        .table(RssChannelTable.TABLE_NAME)
        .where(RssChannelTable._ID + "= ?")
        .whereArgs(entity.getId())
        .build();
  }

  @NonNull
  @Override
  protected ContentValues mapToContentValues(@NonNull RssChannel entity) {
    final ContentValues contentValues = new ContentValues();
    contentValues.put(RssChannelTable._ID, entity.getId());
    contentValues.put(RssChannelTable.TITLE, entity.getTitle());
    contentValues.put(RssChannelTable.DESCRIPTION, entity.getDescription());
    contentValues.put(RssChannelTable.LINK, entity.getLink());

    return contentValues;
  }

}
