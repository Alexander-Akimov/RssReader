package com.akimov.rssreadermvp.presentation.main.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.akimov.rssreader.R;

import com.akimov.rssreader.databinding.AddChannelDialogBinding;
import com.akimov.rssreadermvp.App;
import com.akimov.rssreadermvp.business.models.RssChannel;
import com.akimov.rssreadermvp.di.main.ActivityModule;
import com.akimov.rssreadermvp.di.main.DaggerRssReaderComponent;
import com.akimov.rssreadermvp.di.main.RssReaderComponent;
import com.akimov.rssreadermvp.business.models.RssPost;
import com.akimov.rssreadermvp.presentation.main.presenter.IMainPresenter;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements IMainView {

  @Inject
  public IMainPresenter mainPresenter;

  @Inject
  public ChannelsAdapter mChannelsAdapter;

  @Inject
  public ItemsAdapter mItemsAdapter;

  @BindView(R.id.itemsListView)
  RecyclerView channelItemsView;

  @BindView(R.id.channel_list)
  SwipeMenuListView channelList;

  @BindView(R.id.drawer_layout)
  DrawerLayout mDrawerLayout;

  @BindView(R.id.progressBar)
  ProgressBar progressBar;

  @BindView(R.id.addChannelBtn)
  ImageView addChannelBtn;

  @BindView(R.id.toolbar)
  Toolbar toolbar;

  @BindView(R.id.mainChannelName)
  TextView mChannelName;
  private Unbinder unbinder;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    unbinder = ButterKnife.bind(this);

    setSupportActionBar(toolbar);

    progressBar.setVisibility(View.INVISIBLE);

    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    mDrawerLayout.addDrawerListener(toggle);
    toggle.syncState();

    addChannelBtn.setOnClickListener(view -> this.mainPresenter.addChannelClicked());

    initInjector();

    this.mainPresenter.setView(this);
    this.mainPresenter.initialize(); // data loading

    setupItemsRecyclerView();
    setupChannelListView();
  }

  private void initInjector() {
    RssReaderComponent activityComponent = DaggerRssReaderComponent.builder()
        .appComponent(App.get(this).getApplicationComponent())
        .activityModule(new ActivityModule(this))
        .build();

    activityComponent.inject(this);
  }

  @Override
  protected void onDestroy() {
    this.mainPresenter.onDestroy();
    super.onDestroy();
    unbinder.unbind();
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

  public void selectChannel() {

  }

  public void renderChannelList(ArrayList<RssChannel> channelsList) {
    if (channelsList != null) {
      this.mChannelsAdapter.setChannels(channelsList);
    }
  }

  public void renderChannelItems(ArrayList<RssPost> channelItemsList) {
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
    RssChannel channel = new RssChannel(0, "", "", "");

    View dialogView = getLayoutInflater().inflate(R.layout.add_channel_dialog, null);
    AddChannelDialogBinding binding = AddChannelDialogBinding.bind(dialogView);
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

  public void showEditChannelDialog(int position) {
    RssChannel channel = mChannelsAdapter.getItem(position);

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
        mainPresenter.updateChannel(channel); //, this.onReloadChannels
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
    //channelList.setOnItemClickListener(onChannelClickListener);
    channelList.setOnMenuItemClickListener(onMenuItemClickListener);
    channelList.setMenuCreator(menu -> {
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
    });
    channelList.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);
  }

  private SwipeMenuListView.OnMenuItemClickListener onMenuItemClickListener = (position, menu, index) -> {

    switch (index) {
      case 0:        // edit
        mainPresenter.editChannelClicked(position);
        break;
      case 1:        // delete
        RssChannel channel = mChannelsAdapter.getItem(position);
        mainPresenter.deleteChannel(channel); //, this.onReloadChannels
        break;
    }
    // false : close the menu; true : not close the menu
    return false;
  };


  private ItemViewClick onItemClicked = (RssPost rssItem) -> {
    //TODO: open item activity
  };
}
