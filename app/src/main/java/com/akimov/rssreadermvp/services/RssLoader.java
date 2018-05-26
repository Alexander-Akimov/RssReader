package com.akimov.rssreadermvp.services;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;

import com.akimov.rssreader.services.DataLoadingCallback;
import com.akimov.rssreadermvp.model.ChannelModel;
import com.akimov.rssreadermvp.model.RssItemModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RssLoader {
    private final String TAG = "RssLoader";
    private ChannelModel mChannel = null;
    private ArrayList<RssItemModel> mChannelItems = null;

    public void loadRssItems(String url, DataLoadingCallback callback) {
        new FetchFeedTask(callback).execute(url);
    }

    public ChannelModel getChannel() {
        return mChannel;
    }

    public ArrayList<RssItemModel> getChannelItems() {
        return mChannelItems;
    }

    private class FetchFeedTask extends AsyncTask<String, Void, Boolean> {

        private DataLoadingCallback mCallback;

        FetchFeedTask(DataLoadingCallback callback) {
            mCallback = callback;
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            String urlLink = urls[0];
            if (TextUtils.isEmpty(urlLink))
                return false;

            try {
                if (!urlLink.startsWith("http://") && !urlLink.startsWith("https://"))
                    urlLink = "http://" + urlLink;

                InputStream inputStream = downloadUrl(urlLink);
                mChannelItems = parseFeed(inputStream);
                return true;
            } catch (IOException e) {
                Log.e(TAG, "Error: " + e.getLocalizedMessage());
            } catch (XmlPullParserException e) {
                Log.e(TAG, "Error: " + e.getLocalizedMessage());
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            mCallback.complete(success);
        }
    }

    // Given a string representation of a URL, sets up a connection and gets
// an input stream.
    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //conn.setReadTimeout(10000 /* milliseconds */);
        // conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
        conn.setRequestProperty("Accept", "*/*");
        // Starts the query
        conn.connect();

        return conn.getInputStream();
    }

    public ArrayList<RssItemModel> parseFeed(InputStream inputStream) throws XmlPullParserException, IOException {
        String title = null;
        String link = null;
        String description = null;
        boolean isItem = false;
        ArrayList<RssItemModel> items = new ArrayList<>();

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

                //Log.d("MyXmlParser", "Parsing name ==> " + name);
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
                        RssItemModel item = new RssItemModel(title, description, link, "");
                        items.add(item);
                    } else {
                        //  mChannel = new Channel("", title, link, description);
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
