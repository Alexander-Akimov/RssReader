package com.akimov.rssreadermvp.presentation.main.view;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.akimov.rssreadermvp.model.ChannelModel;

import java.util.ArrayList;

import javax.inject.Inject;

public class ChannelsAdapter extends ArrayAdapter<ChannelModel> {

    @Inject
    public ChannelsAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1);
    }

    public void setChannels(ArrayList<ChannelModel> channels) {
        clear();
        addAll(channels);
        notifyDataSetChanged();
    }

}