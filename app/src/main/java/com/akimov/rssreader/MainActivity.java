package com.akimov.rssreader;

import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.akimov.rssreader.adapters.ItemViewClick;
import com.akimov.rssreader.adapters.RssRecycleAdapter;
import com.akimov.rssreader.model.Channel;
import com.akimov.rssreader.model.RssItem;
import com.akimov.rssreader.services.DataLoadingCallback;
import com.akimov.rssreader.services.RssChannelService;

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
        ListView channelList = findViewById(R.id.channel_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mRssChannelService = RssChannelService.get(this.getApplicationContext());
        mRssChannelService.getChannels(onGetChannels);

        mChannelsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mRssChannelService.channels);
        channelList.setAdapter(mChannelsAdapter);
        channelList.setOnItemClickListener(onItemClickListener);


        mRssRecycleAdapter = new RssRecycleAdapter(mRssChannelService.channelItems, onRssItemClicked);
        channelItemsView.setAdapter(mRssRecycleAdapter);
        channelItemsView.setLayoutManager(new LinearLayoutManager(this));
        //channelItemsView.setHasFixedSize(true);
    }


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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View dialogView = getLayoutInflater().inflate(R.layout.add_channel_dialog, null);
        EditText channelTitle = dialogView.findViewById(R.id.addChannelTitleTxt);
        EditText channelDesc = dialogView.findViewById(R.id.addChannelDescTxt);
        EditText channelUrl = dialogView.findViewById(R.id.addChannelUrl);

        AlertDialog dialog = builder.setView(dialogView)
                .setPositiveButton("Добавить", null)
                .setNegativeButton("Отмена", null).show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view1 -> {
            String title = channelTitle.getText().toString();
            String description = channelDesc.getText().toString();
            String url = channelUrl.getText().toString();
            if (title.isEmpty() && description.isEmpty() && url.isEmpty()) {
                Toast.makeText(this, "Пожалуйста заполните пустые поля", Toast.LENGTH_SHORT).show();
            } else {
                Channel channel = new Channel("", url, title, description);
                mRssChannelService.addChannel(channel, onReloadChannels);
                dialog.dismiss();
            }
        });
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
