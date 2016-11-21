package com.yw.musicplayer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yw.musicplayer.BillboardItemFragment;
import com.yw.musicplayer.NameItemFragment;

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
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] ITEM = new String[]{"本地", "新歌榜", "热歌榜"};

    public HomeViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    public HomeViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return NameItemFragment.newInstance(1);
            case 1:
                return BillboardItemFragment.newInstance(1);
            case 2:
                return BillboardItemFragment.newInstance(2);
        }
        return NameItemFragment.newInstance(1);
    }

    @Override
    public int getCount() {
        return ITEM.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ITEM[position];
    }
}
