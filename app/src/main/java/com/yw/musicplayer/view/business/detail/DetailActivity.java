package com.yw.musicplayer.view.business.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.squareup.picasso.Picasso;
import com.yw.musicplayer.R;
import com.yw.musicplayer.domain.model.BaiduMHotList;
import com.yw.musicplayer.view.NameItemFragment;
import com.yw.musicplayer.view.adapter.HomeViewPagerAdapter;
import com.yw.musicplayer.view.business.home.OnlineMusicFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：YmMusicPlayer
 * 类描述：
 * 创建人：wengyiming
 * 创建时间：2016/11/21 15:18
 * 修改人：wengyiming
 * 修改时间：2016/11/21 15:18
 * 修改备注：
 */

public class DetailActivity extends AppCompatActivity implements NameItemFragment.OnListFragmentInteractionListener, OnlineMusicFragment.OnListFragmentInteractionListener {
    private TestLoopAdapter mLoopAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TabLayout tableLayout = (TabLayout) findViewById(R.id.id_tab_view);
        ViewPager viewPager = (ViewPager) findViewById(R.id.id_viewpager);
        RollPagerView rollPagerView = (RollPagerView) findViewById(R.id.rollviewpager);
        rollPagerView.setAdapter(mLoopAdapter = new TestLoopAdapter(rollPagerView));

        ArrayList<Fragment> fragments = getFragments();

        viewPager.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager()));
        tableLayout.setupWithViewPager(viewPager);
    }


    private void getGameList() {


    }

    @NonNull
    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(NameItemFragment.newInstance(1));
        fragments.add(NameItemFragment.newInstance(2));
        fragments.add(NameItemFragment.newInstance(1));
        return fragments;
    }

    @Override
    public void onListFragmentInteraction(int position) {
        Intent mIntent = new Intent(DetailActivity.this, MusicPlayerActivity.class);
        mIntent.putExtra("position", position);
        startActivity(mIntent);
    }

    private class TestLoopAdapter extends LoopPagerAdapter {
        List<BaiduMHotList.SongListEntity> a;

        public void setImgs(List<BaiduMHotList.SongListEntity> a) {
            this.a = a;
            notifyDataSetChanged();
        }


        public TestLoopAdapter(RollPagerView viewPager) {
            super(viewPager);
        }

        @Override
        public View getView(ViewGroup container, int position) {
            Log.i("RollViewPager", "getView:" + a);

            ImageView view = new ImageView(container.getContext());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("RollViewPager", "onClick");
                }
            });
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            Picasso.with(DetailActivity.this)
                    .load(a.get(position).getPic_big())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(view);
            return view;
        }

        @Override
        public int getRealCount() {
            return a == null ? 0 : a.size();
        }

    }


}
