package com.akimov.rssreadermvp.data.db.storio;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.akimov.rssreadermvp.data.db.RssChannelCursorWrapper;
import com.akimov.rssreadermvp.business.models.RssChannel;
import com.pushtorefresh.storio3.sqlite.StorIOSQLite;
import com.pushtorefresh.storio3.sqlite.operations.get.DefaultGetResolver;


/**
 * Created by lex on 6/9/18.
 */
public class RssChannelGetResolver extends DefaultGetResolver<RssChannel> {

  @NonNull
  @Override
  public RssChannel mapFromCursor(@NonNull StorIOSQLite storIOSQLite, @NonNull Cursor cursor) {

    return new RssChannelCursorWrapper(cursor).getChannel();
  }

}
