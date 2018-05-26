package com.akimov.rssreadermvp.database;

import android.provider.BaseColumns;

public class RssDbSchema {

    public static final class ChannelTable implements BaseColumns {
        public static final String TABLE_NAME = "channel";

        public static final String LINK = "link";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
    }

    public static final class ItemTable implements BaseColumns {
        public static final String TABLE_NAME = "items";

        public static final String GUID = "guid";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String LINK = "link";
        public static final String CHANNEL_ID = "channel_id";
    }

    public static final String SQL_CREATE_CHANNEL_TABLE =
            "CREATE TABLE " + ChannelTable.TABLE_NAME + "(" +
                    ChannelTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ChannelTable.TITLE + ", " +
                    ChannelTable.DESCRIPTION + ", " +
                    ChannelTable.LINK + ")";

    public static final String SQL_CREATE_ITEMS_TABLE =
            "CREATE TABLE " + ItemTable.TABLE_NAME + "(" +
                    ItemTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ItemTable.GUID + ", " +
                    ItemTable.TITLE + ", " +
                    ItemTable.DESCRIPTION + ", " +
                    ItemTable.LINK + ", " +
                    ItemTable.CHANNEL_ID + ")";

    public static final String SQL_DELETE_CHANNEL_TABLE =
            "DROP TABLE IF EXISTS " + ChannelTable.TABLE_NAME;

    public static final String SQL_DELETE_ITEMS_TABLE =
            "DROP TABLE IF EXISTS " + ItemTable.TABLE_NAME;
}
