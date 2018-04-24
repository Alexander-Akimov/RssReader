package com.akimov.rssreader.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
                RssDbSchema.ChannelTable.TABLE_NAME,
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
        RssItemCursorWrapper cursor = queryChannels();
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                channels.add(cursor.getChannel());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return channels;
    }
}
