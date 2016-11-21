package com.yw.musicplayer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yw.musicplayer.BillboardItemFragment;
import com.yw.musicplayer.NameItemFragment;
import com.yw.musicplayer.R;
import com.yw.musicplayer.po.Audio;
import com.yw.musicplayer.po.BaiduMHotList;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Audio} and makes a call to the
 * specified {@link NameItemFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class NetMusicItemRecyclerViewAdapter extends RecyclerView.Adapter<NetMusicItemRecyclerViewAdapter.ViewHolder> {

    public void setmValues(List<BaiduMHotList.SongListEntity> mValues) {
        this.mValues = mValues;
        notifyDataSetChanged();
    }

    private List<BaiduMHotList.SongListEntity> mValues;
    private final BillboardItemFragment.OnListFragmentInteractionListener mListener;

    public NetMusicItemRecyclerViewAdapter(List<BaiduMHotList.SongListEntity> items, BillboardItemFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_name_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.title.setText(holder.mItem.getTitle());
        holder.description.setText(holder.mItem.getTitle());


        Glide.with(holder.mView.getContext())
                .load(holder.mItem.getPic_big())
                .centerCrop()
                .placeholder(R.drawable.icon_play)
                .crossFade()
                .into(holder.playEq);


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView playEq;
        public final TextView title;
        public final TextView description;
        public BaiduMHotList.SongListEntity mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            playEq = (ImageView) view.findViewById(R.id.play_eq);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + title.getText() + "'";
        }
    }
}