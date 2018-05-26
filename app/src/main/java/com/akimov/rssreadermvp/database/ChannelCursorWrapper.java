package com.akimov.rssreadermvp.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.akimov.rssreader.model.Channel;
import com.akimov.rssreadermvp.model.ChannelModel;

public class ChannelCursorWrapper extends CursorWrapper {
    public ChannelCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public ChannelModel getChannel() {
        String idString = getString(getColumnIndex(RssDbSchema.ChannelTable._ID));
        String title = getString(getColumnIndex(RssDbSchema.ChannelTable.TITLE));
        String description = getString(getColumnIndex(RssDbSchema.ChannelTable.DESCRIPTION));
        String link = getString(getColumnIndex(RssDbSchema.ChannelTable.LINK));

        ChannelModel channel = new ChannelModel(idString, link, title, description);

        return channel;

    }
}
