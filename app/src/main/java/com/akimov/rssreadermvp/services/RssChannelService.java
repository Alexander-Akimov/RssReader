package com.akimov.rssreadermvp.services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.akimov.rssreader.services.DataLoadingCallback;
import com.akimov.rssreadermvp.business.models.RssChannel;
import com.akimov.rssreader.database.ItemsRepository;
import com.akimov.rssreadermvp.business.models.RssPost;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RssChannelService {

    private final String TAG = "RssChannelService";


    public ItemsRepository mItemsRepository;


    public RssLoader mRssLoader;

    private RssChannel selectedChannel = null;
    private List<RssChannel> channels = new ArrayList<>();
    private ArrayList<RssPost> channelItems = new ArrayList<>();

    private Context mContext;


    RssChannelService(Context context) {
        mContext = context;
    }

    public List<RssChannel> getChannels() {
        this.channels = mItemsRepository.getChannels();

        if (this.channels.size() > 0)
            this.selectedChannel = this.channels.get(0);

        return channels;
    }

    public ArrayList<RssPost> getChannelItems() {
        return this.channelItems;
    }

    public void getChannelItems(DataLoadingCallback callback) {

        try {
            if (isNetworkAvailable()) {
                mRssLoader.loadRssItems(selectedChannel.getLink(), success -> {
                    if (success) {
                        //loadedChannel = mRssLoader.getChannel();

                        channelItems.clear();
                        channelItems.addAll(mRssLoader.getChannelItems());

                        // Log.d("THREAD 1", "" + Thread.currentThread().getId());

                        new StoreItemsTask(mItemsRepository, channelItems, selectedChannel.getId()).execute();
                    }
                    callback.complete(success);
                });
            } else {
                channelItems.clear();
                channelItems.addAll(mItemsRepository.getChannelItems(selectedChannel.getId()));
                callback.complete(true);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.getLocalizedMessage());
            callback.complete(false);
        }
    }

    private class StoreItemsTask extends AsyncTask<Void, Void, Boolean> {

        private ItemsRepository mRepository;
        private ArrayList<RssPost> mItems = new ArrayList<>();
        private long mChannelId;

        StoreItemsTask(ItemsRepository repository, ArrayList<RssPost> items, long channelId) {
            mRepository = repository;
            mChannelId = channelId;
            mItems.clear();
            mItems.addAll(items);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            try {
                // Log.d("THREAD 2", "" + Thread.currentThread().getId());
                mRepository.insertChannelItems(mItems, mChannelId);
                return true;
            } catch (Exception e) {
                Log.e(TAG, "Error: " + e.getLocalizedMessage());
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            //mCallback.complete(success);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(mContext.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
