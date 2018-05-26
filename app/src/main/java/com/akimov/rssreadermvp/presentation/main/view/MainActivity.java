package com.akimov.rssreadermvp.presentation.main.view;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.akimov.rssreader.R;
import com.akimov.rssreader.databinding.AddChannelDialogBinding;
import com.akimov.rssreader.model.Channel;
import com.akimov.rssreadermvp.App;
import com.akimov.rssreadermvp.di.main.DaggerRssReaderComponent;
import com.akimov.rssreadermvp.di.main.RssReaderComponent;
import com.akimov.rssreadermvp.model.ChannelModel;
import com.akimov.rssreadermvp.model.RssItemModel;
import com.akimov.rssreadermvp.presentation.main.presenter.IMainPresenter;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements IMainView {

    @Inject
    public IMainPresenter mainPresenter;

    @Inject
    public ChannelsAdapter mChannelsAdapter;

    @Inject
    public ItemsAdapter mItemsAdapter;

    RecyclerView channelItemsView;
    SwipeMenuListView channelList;
    DrawerLayout mDrawerLayout;
    private ProgressBar progressBar;
    private ImageView addChannelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        channelList = findViewById(R.id.channel_list);
        channelItemsView = findViewById(R.id.itemsListView);
        addChannelBtn = findViewById(R.id.addChannelBtn);
        addChannelBtn.setOnClickListener(view -> this.mainPresenter.addChannelClicked());

        RssReaderComponent activityComponent = DaggerRssReaderComponent.builder()
                .appComponent(App.get(this).getApplicationComponent())
                .build();

        activityComponent.inject(this);

        this.mainPresenter.setView(this);
        this.mainPresenter.initialize(); // data loading

        setupItemsRecyclerView();
        setupChannelListView();
    }

    @Override
    protected void onDestroy() {
        this.mainPresenter.onDestroy();
        super.onDestroy();
    }

    public void selectChannel() {

    }

    public void renderChannelList(ArrayList<ChannelModel> channelsList) {
        if (channelsList != null) {
            this.mChannelsAdapter.setChannels(channelsList);
        }
    }

    public void renderChannelItems(ArrayList<RssItemModel> channelItemsList) {
        if (channelItemsList != null) {
            this.mItemsAdapter.setItems(channelItemsList);
            this.mItemsAdapter.notifyDataSetChanged();
        }
    }

    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showAddChannelDialog() {
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
                mainPresenter.addChannel(channel);
                dialog.dismiss();
            }
        });
    }

    private void setupItemsRecyclerView() {
        mItemsAdapter.setOnItemClickListener(onItemClicked);
        channelItemsView.setAdapter(mItemsAdapter);
        channelItemsView.setLayoutManager(new LinearLayoutManager(this));

        //channelItemsView.setHasFixedSize(true);
    }

    private void setupChannelListView() {
        channelList.setAdapter(mChannelsAdapter);
        channelList.setOnItemClickListener(onChannelClickListener);
    }

    private AdapterView.OnItemClickListener onChannelClickListener = (adapterView, view, i, l) -> {

    };

    private ItemViewClick onItemClicked = (RssItemModel rssItem) -> {
        //TODO: open item activity
    };
}
