package com.akimov.rssreadermvp.presentation.main.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akimov.rssreader.R;
import com.akimov.rssreadermvp.model.RssItemModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private final LayoutInflater layoutInflater;
    private List<RssItemModel> mItems;
    private ItemViewClick mItemClick;

    @Inject
    public ItemsAdapter(Context context) {
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mItems = Collections.emptyList();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = this.layoutInflater
                .inflate(R.layout.rssitem_list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        RssItemModel item = mItems.get(position);
        holder.bindRssItem(item, mItemClick);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setOnItemClickListener(ItemViewClick itemClick) {
        mItemClick = itemClick;
    }

    public void setItems(ArrayList<RssItemModel> itemsList) {
        mItems = null;//TODO:????
        mItems = itemsList;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private ItemViewClick mItemClick;
        private TextView mItemTitle;
        private TextView mItemDescription;
        private TextView mItemLink;
        private RssItemModel mItem;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mItemTitle = itemView.findViewById(R.id.itemTitle);
            mItemDescription = itemView.findViewById(R.id.itemDescription);
            mItemLink = itemView.findViewById(R.id.itemLink);
        }

        public void bindRssItem(RssItemModel rssItem, ItemViewClick itemClick) {
            mItem = rssItem;
            mItemClick = itemClick;
            mItemTitle.setText(rssItem.title);
            mItemDescription.setText(rssItem.description);
            mItemLink.setText(rssItem.link);
            itemView.setOnClickListener((view) -> mItemClick.handleClick(mItem));
        }
    }
}