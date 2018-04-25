package com.akimov.rssreader.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.akimov.rssreader.database.RssDbSchema.ChannelTable;
import com.akimov.rssreader.model.Channel;

import java.util.ArrayList;

public class ItemsRepository {
    private SQLiteDatabase mDatabase;

    public ItemsRepository(Context context) {
        SQLiteDatabase db = new RssBaseHelper(context)
                .getWritableDatabase();

        mDatabase = db;

    }

    private RssItemCursorWrapper queryChannels() {
        Cursor cursor = mDatabase.query(
                ChannelTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        return new RssItemCursorWrapper(cursor);
    }

    public ArrayList<Channel> getChannels() {
        ArrayList<Channel> channels = new ArrayList<>();
        try (RssItemCursorWrapper cursor = queryChannels()) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                channels.add(cursor.getChannel());
                cursor.moveToNext();
            }
        }
        return channels;
    }

    public void addChannel(Channel channel) {
        ContentValues values = new ContentValues();
        values.put(ChannelTable.TITLE, channel.getTitle());
        values.put(ChannelTable.DESCRIPTION, channel.getDescription());
        values.put(ChannelTable.LINK, channel.getLink());

        long newRowId = mDatabase.insert(ChannelTable.TABLE_NAME, null, values);

    }

}
