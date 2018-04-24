package com.akimov.rssreader.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akimov.rssreader.R;
import com.akimov.rssreader.model.RssItem;

import java.util.List;

public class RssRecycleAdapter extends RecyclerView.Adapter<RssRecycleAdapter.ItemViewHolder> {

    private List<RssItem> mRssItems;
    private ItemViewClick mItemClick;

    public RssRecycleAdapter(List<RssItem> rssItems, ItemViewClick itemClick) {
        mRssItems = rssItems;
        mItemClick = itemClick;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rssitem_list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        RssItem item = mRssItems.get(position);
        holder.bindRssItem(item, mItemClick);
    }

    @Override
    public int getItemCount() {
        return mRssItems.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private ItemViewClick mItemClick;
        private TextView mItemTitle;
        private TextView mItemDescription;
        private TextView mItemLink;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mItemTitle = itemView.findViewById(R.id.itemTitle);
            mItemDescription = itemView.findViewById(R.id.itemDescription);
            mItemLink = itemView.findViewById(R.id.itemLink);
        }

        public void bindRssItem(RssItem rssItem, ItemViewClick itemClick) {
            mItemClick = itemClick;
            mItemTitle.setText(rssItem.title);
            mItemDescription.setText(rssItem.description);
            mItemLink.setText(rssItem.link);
            itemView.setOnClickListener((view) -> mItemClick.handleClick(rssItem));
        }
    }
}
