package com.akimov.rssreadermvp.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

public class DBHelper extends SQLiteOpenHelper {

  private static final int VERSION = 1;
  private static final String DATABASE_NAME = "rssReaderBase.db";

  public DBHelper(@NonNull Context context) {
    super(context, DATABASE_NAME, null, VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(RssChannelTable.SQL_CREATE_CHANNEL_TABLE);
    db.execSQL(RssPostTable.SQL_CREATE_ITEMS_TABLE);

    initDB(db);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL(RssChannelTable.SQL_DROP_CHANNEL_TABLE);
    db.execSQL(RssPostTable.SQL_DROP_ITEMS_TABLE);

    onCreate(db);
  }

  private void initDB(SQLiteDatabase db) {

    //TODO: possible convert to db.execSQL
    ContentValues values = new ContentValues();
    values.put(RssChannelTable.TITLE, "Yahoo News - Latest News & Headlines");
    values.put(RssChannelTable.DESCRIPTION, "The latest news and headlines from Yahoo! News. Get breaking news stories and in-depth coverage with videos and photos.");
    values.put(RssChannelTable.LINK, "https://www.yahoo.com/news/rss/politics");
    db.insert(RssChannelTable.TABLE_NAME, null, values);


    values = new ContentValues();
    values.put(RssChannelTable.TITLE, "120 минут классики рока   (звук)  | Эхо Москвы");
    values.put(RssChannelTable.DESCRIPTION, "120 минут классики рока   (звук)  на Эхе Москвы.\n Ведущие:\n Владимир Ильинский, Михаил Кузищев");
    values.put(RssChannelTable.LINK, "https://echo.msk.ru/programs/brother/rss-audio.xml");
    db.insert(RssChannelTable.TABLE_NAME, null, values);


    values = new ContentValues();
    values.put(RssChannelTable.TITLE, "Новости - политика");
    values.put(RssChannelTable.DESCRIPTION, "Новости - политика - Радио Свобода");
    values.put(RssChannelTable.LINK, "https://www.svoboda.org/api/zogqpoegmopo");
    db.insert(RssChannelTable.TABLE_NAME, null, values);
  }
}

