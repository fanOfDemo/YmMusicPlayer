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
    private String[] ITEM = new String[]{"新歌榜", "热歌榜", "欧美金曲榜",
            "摇滚榜", "爵士", "流行",
            "经典老歌榜", "情歌对唱榜", "影视金曲榜",
            "网络歌曲榜"
    };

    //    1-新歌榜,2-热歌榜,11-摇滚榜,12-爵士,16-流行,21-欧美金曲榜,22-经典老歌榜,23-情歌对唱榜,24-影视金曲榜,25-网络歌曲榜
    public HomeViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public HomeViewPagerAdapter(FragmentManager supportFragmentManager, ArrayList<Fragment> fragments) {
        super(supportFragmentManager);

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return BillboardItemFragment.newInstance(1);
            case 1:
                return BillboardItemFragment.newInstance(2);
            case 2:
                return BillboardItemFragment.newInstance(21);
            case 3:
                return BillboardItemFragment.newInstance(11);
            case 4:
                return BillboardItemFragment.newInstance(12);
            case 5:
                return BillboardItemFragment.newInstance(16);
            case 6:
                return BillboardItemFragment.newInstance(22);
            case 7:
                return BillboardItemFragment.newInstance(23);
            case 8:
                return BillboardItemFragment.newInstance(24);
            case 9:
                return BillboardItemFragment.newInstance(25);
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
