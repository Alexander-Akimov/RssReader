package com.akimov.rssreader.services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.akimov.rssreader.database.RssDataSource;
import com.akimov.rssreadermvp.business.models.RssChannel;
import com.akimov.rssreadermvp.business.models.RssPost;


import java.util.ArrayList;

public class RssChannelService {

  private final String TAG = RssChannelService.class.getSimpleName();

  public ArrayList<RssChannel> channels = new ArrayList<>();
  public ArrayList<RssPost> channelItems = new ArrayList<>();
  private Context mContext;
  private RssDataSource mRssDataSource;
  private RssLoader mRssLoader;

  public RssChannel selectedChannel = null;
  //private RssChannel loadedChannel = null;

  private static RssChannelService sInstance;

  public static RssChannelService get(Context context) {
    if (sInstance == null) {
      sInstance = new RssChannelService(context);
    }
    return sInstance;
  }

  private RssChannelService(Context context) {
    mContext = context;
    mRssDataSource = new RssDataSource(context);
    mRssLoader = new RssLoader();
  }

  private boolean isNetworkAvailable() {
    ConnectivityManager connectivityManager
        = (ConnectivityManager) mContext.getSystemService(mContext.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
  }

  public void getChannels(DataLoadingCallback callback) {
    try {
      channels.clear();
      channels.addAll(mRssDataSource.getChannels());

      callback.complete(true);
    } catch (Exception e) {
      callback.complete(false);
    }
  }

  public void getChannelItems(DataLoadingCallback callback) {

    try {
      if (isNetworkAvailable()) {
        mRssLoader.loadRssPosts(selectedChannel.getLink(), success -> {
          if (success) {
            //loadedChannel = mRssLoader.getChannel();
            channelItems.clear();
            channelItems.addAll(mRssLoader.getChannelItems());

            // Log.d("THREAD 1", "" + Thread.currentThread().getId());

            new StoreItemsTask(mRssDataSource, channelItems, selectedChannel.getId()).execute();
          }
          callback.complete(success);
        });
      } else {
        channelItems.clear();
        channelItems.addAll(mRssDataSource.getChannelItems(selectedChannel.getId()));
        callback.complete(true);
      }
    } catch (Exception e) {
      callback.complete(false);
    }
  }

  public void addChannel(RssChannel channel, DataLoadingCallback callback) {
    try {
      mRssDataSource.insertChannel(channel);
      channels.clear();
      channels.addAll(mRssDataSource.getChannels());

      callback.complete(true);
    } catch (Exception e) {
      callback.complete(false);
    }
  }

  public void updateChannel(RssChannel channel, DataLoadingCallback callback) {
    try {
      mRssDataSource.updateChannel(channel);
      callback.complete(true);
    } catch (Exception e) {
      callback.complete(false);
    }
  }

  public void deleteChannel(long channelId, DataLoadingCallback callback) {
    try {
      mRssDataSource.deleteChannel(channelId);
      channels.clear();
      channels.addAll(mRssDataSource.getChannels());

      callback.complete(true);
    } catch (Exception e) {
      callback.complete(false);
    }
  }

  private class StoreItemsTask extends AsyncTask<Void, Void, Boolean> {

    private RssDataSource mRssDataSource;
    private ArrayList<RssPost> mItems = new ArrayList<>();
    private long mChannelId;

    StoreItemsTask(RssDataSource dataSource, ArrayList<RssPost> items, long channelId) {
      mRssDataSource = dataSource;
      mChannelId = channelId;
      mItems.clear();
      mItems.addAll(items);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

      try {
        // Log.d("THREAD 2", "" + Thread.currentThread().getId());
        mRssDataSource.insertChannelItems(mItems, mChannelId);
        return true;
      } catch (Exception e) {
        Log.e(TAG, "Error", e);
      }
      return false;
    }

    @Override
    protected void onPostExecute(Boolean success) {
      //mCallback.complete(success);
    }
  }


}
