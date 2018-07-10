package com.akimov.rssreader.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akimov.rssreader.R;
import com.akimov.rssreadermvp.business.models.RssPost;

import java.util.List;

public class RssRecycleAdapter extends RecyclerView.Adapter<RssRecycleAdapter.ItemViewHolder> {

    private List<RssPost> mRssItems;
    private ItemViewClick mItemClick;

    public RssRecycleAdapter(List<RssPost> rssItems, ItemViewClick itemClick) {
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
        RssPost item = mRssItems.get(position);
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
        private RssPost mItem;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mItemTitle = itemView.findViewById(R.id.itemTitle);
            mItemDescription = itemView.findViewById(R.id.itemDescription);
            mItemLink = itemView.findViewById(R.id.itemLink);
        }

        public void bindRssItem(RssPost rssItem, ItemViewClick itemClick) {
            mItem = rssItem;
            mItemClick = itemClick;
            mItemTitle.setText(rssItem.getTitle());
            mItemDescription.setText(rssItem.getDescription());
            mItemLink.setText(rssItem.getLink());
            itemView.setOnClickListener((view) -> mItemClick.handleClick(mItem));
        }
    }
}
