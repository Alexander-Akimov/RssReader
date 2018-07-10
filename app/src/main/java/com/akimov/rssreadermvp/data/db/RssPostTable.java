package com.akimov.rssreadermvp.data.db;

import android.provider.BaseColumns;

public final class RssPostTable implements BaseColumns {
  public static final String TABLE_NAME = "items";

  public static final String TITLE = "title";
  public static final String DESCRIPTION = "description";
  public static final String LINK = "link";
  public static final String CHANNEL_ID = "channel_id";

  static final String SQL_CREATE_ITEMS_TABLE =
      "CREATE TABLE " + RssPostTable.TABLE_NAME + "(" +
          RssPostTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
          RssPostTable.TITLE + " TEXT NOT NULL, " +
          RssPostTable.DESCRIPTION + " TEXT NOT NULL, " +
          RssPostTable.LINK + " TEXT NOT NULL, " +
          RssPostTable.CHANNEL_ID + " INTEGER NOT NULL " +
          " REFERENCES " + RssChannelTable.TABLE_NAME + "," +
          " UNIQUE (" + RssPostTable._ID + ") ON CONFLICT REPLACE )";


  static final String SQL_DROP_ITEMS_TABLE =
      "DROP TABLE IF EXISTS " + RssPostTable.TABLE_NAME;
}
