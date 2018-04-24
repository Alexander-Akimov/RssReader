package com.akimov.rssreader;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.akimov.rssreader.model.Channel;
import com.akimov.rssreader.services.RssChannelService;

public class MainActivity extends AppCompatActivity {

    private RssChannelService mRssChannelService;
    private ArrayAdapter<Channel> mChannelArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView mChannelList = findViewById(R.id.channel_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       final DrawerLayout mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mRssChannelService = RssChannelService.get(this.getApplicationContext());
        mRssChannelService.getChannels();

        mChannelArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mRssChannelService.channels);
        mChannelList.setAdapter(mChannelArrayAdapter);

        mChannelList.setOnItemClickListener((adapterView, view, i, l) -> {
            mRssChannelService.mSelectedChannel = mRssChannelService.channels.get(i);
            mDrawerLayout.closeDrawer(GravityCompat.START);
        });
    }

    private void updateRssItems(){

        //mRssChannelService.getChannelItems()
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
}
