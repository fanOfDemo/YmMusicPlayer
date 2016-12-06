package com.yw.musicplayer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.squareup.picasso.Picasso;
import com.yw.musicplayer.adapter.HomeViewPagerAdapter;
import com.yw.musicplayer.po.BaiduMHotList;
import com.yw.musicplayer.service.ApiService;
import com.yw.musicplayer.service.MusicApi;
import com.yw.musicplayer.util.StatusBarCompat;
import com.yw.musicplayer.view.widget.DragExpendLayout;
import com.yw.musicplayer.view.widget.MyAppBar;
import com.yw.musicplayer.view.widget.MyViewPager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NameItemFragment.OnListFragmentInteractionListener, NetMusicListFragment.OnListFragmentInteractionListener {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.rollviewpager)
    RollPagerView mRollviewpager;
    @Bind(R.id.appbar)
    MyAppBar mAppbar;
    @Bind(R.id.id_tab_view)
    TabLayout mIdTabView;
    @Bind(R.id.id_viewpager)
    MyViewPager mIdViewpager;
    @Bind(R.id.dragexpendview)
    DragExpendLayout mDragexpendview;
    @Bind(R.id.nav_view)
    NavigationView mNavView;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    private TestLoopAdapter mLoopAdapter;


    private int mAppbarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        StatusBarCompat.compat(this);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavView.setNavigationItemSelectedListener(this);
        mRollviewpager.setAdapter(mLoopAdapter = new TestLoopAdapter(mRollviewpager));
        final HomeViewPagerAdapter mPagerAdapter = new HomeViewPagerAdapter(getSupportFragmentManager());
        mIdViewpager.setAdapter(mPagerAdapter);
        mIdTabView.setupWithViewPager(mIdViewpager);

        mDragexpendview.setDismissOffset(mAppbar.getMeasuredHeight());
        mIdViewpager.setEnabled(mDragexpendview.isExpanded());
        mDragexpendview.registerCallback(new DragExpendLayout.Callbacks() {
            private int dy;

            @Override
            public void onSheetExpanded() {
                mAppbar.onDispatchUp();
                mAppbar.setTranslationY(0);
                mAppbar.setVisibility(View.GONE);
                mIdTabView.setTranslationY(-mAppbar.getHeight());
                mIdTabView.setVisibility(View.VISIBLE);
                mAppbar.setScaleX(1.f);
                mAppbar.setScaleY(1.f);
                mIdViewpager.setScrollable(true);
                dy = 0;
            }

            @Override
            public void onSheetNarrowed() {
                mAppbar.onDispatchUp();
                mAppbar.setTranslationY(0);
                mAppbar.setScaleX(1.f);
                mAppbar.setScaleY(1.f);
                mIdTabView.setVisibility(View.GONE);
                mIdViewpager.setScrollable(false);
                mAppbar.setVisibility(View.VISIBLE);
                dy = 0;

            }

            @Override
            public void onSheetPositionChanged(int sheetTop, float currentX, int ddy, boolean reverse) {
                if (mAppbarHeight == 0) {
                    mAppbarHeight = mAppbar.getHeight();
                    mDragexpendview.setDismissOffset(mAppbarHeight);
                }
                this.dy += ddy;
                float fraction = 1 - sheetTop * 1.0f / mAppbarHeight;
                if (!reverse) {
                    if (fraction >= 0 && !mDragexpendview.isExpanded()) {//向上拉
                        mIdTabView.setVisibility(View.VISIBLE);
                        mDragexpendview.setTabHeight(mIdTabView.getHeight());
                        mAppbar.setTranslationY(dy * 0.2f);
                        mIdTabView.setTranslationY(-fraction * (mAppbar.getHeight() + mIdTabView.getHeight()));
                    } else if (fraction < 0 && !mDragexpendview.isExpanded()) {//向下拉
                        mIdTabView.setVisibility(View.GONE);
                        mAppbar.onDispatch(currentX, dy);
                        mAppbar.setScaleX(1 - fraction * 0.5f);
                        mAppbar.setScaleY(1 - fraction * 0.5f);
                    }
                }
            }
        });
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }



    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (mDragexpendview.isExpanded()) {
            mDragexpendview.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(int position) {
        Intent mIntent = new Intent(HomeActivity.this, MusicPlayerActivity.class);
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
            Picasso.with(HomeActivity.this)
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


    protected CompositeSubscription subscription = new CompositeSubscription();

    private void getGameList() {
        subscription.add(ApiService.getInstance().createApi(MusicApi.class).login(5, 1)
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
