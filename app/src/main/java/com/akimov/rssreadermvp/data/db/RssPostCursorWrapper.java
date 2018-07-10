package com.akimov.rssreadermvp.data.db;

import android.database.Cursor;
import android.database.CursorWrapper;


import com.akimov.rssreadermvp.business.models.RssPost;

public class RssPostCursorWrapper extends CursorWrapper {
  public RssPostCursorWrapper(Cursor cursor) {
    super(cursor);
  }

  public RssPost getChannel() {
    String idString = getString(getColumnIndex(RssPostTable._ID));

    String title = getString(getColumnIndex(RssPostTable.TITLE));
    String description = getString(getColumnIndex(RssPostTable.DESCRIPTION));
    String link = getString(getColumnIndex(RssPostTable.LINK));
    int channelId = getInt(getColumnIndex(RssPostTable.CHANNEL_ID));

    RssPost item = new RssPost(title, description, link, channelId);

    return item;

  }
}
