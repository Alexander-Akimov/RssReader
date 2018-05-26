package com.akimov.rssreadermvp.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.akimov.rssreadermvp.model.RssItemModel;


public class ChannelItemsCursorWrapper extends CursorWrapper {
    public ChannelItemsCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public RssItemModel getChannel() {
        String idString = getString(getColumnIndex(RssDbSchema.ItemTable._ID));

        String title = getString(getColumnIndex(RssDbSchema.ItemTable.TITLE));
        String description = getString(getColumnIndex(RssDbSchema.ItemTable.DESCRIPTION));
        String link = getString(getColumnIndex(RssDbSchema.ItemTable.LINK));
        String channelId = getString(getColumnIndex(RssDbSchema.ItemTable.CHANNEL_ID));

        RssItemModel item = new RssItemModel(title, description, link, channelId);

        return item;

    }
}
