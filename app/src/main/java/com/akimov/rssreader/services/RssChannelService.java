package com.akimov.rssreader.services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.akimov.rssreader.database.ItemsRepository;
import com.akimov.rssreader.database.RssLoader;
import com.akimov.rssreader.model.Channel;
import com.akimov.rssreader.model.RssItem;

import java.util.ArrayList;

public class RssChannelService {

    public ArrayList<Channel> channels = new ArrayList<>();
    public ArrayList<RssItem> channelItems = new ArrayList<>();
    private Context mContext;
    private ItemsRepository mItemsRepository;
    private RssLoader mRssLoader;

    public Channel selectedChannel = null;
    private Channel loadedChannel = null;

    private final String TAG = "RssChannelService";

    private static RssChannelService sInstance;

    public static RssChannelService get(Context context) {
        if (sInstance == null) {
            sInstance = new RssChannelService(context);
        }
        return sInstance;
    }

    private RssChannelService(Context context) {
        mContext = context;
        mItemsRepository = new ItemsRepository(context);
        mRssLoader = new RssLoader();
    }

    public void getChannels(DataLoadingCallback callback) {
        try {
            channels.clear();
            channels.addAll(mItemsRepository.getChannels());

            callback.complete(true);
        } catch (Exception e) {
            callback.complete(false);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(mContext.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void getChannelItems(DataLoadingCallback callback) {

        try {
            if (isNetworkAvailable()) {
                mRssLoader.loadRssItems(selectedChannel.getLink(), success -> {
                    if (success) {
                        loadedChannel = mRssLoader.getChannel();
                        channelItems.clear();
                        channelItems.addAll(mRssLoader.getChannelItems());

                        // Log.d("THREAD 1", "" + Thread.currentThread().getId());

                        new StoreItemsTask(mItemsRepository, channelItems, selectedChannel.getId()).execute();
                    }
                    callback.complete(success);
                });
            }
            else
            {
                channelItems.clear();
                channelItems.addAll(mItemsRepository.getChannelItems(selectedChannel.getId()));
                callback.complete(true);
            }
        } catch (Exception e) {
            callback.complete(false);
        }
    }

    private class StoreItemsTask extends AsyncTask<Void, Void, Boolean> {

        private ItemsRepository mRepository;
        private ArrayList<RssItem> mItems = new ArrayList<>();
        private String mChannelId;

        StoreItemsTask(ItemsRepository repository, ArrayList<RssItem> items, String channelId) {
            mRepository = repository;
            mChannelId = channelId;
            mItems.clear();
            mItems.addAll(items);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            try {
                // Log.d("THREAD 2", "" + Thread.currentThread().getId());
                mRepository.insertRssItems(mItems, mChannelId);
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

    public void addChannel(Channel channel, DataLoadingCallback callback) {
        try {
            mItemsRepository.insertChannel(channel);
            channels.clear();
            channels.addAll(mItemsRepository.getChannels());

            callback.complete(true);
        } catch (Exception e) {
            callback.complete(false);
        }
    }

    public void addChannelItems() {

    }

}
