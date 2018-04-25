package com.akimov.rssreader.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.akimov.rssreader.model.Channel;
import com.akimov.rssreader.model.RssItem;

public class RssItemCursorWrapper extends CursorWrapper {
    public RssItemCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public RssItem getChannel() {
        String idString = getString(getColumnIndex(RssDbSchema.ItemTable._ID));

        String title = getString(getColumnIndex(RssDbSchema.ItemTable.TITLE));
        String description = getString(getColumnIndex(RssDbSchema.ItemTable.DESCRIPTION));
        String link = getString(getColumnIndex(RssDbSchema.ItemTable.LINK));
        String channelId = getString(getColumnIndex(RssDbSchema.ItemTable.CHANNEL_ID));

        RssItem item = new RssItem(title, description, link, channelId);

        return item;

    }
}
