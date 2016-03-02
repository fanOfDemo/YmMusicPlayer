/*
 * @wengyiming
 */
package com.yw.musicplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yw.musicplayer.R;
import com.yw.musicplayer.po.Audio;

import java.util.ArrayList;

public class MusicListAdapter extends BaseAdapter {

    private ArrayList<Audio> musicInfos;
    private LayoutInflater inflater;

    public MusicListAdapter(Context mcContext, ArrayList<Audio> musicInfos) {
        this.musicInfos = musicInfos;
        inflater = LayoutInflater.from(mcContext);
    }

    @Override
    public int getCount() {
        return musicInfos.size();
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
            v = inflater.inflate(R.layout.musiclist_row, null);
            holderView.music_name = (TextView) v
                    .findViewById(R.id.music_name);
            holderView.music_artist = (TextView) v
                    .findViewById(R.id.music_artist);
            holderView.music_filesize = (TextView) v
                    .findViewById(R.id.music_filesize);
            v.setTag(holderView);
        } else {
            holderView = (HolderView) v.getTag();
        }
        final Audio musicInfo = musicInfos.get(position);
        holderView.music_name.setText(musicInfo.getTitle());
        holderView.music_artist.setText(musicInfo.getArtist());
//        holderView.music_filesize.setText(musicInfo.getSize());
        return v;
    }

    static class HolderView {

        TextView music_name;
        TextView music_artist;
        TextView music_filesize;
    }

}
