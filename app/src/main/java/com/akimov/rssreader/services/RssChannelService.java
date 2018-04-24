package com.akimov.rssreader.services;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Xml;

import com.akimov.rssreader.database.ItemsRepository;
import com.akimov.rssreader.database.RssBaseHelper;
import com.akimov.rssreader.database.RssDbSchema;
import com.akimov.rssreader.database.RssItemCursorWrapper;
import com.akimov.rssreader.model.Channel;
import com.akimov.rssreader.model.RssItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RssChannelService {

    public ArrayList<Channel> channels;
    private ArrayList<RssItem> mRssItems;
    private Context mContext;
    private ItemsRepository mItemsRepository;


    public Channel mSelectedChannel = null;


    private static RssChannelService sInstance;

    public static RssChannelService get(Context context) {
        if (sInstance == null) {
            sInstance = new RssChannelService(context);
        }
        return sInstance;
    }

    private RssChannelService(Context context) {
        mContext = context;//.getApplicationContext();

        mItemsRepository = new ItemsRepository(context);


        //channels = new ArrayList<>();
        // mRssItems = new ArrayList<>();
    }


    public ArrayList<Channel> getChannels() {

        channels = mItemsRepository.getChannels();

        return channels;
    }

    public ArrayList<RssItem> getChannelItems(String channelId) {


        return mRssItems;
    }


    public List<RssItem> parseFeed(InputStream inputStream) throws XmlPullParserException, IOException {
        String title = null;
        String link = null;
        String description = null;
        boolean isItem = false;
        List<RssItem> items = new ArrayList<>();
        Channel channel = null;

        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(inputStream, null);

            xmlPullParser.nextTag();
            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                int eventType = xmlPullParser.getEventType();

                String name = xmlPullParser.getName();
                if (name == null)
                    continue;

                if (eventType == XmlPullParser.END_TAG) {
                    if (name.equalsIgnoreCase("item")) {
                        isItem = false;
                    }
                    continue;
                }

                if (eventType == XmlPullParser.START_TAG) {
                    if (name.equalsIgnoreCase("item")) {
                        isItem = true;
                        continue;
                    }
                }

                Log.d("MyXmlParser", "Parsing name ==> " + name);
                String result = "";
                if (xmlPullParser.next() == XmlPullParser.TEXT) {
                    result = xmlPullParser.getText();
                    xmlPullParser.nextTag();
                }

                if (name.equalsIgnoreCase("title")) {
                    title = result;
                } else if (name.equalsIgnoreCase("link")) {
                    link = result;
                } else if (name.equalsIgnoreCase("description")) {
                    description = result;
                }

                if (title != null && link != null && description != null) {
                    if (isItem) {
                        RssItem item = new RssItem(title, link, description, "");
                        items.add(item);
                    } else {
                        channel = new Channel("", title, link, description);
                    }

                    title = null;
                    link = null;
                    description = null;
                    isItem = false;
                }
            }

            return items;
        } finally {
            inputStream.close();
        }
    }
}
