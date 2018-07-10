package com.akimov.rssreadermvp.data.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.akimov.rssreadermvp.business.models.RssChannel;

public class RssChannelCursorWrapper extends CursorWrapper {
    public RssChannelCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public RssChannel getChannel() {
        long id = getLong(getColumnIndex(RssChannelTable._ID));
        String title = getString(getColumnIndex(RssChannelTable.TITLE));
        String description = getString(getColumnIndex(RssChannelTable.DESCRIPTION));
        String link = getString(getColumnIndex(RssChannelTable.LINK));

        RssChannel channel = new RssChannel(id, link, title, description);

        return channel;

    }
}
