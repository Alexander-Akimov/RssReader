package com.akimov.rssreader.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.akimov.rssreadermvp.business.models.RssChannel;
import com.akimov.rssreadermvp.business.models.RssPost;
import com.akimov.rssreadermvp.data.db.DBHelper;
import com.akimov.rssreadermvp.data.db.RssChannelCursorWrapper;
import com.akimov.rssreadermvp.data.db.RssChannelTable;
import com.akimov.rssreadermvp.data.db.RssPostCursorWrapper;
import com.akimov.rssreadermvp.data.db.RssPostTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lex on 6/9/18.
 */
public class RssDataSource {

  private static final String TAG = RssDataSource.class.getSimpleName();
  private SQLiteDatabase mDatabase;
  private DBHelper dbHelper;

  public RssDataSource(Context context) {
    this.dbHelper = new DBHelper(context);
    //this.database = dbHelper
  }

  public void open() { // call in onResume()
    this.mDatabase = dbHelper.getWritableDatabase();
    Log.d(TAG, "database is opened");
  }

  public void close() { // call in onPause()
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

  private RssChannelCursorWrapper queryChannel(long id) { // 27.08.2018
    String selection = RssChannelTable._ID + " = ?";
    String[] selectionArgs = {Long.toString(id)};

    Cursor cursor = mDatabase.query(
        RssChannelTable.TABLE_NAME,
        null, // all columns
        selection,
        selectionArgs,
        null,
        null,
        null
    );
    return new RssChannelCursorWrapper(cursor);
  }

  public List<RssChannel> getAllChannels() { // 27.08.2018
    List<RssChannel> channels = new ArrayList<>();

    String selectQuery = "SELECT * FROM channel";
    Cursor cursor = mDatabase.rawQuery(selectQuery, null);
    RssChannelCursorWrapper cursorWrp = new RssChannelCursorWrapper(cursor);

    try {
      while (cursorWrp.moveToNext()) {
        RssChannel channel = cursorWrp.getChannel();
        channels.add(channel);
      }
    } finally {
      if (cursorWrp != null && cursorWrp.isClosed())
        cursorWrp.close();
    }

    return channels;
  }

  public void updateChannel(RssChannel channel) {
    ContentValues values = new ContentValues();
    values.put(RssChannelTable.TITLE, channel.getTitle());
    values.put(RssChannelTable.DESCRIPTION, channel.getDescription());
    values.put(RssChannelTable.LINK, channel.getLink());

    int count = mDatabase.update(
        RssChannelTable.TABLE_NAME,
        values,
        RssChannelTable._ID + " LIKE ?",
        new String[]{String.valueOf(channel.getId())});
    Log.d(TAG, "number of records updated: " + count);
  }

  public void deleteChannel(long channelId) { // 30.08.2018

    String selection = RssChannelTable._ID + " = ? ";
    String[] selectionArgs = {String.valueOf(channelId)};

    int count = mDatabase.delete(RssChannelTable.TABLE_NAME,
        selection, selectionArgs);
    Log.d(TAG, "number of deleted updated: " + count);
  }

  public void deleteAllChannels() { // 30.08.2018
    int count = mDatabase.delete(RssChannelTable.TABLE_NAME, null, null);
    Log.d(TAG, "number of deleted updated: " + count);
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


}
