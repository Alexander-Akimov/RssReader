package com.akimov.rssreader.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.akimov.rssreader.model.Channel;

import java.util.ArrayList;

import static com.akimov.rssreader.database.RssDbSchema.SQL_CREATE_CHANNEL_TABLE;
import static com.akimov.rssreader.database.RssDbSchema.SQL_CREATE_ITEMS_TABLE;
import static com.akimov.rssreader.database.RssDbSchema.SQL_DELETE_CHANNEL_TABLE;
import static com.akimov.rssreader.database.RssDbSchema.SQL_DELETE_ITEMS_TABLE;

public class RssBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "rssReaderBase.db";

    public RssBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CHANNEL_TABLE);
        db.execSQL(SQL_CREATE_ITEMS_TABLE);

        initDB(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_CHANNEL_TABLE);
        db.execSQL(SQL_DELETE_ITEMS_TABLE);

        onCreate(db);
    }

    private void initDB(SQLiteDatabase db) {
        Channel channel1 = new Channel("", "https://www.yahoo.com/news/rss/politics",
                "Yahoo News - Latest News & Headlines",
                "The latest news and headlines from Yahoo! News. Get breaking news stories and in-depth coverage with videos and photos.");

        Channel channel2 = new Channel("", "https://echo.msk.ru/programs/brother/rss-audio.xml",
                "120 минут классики рока   (звук)  | Эхо Москвы",
                "120 минут классики рока   (звук)  на Эхе Москвы.\n" +
                        "        Ведущие:\n" +
                        "        Владимир Ильинский, Михаил Кузищев");

        Channel channel3 = new Channel("", "http://www.radiorecord.ru/rss.xml",
                "Radio Record",
                "Программы Радио Рекорд");

        ArrayList<Channel> channels = new ArrayList<>();
        channels.add(channel1);
        channels.add(channel2);
        channels.add(channel3);

        for (Channel channel : channels) {
            ContentValues values = new ContentValues();
            values.put(RssDbSchema.ChannelTable.TITLE, channel.getTitle());
            values.put(RssDbSchema.ChannelTable.DESCRIPTION, channel.getDescription());
            values.put(RssDbSchema.ChannelTable.LINK, channel.getLink());

            db.insert(RssDbSchema.ChannelTable.TABLE_NAME, null, values);
        }
    }



}

