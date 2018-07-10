package com.akimov.rssreadermvp.presentation.main.view;

import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;

import com.akimov.rssreadermvp.business.models.RssChannel;

import java.util.ArrayList;

import javax.inject.Inject;

public class ChannelsAdapter extends ArrayAdapter<RssChannel> {

    @Inject
    public ChannelsAdapter(Activity context) {
        super(context, android.R.layout.simple_list_item_1);
    }

    public void setChannels(ArrayList<RssChannel> channels) {
        clear();
        addAll(channels);
        notifyDataSetChanged();
    }

}