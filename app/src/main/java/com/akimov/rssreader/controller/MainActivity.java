package com.akimov.rssreader.controller;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.akimov.rssreader.R;
import com.akimov.rssreader.adapters.ItemViewClick;
import com.akimov.rssreader.adapters.RssRecycleAdapter;
import com.akimov.rssreader.databinding.AddChannelDialogBinding;
import com.akimov.rssreader.model.Channel;
import com.akimov.rssreader.model.RssItem;
import com.akimov.rssreader.services.DataLoadingCallback;
import com.akimov.rssreader.services.RssChannelService;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

public class MainActivity extends AppCompatActivity {

    private RssChannelService mRssChannelService;
    private ArrayAdapter<Channel> mChannelsAdapter;
    private RssRecycleAdapter mRssRecycleAdapter;
    private ProgressBar progressBar;
    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressBar = findViewById(R.id.progressBar);
        //progressBar.setVisibility(View.VISIBLE);

        RecyclerView channelItemsView = findViewById(R.id.itemsListView);

        SwipeMenuListView channelList = findViewById(R.id.channel_list);
        channelList.setOnMenuItemClickListener(menuItemClickListener);
        channelList.setMenuCreator(creator);
        channelList.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mRssChannelService = RssChannelService.get(this);
        mRssChannelService.getChannels(onGetChannels);

        mChannelsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mRssChannelService.channels);
        channelList.setAdapter(mChannelsAdapter);
        channelList.setOnItemClickListener(onItemClickListener);

        mRssRecycleAdapter = new RssRecycleAdapter(mRssChannelService.channelItems, onRssItemClicked);
        channelItemsView.setAdapter(mRssRecycleAdapter);
        channelItemsView.setLayoutManager(new LinearLayoutManager(this));
        //channelItemsView.setHasFixedSize(true);
    }

    private SwipeMenuCreator creator = menu -> {
        // create "open" item
        SwipeMenuItem editItem = new SwipeMenuItem(
                getApplicationContext());
        // set item background
        editItem.setBackground(new ColorDrawable(Color.rgb(0xdf, 0xdf,
                0xff)));
        // set item width
        editItem.setWidth(100);
        editItem.setIcon(R.drawable.ic_edit);
        // set item title
        // editItem.setTitle("Изменить");
        // set item title fontsize
        // editItem.setTitleSize(14);
        // set item title font color
        editItem.setTitleColor(Color.WHITE);
        // add to menu
        menu.addMenuItem(editItem);

        // create "delete" item
        SwipeMenuItem deleteItem = new SwipeMenuItem(
                getApplicationContext());
        // set item background
        deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFf,
                0xbF, 0xc9)));
        // set item width
        deleteItem.setWidth(100);
        // set a icon
        deleteItem.setIcon(R.drawable.ic_delete);
        // add to menu
        menu.addMenuItem(deleteItem);
    };


    private SwipeMenuListView.OnMenuItemClickListener menuItemClickListener = (position, menu, index) -> {
        Channel channel = mChannelsAdapter.getItem(position);
        switch (index) {
            case 0:
                // edit
                View dialogView = getLayoutInflater().inflate(R.layout.add_channel_dialog, null);
                AddChannelDialogBinding binding = AddChannelDialogBinding.bind(dialogView);
                binding.setChannel(channel);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                AlertDialog dialog = builder.setView(binding.getRoot())
                        .setPositiveButton("Изменить", null)
                        .setNegativeButton("Отмена", null).show();

                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> {

                    if (channel != null && channel.isValid()) {
                        Toast.makeText(this, "Пожалуйста заполните пустые поля", Toast.LENGTH_SHORT).show();
                    } else {
                        mRssChannelService.updateChannel(channel, this.onReloadChannels);
                        dialog.dismiss();
                    }
                });
                break;
            case 1:
                // delete
                mRssChannelService.deleteChannel(channel.getId(), this.onReloadChannels);
                break;
        }
        // false : close the menu; true : not close the menu
        return false;
    };

    private AdapterView.OnItemClickListener onItemClickListener = (adapterView, view, i, l) -> {
        mRssChannelService.selectedChannel = mRssChannelService.channels.get(i);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        updateRssItems();
    };

    private ItemViewClick onRssItemClicked = (RssItem rssItem) -> {
        //TODO: open item activity
    };

    private DataLoadingCallback onGetChannels = (boolean success) -> {
        if (success) {
            if (mRssChannelService.channels.size() > 0) {
                mRssChannelService.selectedChannel = mRssChannelService.channels.get(0);
                updateRssItems();
                mChannelsAdapter.notifyDataSetChanged();
            }
        }
    };

    private DataLoadingCallback onReloadChannels = (boolean success) -> {
        if (success) {
            if (mRssChannelService.channels.size() > 0) {
                mRssChannelService.selectedChannel = mRssChannelService.channels.get(0);
                mChannelsAdapter.notifyDataSetChanged();
            }
        }
    };

    private void updateRssItems() {
        progressBar.setVisibility(View.VISIBLE);
        TextView mChannelName = findViewById(R.id.mainChannelName);
        mChannelName.setText(mRssChannelService.selectedChannel.getTitle());

        mRssChannelService.getChannelItems((boolean success) -> {
            if (success)
                mRssRecycleAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.INVISIBLE);
        });
    }

    public void addChannelBtnClicked(View view) {

        View dialogView = getLayoutInflater().inflate(R.layout.add_channel_dialog, null);
        AddChannelDialogBinding binding = AddChannelDialogBinding.bind(dialogView);

        Channel channel = new Channel("", "", "", "");
        binding.setChannel(channel);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        AlertDialog dialog = builder.setView(binding.getRoot())
                //.setTitle(R.string.add_dialog_title)
                .setPositiveButton("Добавить", null)
                .setNegativeButton("Отмена", null).show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view1 -> {
            if (channel.isValid()) {
                Toast.makeText(this, "Пожалуйста заполните пустые поля", Toast.LENGTH_SHORT).show();
            } else {
                mRssChannelService.addChannel(channel, this.onReloadChannels);
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        //mDbHelper.close();
        super.onDestroy();
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
