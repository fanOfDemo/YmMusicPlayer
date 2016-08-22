package com.yw.musicplayer;

import com.yw.musicplayer.adapter.MusicListAdapter;
import com.yw.musicplayer.po.Audio;
import com.yw.musicplayer.util.MediaUtils;
import com.yw.musicplayer.view.widget.DefaultHeader;
import com.yw.musicplayer.view.widget.RefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RefreshLayout mRefreshLayout;
    private ListView musicListView;
    private MusicListAdapter musicListAdapter;
    private List<Audio> mAudioList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        mRefreshLayout = (RefreshLayout) findViewById(R.id.refreshlayout);
        musicListView = (ListView) findViewById(R.id.list);
        musicListAdapter = new MusicListAdapter(MainActivity.this, (ArrayList<Audio>) mAudioList);
        musicListView.setAdapter(musicListAdapter);
        musicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                Intent mIntent = new Intent(MainActivity.this, MusicPlayerActivity.class);
                mIntent.putExtra("position", position);
                startActivity(mIntent);
            }
        });
        mRefreshLayout.setType(RefreshLayout.Type.FOLLOW);
        mRefreshLayout.setHeader(new DefaultHeader(this));
        mRefreshLayout.setListener(new RefreshLayout.OnFreshListener() {
            @Override
            public void onRefresh() {
                reLoadList();
            }

            @Override
            public void onLoadmore() {

            }
        });
        reLoadList();
        mRefreshLayout.callFresh();
    }

    private void reLoadList() {
        mAudioList = MediaUtils.getAudioList(MainActivity.this);
        musicListAdapter.musicInfos = (ArrayList<Audio>) mAudioList;
        musicListAdapter.notifyDataSetChanged();
        mRefreshLayout.onFinishFreshAndLoad();
    }


}

