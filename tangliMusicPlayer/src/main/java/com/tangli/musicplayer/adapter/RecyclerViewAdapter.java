
package com.tangli.musicplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.tangli.musicplayer.bean.Coconut;
import com.tangli.musicplayer.databinding.ContentListItemBinding;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final List<Coconut> mValues;

    public RecyclerViewAdapter(List<Coconut> items) {
        mValues = items;
    }
    private ContentListItemBinding itemBinding;
    private OnItemClickListener mOnItemClickListener;
    private Context context;



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemBinding=ContentListItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        context=parent.getContext();
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mCoverView.setImageResource(holder.mItem.getCover());
        holder.mTitleView.setText(holder.mItem.getName());
        holder.mArtistView.setText(holder.mItem.getArtist());
        RequestOptions options = RequestOptions
                .bitmapTransform(new RoundedCorners(20));
        Glide.with(context)
                .load(holder.mItem.getCover())
                .apply(options)
                .into(holder.mCoverView);
        holder.mView.setOnClickListener(v -> {
            if (null != mOnItemClickListener) {
                mOnItemClickListener.onItemClick(position);
            }
        });

        holder.mView.setOnLongClickListener(v -> {
            if (null != mOnItemClickListener) {
                mOnItemClickListener.onItemLongClick(position);
                return true;
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return mValues!=null ? mValues.size() : 0;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mCoverView;
        public final TextView mTitleView;
        public final TextView mArtistView;
        public Coconut mItem;

        public ViewHolder(ContentListItemBinding binding) {
            super(binding.getRoot());
            mView = binding.getRoot();
            mCoverView = binding.cover;
            mTitleView = binding.title;
            mArtistView = binding.artist;

        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        public void onItemClick(int position);
        public void onItemLongClick(int position);
    }


}
