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
          _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
          TITLE + " TEXT NOT NULL, " +
          DESCRIPTION + " TEXT NOT NULL, " +
          LINK + " TEXT NOT NULL, " +
          CHANNEL_ID + " INTEGER NOT NULL " +
          " REFERENCES " + TABLE_NAME + "," +
          " UNIQUE (" + _ID + ") ON CONFLICT REPLACE )";


  static final String SQL_DROP_ITEMS_TABLE =
      "DROP TABLE IF EXISTS " + TABLE_NAME;
}
