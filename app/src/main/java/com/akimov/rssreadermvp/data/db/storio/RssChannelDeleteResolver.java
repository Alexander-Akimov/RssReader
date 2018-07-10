package com.akimov.rssreadermvp.data.db.storio;

import android.support.annotation.NonNull;

import com.akimov.rssreadermvp.business.models.RssChannel;
import com.akimov.rssreadermvp.data.db.RssChannelTable;
import com.pushtorefresh.storio3.sqlite.operations.delete.DefaultDeleteResolver;
import com.pushtorefresh.storio3.sqlite.queries.DeleteQuery;

/**
 * Created by lex on 7/10/18.
 */
public class RssChannelDeleteResolver extends DefaultDeleteResolver<RssChannel> {
  @NonNull
  @Override
  protected DeleteQuery mapToDeleteQuery(@NonNull RssChannel object) {
    return DeleteQuery.builder()
        .table(RssChannelTable.TABLE_NAME)
        .where(RssChannelTable._ID + "=?")
        .whereArgs(object.getId())
        .build();
  }
}
