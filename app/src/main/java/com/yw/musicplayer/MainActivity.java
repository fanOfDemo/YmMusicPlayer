package com.yw.musicplayer;

import com.yw.musicplayer.adapter.MusicListAdapter;
import com.yw.musicplayer.po.Audio;
import com.yw.musicplayer.util.MediaUtils;
import com.yw.musicplayer.view.widget.DefaultHeader;
import com.yw.musicplayer.view.widget.RefreshLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSiON_REQUEST_CODE = 123;
    private RefreshLayout mRefreshLayout;
    private ListView musicListView;
    private MusicListAdapter musicListAdapter;
    private List<Audio> mAudioList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        checkPerssion();
    }

    private void reLoadList() {
        mAudioList = MediaUtils.getAudioList(MainActivity.this);
        musicListAdapter.musicInfos = (ArrayList<Audio>) mAudioList;
        musicListAdapter.notifyDataSetChanged();
        mRefreshLayout.onFinishFreshAndLoad();
    }


    private void checkPerssion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.MODIFY_AUDIO_SETTINGS
                    },
                    PERMISSiON_REQUEST_CODE);
        } else {
            reLoadList();
            mRefreshLayout.callFresh();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSiON_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
                } else {
                    //权限被拒绝，程序无法继续运行
                    finish();
                }
            }
        }
    }
}

