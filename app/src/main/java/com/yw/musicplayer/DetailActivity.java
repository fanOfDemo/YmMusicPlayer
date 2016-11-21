package com.yw.musicplayer;

import android.annotation.SuppressLint;
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

import com.bumptech.glide.Glide;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.yw.musicplayer.adapter.HomeViewPagerAdapter;
import com.yw.musicplayer.po.BaiduMHotList;
import com.yw.musicplayer.service.ApiService;
import com.yw.musicplayer.service.MusicApi;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 项目名称：YmMusicPlayer
 * 类描述：
 * 创建人：wengyiming
 * 创建时间：2016/11/21 15:18
 * 修改人：wengyiming
 * 修改时间：2016/11/21 15:18
 * 修改备注：
 */

public class DetailActivity extends AppCompatActivity implements NameItemFragment.OnListFragmentInteractionListener, BillboardItemFragment.OnListFragmentInteractionListener {
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

        viewPager.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager(), fragments));
        tableLayout.setupWithViewPager(viewPager);
    }


    private void getGameList() {
        subscription.add(ApiService.getInstance().createApi(MusicApi.class).login(5,1)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaiduMHotList>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void call(BaiduMHotList t) {
                        if (t == null) return;
                        if (t.getError_code() == 22000) {
                            List<BaiduMHotList.SongListEntity> a = t.getSong_list();
                            Log.e("", a.toString());
                            mLoopAdapter.setImgs(a);
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                }));
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
            Glide.with(DetailActivity.this)
                    .load(a.get(position).getPic_big())
                    .fitCenter()
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

    protected CompositeSubscription subscription = new CompositeSubscription();

    @Override
    protected void onResume() {
        super.onResume();
        subscription = new CompositeSubscription();
        getGameList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        subscription.unsubscribe();
    }


}
