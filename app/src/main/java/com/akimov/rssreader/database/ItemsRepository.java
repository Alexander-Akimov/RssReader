package com.akimov.rssreader.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.akimov.rssreader.database.RssDbSchema.ChannelTable;
import com.akimov.rssreader.database.RssDbSchema.ItemTable;
import com.akimov.rssreader.model.Channel;
import com.akimov.rssreader.model.RssItem;

import java.util.ArrayList;

public class ItemsRepository {
    private SQLiteDatabase mDatabase;

    public ItemsRepository(Context context) {
        SQLiteDatabase db = new RssBaseHelper(context)
                .getWritableDatabase();

        mDatabase = db;
    }

    private ChannelCursorWrapper queryChannels() {
        Cursor cursor = mDatabase.query(
                ChannelTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        return new ChannelCursorWrapper(cursor);
    }

    private RssItemCursorWrapper queryChannelItems(String id) {
        String selection = ItemTable.CHANNEL_ID + " = ?";
        String[] selectionArgs = { id };

        Cursor cursor = mDatabase.query(
                ItemTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        return new RssItemCursorWrapper(cursor);
    }

    public ArrayList<Channel> getChannels() {
        ArrayList<Channel> channels = new ArrayList<>();
        try (ChannelCursorWrapper cursor = queryChannels()) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                channels.add(cursor.getChannel());
                cursor.moveToNext();
            }
        }
        return channels;
    }

    public ArrayList<RssItem> getChannelItems(String id) {
        ArrayList<RssItem> rssItems = new ArrayList<>();
        try (RssItemCursorWrapper cursor = queryChannelItems(id)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                rssItems.add(cursor.getChannel());
                cursor.moveToNext();
            }
        }
        return rssItems;
    }

    public void insertChannel(Channel channel) {
        ContentValues values = new ContentValues();
        values.put(ChannelTable.TITLE, channel.getTitle());
        values.put(ChannelTable.DESCRIPTION, channel.getDescription());
        values.put(ChannelTable.LINK, channel.getLink());

        long newRowId = mDatabase.insert(ChannelTable.TABLE_NAME, null, values);
    }

    public void insertRssItems(ArrayList<RssItem> rssItems, String channelId) {
        //Log.d("THREAD 3", "" + Thread.currentThread().getId());

        // Define 'where' part of query.
        String selection = ItemTable.CHANNEL_ID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {channelId};
        // Issue SQL statement.
        int deletedRows = mDatabase.delete(ItemTable.TABLE_NAME, selection, selectionArgs);

        for (RssItem item : rssItems) {
            ContentValues values = new ContentValues();
            values.put(ItemTable.TITLE, item.title);
            values.put(ItemTable.DESCRIPTION, item.description);
            values.put(ItemTable.LINK, item.link);
            values.put(ItemTable.CHANNEL_ID, channelId);

            long newRowId = mDatabase.insert(ItemTable.TABLE_NAME, null, values);
        }/**/
    }

}
