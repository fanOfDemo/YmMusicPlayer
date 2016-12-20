package com.yw.musicplayer.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.yw.musicplayer.NetMusicListFragment;

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

    private ArrayList<NetMusicListFragment> mViewPagerFragments;


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
    private ArrayList<NetMusicListFragment> getFragments() {
        ArrayList<NetMusicListFragment> arrayList = new ArrayList<NetMusicListFragment>();
        arrayList.add(NetMusicListFragment.newInstance(1));
        arrayList.add(NetMusicListFragment.newInstance(2));
        arrayList.add(NetMusicListFragment.newInstance(21));
        arrayList.add(NetMusicListFragment.newInstance(11));
        arrayList.add(NetMusicListFragment.newInstance(12));
        arrayList.add(NetMusicListFragment.newInstance(16));
        arrayList.add(NetMusicListFragment.newInstance(22));
        arrayList.add(NetMusicListFragment.newInstance(23));
        arrayList.add(NetMusicListFragment.newInstance(24));
        arrayList.add(NetMusicListFragment.newInstance(25));
        return arrayList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        ((MyViewPager) container).detachViewFromParent(((Fragment) object).getView());
//        container.invalidate();
    }

    @Override
    public void startUpdate(ViewGroup container) {
//        int count = container.getChildCount();
//        for (int i = 0; i < count; i++) {
//            container.getChildAt(i).forceLayout();
//        }
        super.startUpdate(container);
    }

}
