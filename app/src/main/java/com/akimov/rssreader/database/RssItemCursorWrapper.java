package com.akimov.rssreader.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.akimov.rssreader.model.Channel;

public class RssItemCursorWrapper extends CursorWrapper {
    public RssItemCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Channel getChannel() {
        String idString = getString(getColumnIndex(RssDbSchema.ChannelTable._ID));
        String title = getString(getColumnIndex(RssDbSchema.ChannelTable.TITLE));
        String description = getString(getColumnIndex(RssDbSchema.ChannelTable.DESCRIPTION));
        String link = getString(getColumnIndex(RssDbSchema.ChannelTable.LINK));

        Channel channel = new Channel(idString, link, title, description);

        return channel;

    }
}
