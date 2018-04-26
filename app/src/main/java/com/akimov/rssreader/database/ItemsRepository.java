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

    private final String TAG = "ItemsRepository";
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

    private ChannelItemsCursorWrapper queryChannelItems(String id) {
        String selection = ItemTable.CHANNEL_ID + " = ?";
        String[] selectionArgs = {id};

        Cursor cursor = mDatabase.query(
                ItemTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        return new ChannelItemsCursorWrapper(cursor);
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

    public void insertChannel(Channel channel) {
        ContentValues values = new ContentValues();
        values.put(ChannelTable.TITLE, channel.getTitle());
        values.put(ChannelTable.DESCRIPTION, channel.getDescription());
        values.put(ChannelTable.LINK, channel.getLink());

        long newRowId = mDatabase.insert(ChannelTable.TABLE_NAME, null, values);
    }

    public void updateChannel(Channel channel) {
        ContentValues values = new ContentValues();
        values.put(ChannelTable.TITLE, channel.getTitle());
        values.put(ChannelTable.DESCRIPTION, channel.getDescription());
        values.put(ChannelTable.LINK, channel.getLink());

        int count = mDatabase.update(
                ChannelTable.TABLE_NAME,
                values,
                ChannelTable._ID + " LIKE ?",
                new String[]{channel.getId()});
    }

    public void deleteChannel(String channelId) {

        mDatabase.beginTransaction();
        try {
            int deletedItems = mDatabase.delete(ItemTable.TABLE_NAME,
                    ItemTable.CHANNEL_ID + " LIKE ?",
                    new String[]{channelId});

            int deletedChannels = mDatabase.delete(ChannelTable.TABLE_NAME,
                    ChannelTable._ID + " LIKE ?",
                    new String[]{channelId});
            mDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.getMessage());
        } finally {
            mDatabase.endTransaction();
        }
    }

    public ArrayList<RssItem> getChannelItems(String id) {
        ArrayList<RssItem> rssItems = new ArrayList<>();
        try (ChannelItemsCursorWrapper cursor = queryChannelItems(id)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                rssItems.add(cursor.getChannel());
                cursor.moveToNext();
            }
        }
        return rssItems;
    }

    public void insertChannelItems(ArrayList<RssItem> rssItems, String channelId) {
        //Log.d("THREAD 3", "" + Thread.currentThread().getId());
        mDatabase.beginTransaction();
        try {
            int deletedRows = mDatabase.delete(ItemTable.TABLE_NAME,
                    ItemTable.CHANNEL_ID + " LIKE ?",
                    new String[]{channelId});

            for (RssItem item : rssItems) {
                ContentValues values = new ContentValues();
                values.put(ItemTable.TITLE, item.title);
                values.put(ItemTable.DESCRIPTION, item.description);
                values.put(ItemTable.LINK, item.link);
                values.put(ItemTable.CHANNEL_ID, channelId);

                long newRowId = mDatabase.insert(ItemTable.TABLE_NAME, null, values);
            }
            mDatabase.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.getMessage());
        } finally {
            mDatabase.endTransaction();
        }
    }
}
