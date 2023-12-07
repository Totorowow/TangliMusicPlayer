
package com.tangli.musicplayer.adapter;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;


import com.tangli.musicplayer.R;
import com.tangli.musicplayer.databinding.ContentListItemBinding;
import com.tangli.musicplayer.music.MusicContent;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final List<MusicContent.MusicItem> mValues;

    public RecyclerViewAdapter(List<MusicContent.MusicItem> items) {
        mValues = items;
    }
    private ContentListItemBinding itemBinding;
    private OnItemClickListener mOnItemClickListener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemBinding=ContentListItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mCoverView.setImageResource(holder.mItem.getCover());
        holder.mTitleView.setText(holder.mItem.getTitle());
        holder.mArtistView.setText(holder.mItem.getArtist());
        holder.mDurationView.setText(DateUtils.formatElapsedTime(holder.mItem.getDuration()));
        holder.mView.setOnClickListener(v -> {
            if (null != mOnItemClickListener) {
                mOnItemClickListener.onItemClick();
            }
        });

        holder.mView.setOnLongClickListener(v -> {
            if (null != mOnItemClickListener) {
                mOnItemClickListener.onItemLongClick();
                return true;
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mCoverView;
        public final TextView mTitleView;
        public final TextView mArtistView;
        public final TextView mDurationView;
        public MusicContent.MusicItem mItem;

        public ViewHolder(ContentListItemBinding binding) {
            super(binding.getRoot());
            mView = binding.getRoot();
            mCoverView = binding.cover;
            mTitleView = binding.title;
            mArtistView = binding.artist;
            mDurationView = binding.duration;
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        public void onItemClick();
        public void onItemLongClick();
    }


}
