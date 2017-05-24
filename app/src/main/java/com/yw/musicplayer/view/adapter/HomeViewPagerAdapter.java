package com.yw.musicplayer.view.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;


import com.yw.musicplayer.view.business.home.OnlineMusicFragment;

import java.util.ArrayList;

/**
 * 项目名称：YmMusicPlayer
 * 类描述：
 * 创建人：wengyiming
 * 创建时间：2016/11/16 14:47
 * 修改人：wengyiming
 * 修改时间：2016/11/16 14:47
 * 修改备注：
 */

public class HomeViewPagerAdapter extends FragmentPagerAdapter {
    public static final String[] ITEM = new String[]{"新歌榜", "热歌榜", "欧美金曲榜",
            "摇滚榜", "爵士", "流行",
            "经典老歌榜", "情歌对唱榜", "影视金曲榜",
            "网络歌曲榜"
    };

    private ArrayList<OnlineMusicFragment> mViewPagerFragments;


    //    1-新歌榜,2-热歌榜,11-摇滚榜,12-爵士,16-流行,21-欧美金曲榜,22-经典老歌榜,23-情歌对唱榜,24-影视金曲榜,25-网络歌曲榜
    public HomeViewPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
        this.mViewPagerFragments = getFragments();

    }

    @Override
    public Fragment getItem(int position) {
        return mViewPagerFragments.get(position);
    }

    @Override
    public int getCount() {
        return ITEM.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ITEM[position];
    }

    @NonNull
    private ArrayList<OnlineMusicFragment> getFragments() {
        ArrayList<OnlineMusicFragment> arrayList = new ArrayList<OnlineMusicFragment>();
        arrayList.add(OnlineMusicFragment.newInstance(1));
        arrayList.add(OnlineMusicFragment.newInstance(2));
        arrayList.add(OnlineMusicFragment.newInstance(21));
        arrayList.add(OnlineMusicFragment.newInstance(11));
        arrayList.add(OnlineMusicFragment.newInstance(12));
        arrayList.add(OnlineMusicFragment.newInstance(16));
        arrayList.add(OnlineMusicFragment.newInstance(22));
        arrayList.add(OnlineMusicFragment.newInstance(23));
        arrayList.add(OnlineMusicFragment.newInstance(24));
        arrayList.add(OnlineMusicFragment.newInstance(25));
        return arrayList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }


    @Override
    public void startUpdate(ViewGroup container) {
        super.startUpdate(container);
    }

}
