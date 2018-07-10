package com.akimov.rssreader.database;

import android.content.Context;
import com.akimov.rssreadermvp.business.models.RssChannel;
import com.akimov.rssreadermvp.business.models.RssPost;

import java.util.ArrayList;

public class RssDataSourceRepository {

  private final String TAG = RssDataSourceRepository.class.getSimpleName();
  private RssDataSource mRssDataSource;

  public RssDataSourceRepository(Context context) {
    this.mRssDataSource = new RssDataSource(context);
  }



  public void insertChannel(RssChannel channel) {
    mRssDataSource.insertChannel(channel);
  }

  public void updateChannel(RssChannel channel) {
    /*ContentValues values = new ContentValues();
    values.put(RssChannelTable.TITLE, channel.getTitle());
    values.put(RssChannelTable.DESCRIPTION, channel.getDescription());
    values.put(RssChannelTable.LINK, channel.getLink());

    int count = mDatabase.update(
        RssChannelTable.TABLE_NAME,
        values,
        RssChannelTable._ID + " LIKE ?",
        new String[]{channel.getId()});*/
  }

  public void deleteChannel(String channelId) {

 /*   mDatabase.beginTransaction();
    try {
      int deletedItems = mDatabase.delete(RssPostTable.TABLE_NAME,
          RssPostTable.CHANNEL_ID + " LIKE ?",
          new String[]{channelId});

      int deletedChannels = mDatabase.delete(RssChannelTable.TABLE_NAME,
          RssChannelTable._ID + " LIKE ?",
          new String[]{channelId});
      mDatabase.setTransactionSuccessful();
    } catch (Exception e) {
      Log.e(TAG, "Error: " + e.getMessage());
    } finally {
      mDatabase.endTransaction();
    }*/
  }


  public void insertChannelItems(ArrayList<RssPost> rssItems, long channelId) {
    //Log.d("THREAD 3", "" + Thread.currentThread().getId());
    mRssDataSource.insertChannelItems(rssItems, channelId);
  }
}
