package com.akimov.rssreader;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.akimov.rssreader.adapters.RssRecycleAdapter;
import com.akimov.rssreader.model.Channel;
import com.akimov.rssreader.model.RssItem;
import com.akimov.rssreader.services.RssChannelService;

public class MainActivity extends AppCompatActivity {

    private RssChannelService mRssChannelService;
    private ArrayAdapter<Channel> mChannelsAdapter;
    private RssRecycleAdapter mRssRecycleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView channelItemsView = findViewById(R.id.itemsListView);
        ListView channelList = findViewById(R.id.channel_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mRssChannelService = RssChannelService.get(this.getApplicationContext());
        mChannelsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mRssChannelService.channels);

        channelList.setAdapter(mChannelsAdapter);
        channelList.setOnItemClickListener((adapterView, view, i, l) -> {
            mRssChannelService.selectedChannel = mRssChannelService.channels.get(i);
            mDrawerLayout.closeDrawer(GravityCompat.START);
            updateRssItems();
        });
        mRssChannelService.getChannels((boolean success) -> {
            if (success) {
                if (mRssChannelService.channels.size() > 0) {
                    mRssChannelService.selectedChannel = mRssChannelService.channels.get(0);
                    updateRssItems();
                    mChannelsAdapter.notifyDataSetChanged();
                }
            }
        });

        mRssRecycleAdapter = new RssRecycleAdapter(mRssChannelService.channelItems, (RssItem rssItem) -> {
            //TODO: open item activity
        });
        channelItemsView.setAdapter(mRssRecycleAdapter);
        channelItemsView.setLayoutManager(new LinearLayoutManager(this));
        channelItemsView.setHasFixedSize(true);
    }

    private void updateRssItems() {

        TextView mChannelName = findViewById(R.id.mainChannelName);
        mChannelName.setText(mRssChannelService.selectedChannel.getTitle());

        mRssChannelService.getChannelItems((boolean success) -> {
            if (success) {
                mRssRecycleAdapter.notifyDataSetChanged();
            }
        });/**/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void addChannelBtnClicked() {

        //mRssChannelService.addChannel();
    }
}
