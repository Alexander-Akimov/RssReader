package com.akimov.rssreadermvp.data.db;

import android.provider.BaseColumns;


public final class RssChannelTable implements BaseColumns {
  public static final String TABLE_NAME = "channel";

  public static final String TITLE = "title";
  public static final String DESCRIPTION = "description";
  public static final String LINK = "link";

  static final String SQL_CREATE_CHANNEL_TABLE =
      "CREATE TABLE " + RssChannelTable.TABLE_NAME + "(" +
          RssChannelTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
          RssChannelTable.TITLE + " TEXT NOT NULL, " +
          RssChannelTable.DESCRIPTION + " TEXT NOT NULL, " +
          RssChannelTable.LINK + " TEXT NOT NULL, " +
          "UNIQUE (" + RssChannelTable._ID + ") ON CONFLICT REPLACE )";

  static final String SQL_DROP_CHANNEL_TABLE =
      "DROP TABLE IF EXISTS " + RssChannelTable.TABLE_NAME;
}


