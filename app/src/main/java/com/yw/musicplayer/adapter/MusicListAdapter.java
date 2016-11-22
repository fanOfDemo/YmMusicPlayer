/*
 * @wengyiming
 */
package com.yw.musicplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yw.musicplayer.R;
import com.yw.musicplayer.po.Audio;

import java.util.ArrayList;

public class MusicListAdapter extends BaseAdapter {

    public ArrayList<Audio> musicInfos;
    private LayoutInflater inflater;

    public MusicListAdapter(Context mcContext, ArrayList<Audio> musicInfos) {
        this.musicInfos = musicInfos;
        inflater = LayoutInflater.from(mcContext);
    }

    @Override
    public int getCount() {
        if (musicInfos == null||musicInfos.isEmpty()) {
            return 0;
        }else {
           return musicInfos.size();
        }
    }

    @Override
    public Object getItem(int arg0) {
        return musicInfos.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    public void updateItemView(int position) {
    }

    @Override
    public View getView(final int position, View v, ViewGroup parent) {
        HolderView holderView = null;
        if (v == null) {
            holderView = new HolderView();
            v = inflater.inflate(R.layout.media_list_item, null);
            holderView.title = (TextView) v
                    .findViewById(R.id.title);
            holderView.description = (TextView) v
                    .findViewById(R.id.description);
            holderView.playEq = (ImageView) v
                    .findViewById(R.id.play_eq);
            v.setTag(holderView);
        } else {
            holderView = (HolderView) v.getTag();
        }
        final Audio musicInfo = musicInfos.get(position);
        holderView.title.setText(musicInfo.getTitle());
        holderView.description.setText(musicInfo.getArtist());
        return v;
    }

    static class HolderView {

        ImageView playEq;
        TextView title;
        TextView description;
    }

}
