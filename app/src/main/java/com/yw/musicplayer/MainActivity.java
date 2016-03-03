package com.yw.musicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yw.musicplayer.adapter.MusicListAdapter;
import com.yw.musicplayer.po.Audio;
import com.yw.musicplayer.util.MediaUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView musicListView;
    private MusicListAdapter musicListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        final List<Audio> mAudioList = MediaUtils.getAudioList(this);
        musicListView = (ListView) findViewById(R.id.list);
        musicListAdapter = new MusicListAdapter(this, (ArrayList<Audio>) mAudioList);
        musicListView.setAdapter(musicListAdapter);

        musicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                Intent mIntent = new Intent(MainActivity.this, MusicPlayerActivity.class);
                mIntent.putExtra("AudioList", (Serializable) mAudioList);
                startActivity(mIntent);
            }
        });
    }


}

