package com.akimov.rssreader.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.akimov.rssreadermvp.business.models.RssChannel;
import com.akimov.rssreadermvp.business.models.RssPost;
import com.akimov.rssreadermvp.data.db.DataBaseHelper;
import com.akimov.rssreadermvp.data.db.RssChannelCursorWrapper;
import com.akimov.rssreadermvp.data.db.RssChannelTable;
import com.akimov.rssreadermvp.data.db.RssPostCursorWrapper;
import com.akimov.rssreadermvp.data.db.RssPostTable;

import java.util.ArrayList;

/**
 * Created by lex on 6/9/18.
 */
public class RssDataSource {

  private static final String TAG = RssDataSource.class.getSimpleName();
  private SQLiteDatabase mDatabase;
  private DataBaseHelper dbHelper;

  public RssDataSource(Context context) {
    this.dbHelper = new DataBaseHelper(context);
    //this.database = dbHelper
  }

  public void open() {
    this.mDatabase = dbHelper.getWritableDatabase();
    Log.d(TAG, "database is opened");
  }

  public void close() {
    dbHelper.close();
    Log.d(TAG, "database is opened");
  }

  public void insertChannel(RssChannel channel) {
    ContentValues values = new ContentValues();
    values.put(RssChannelTable.TITLE, channel.getTitle());
    values.put(RssChannelTable.DESCRIPTION, channel.getDescription());
    values.put(RssChannelTable.LINK, channel.getLink());

    long newRowId = mDatabase.insert(RssChannelTable.TABLE_NAME, null, values);
  }

  public void insertChannelItem(RssPost item, long channelId) {
    ContentValues values = new ContentValues();
    values.put(RssPostTable.TITLE, item.getTitle());
    values.put(RssPostTable.DESCRIPTION, item.getDescription());
    values.put(RssPostTable.LINK, item.getLink());
    values.put(RssPostTable.CHANNEL_ID, channelId);

    long newRowId = mDatabase.insert(RssPostTable.TABLE_NAME, null, values);
  }

  public void insertChannelItems(ArrayList<RssPost> rssItems, long channelId) {
    //Log.d("THREAD 3", "" + Thread.currentThread().getId());
    mDatabase.beginTransaction();
    try {
      int deletedRows = mDatabase.delete(RssPostTable.TABLE_NAME,
          RssPostTable.CHANNEL_ID + " LIKE ?",
          new String[]{Long.toString(channelId)});

      for (RssPost item : rssItems) {
        insertChannelItem(item, channelId);
      }
      mDatabase.setTransactionSuccessful();

    } catch (Exception e) {
      Log.e(TAG, "Error: " + e.getMessage());
    } finally {
      mDatabase.endTransaction();
    }
  }


  private RssChannelCursorWrapper queryChannels() {
    Cursor cursor = mDatabase.query(
        RssChannelTable.TABLE_NAME,
        null,
        null,
        null,
        null,
        null,
        null
    );
    return new RssChannelCursorWrapper(cursor);
  }

  private RssPostCursorWrapper queryChannelItems(long id) {
    String selection = RssPostTable.CHANNEL_ID + " = ?";
    String[] selectionArgs = {Long.toString(id)};

    Cursor cursor = mDatabase.query(
        RssPostTable.TABLE_NAME,
        null,
        selection,
        selectionArgs,
        null,
        null,
        null
    );
    return new RssPostCursorWrapper(cursor);
  }

  public ArrayList<RssChannel> getChannels() {
    ArrayList<RssChannel> channels = new ArrayList<>();

    try (RssChannelCursorWrapper cursor = queryChannels()) {
      cursor.moveToFirst();
      while (!cursor.isAfterLast()) {
        channels.add(cursor.getChannel());
        cursor.moveToNext();
      }
    }
    return channels;
  }

  public ArrayList<RssPost> getChannelItems(long id) {
    ArrayList<RssPost> rssItems = new ArrayList<>();
    try (RssPostCursorWrapper cursor = queryChannelItems(id)) {
      cursor.moveToFirst();
      while (!cursor.isAfterLast()) {
        rssItems.add(cursor.getChannel());
        cursor.moveToNext();
      }
    }
    return rssItems;
  }

  public void updateChannel(RssChannel channel) {
    
  }

  public void deleteChannel(long channelId) {
  }
}
