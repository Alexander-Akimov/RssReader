package com.akimov.rssreader.services;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import com.akimov.rssreader.database.ItemsRepository;
import com.akimov.rssreader.database.RssBaseHelper;
import com.akimov.rssreader.database.RssDbSchema;
import com.akimov.rssreader.database.RssItemCursorWrapper;
import com.akimov.rssreader.database.RssLoader;
import com.akimov.rssreader.model.Channel;
import com.akimov.rssreader.model.RssItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RssChannelService {

    public ArrayList<Channel> channels = new ArrayList<>();
    public ArrayList<RssItem> channelItems = new ArrayList<>();
    private Context mContext;
    private ItemsRepository mItemsRepository;
    private RssLoader mRssLoader;

    public Channel selectedChannel = null;
    private Channel loadedChannel = null;


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

    public void getChannelItems(DataLoadingCallback callback) {

        try {
            mRssLoader.loadRssItems(selectedChannel.getLink(), success -> {
                if (success) {
                    loadedChannel = mRssLoader.getChannel();
                    channelItems.clear();
                    channelItems.addAll(mRssLoader.getChannelItems());
                    callback.complete(success);
                }
            });
            //channels = mItemsRepository.getChannels();
            //
        } catch (Exception e) {
            callback.complete(false);
        }
    }
    public void addChannel(Channel channel) {

    }


}
