package com.akimov.rssreadermvp.data.db;

import android.provider.BaseColumns;


public final class RssChannelTable implements BaseColumns {
  public static final String TABLE_NAME = "channel";

  public static final String TITLE = "title";
  public static final String DESCRIPTION = "description";
  public static final String LINK = "link";

  static final String SQL_CREATE_CHANNEL_TABLE =
      "CREATE TABLE " + TABLE_NAME + "(" +
          _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
          TITLE + " TEXT NOT NULL, " +
          DESCRIPTION + " TEXT NOT NULL, " +
          LINK + " TEXT NOT NULL, " +
          "UNIQUE (" + _ID + ") ON CONFLICT REPLACE )";

  static final String SQL_DROP_CHANNEL_TABLE =
      "DROP TABLE IF EXISTS " + TABLE_NAME;
}


