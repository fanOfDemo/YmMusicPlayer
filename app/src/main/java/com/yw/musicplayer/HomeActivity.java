package com.yw.musicplayer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
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

import com.bumptech.glide.Glide;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.yw.musicplayer.adapter.HomeViewPagerAdapter;
import com.yw.musicplayer.po.BaiduMHotList;
import com.yw.musicplayer.service.ApiService;
import com.yw.musicplayer.service.MusicApi;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NameItemFragment.OnListFragmentInteractionListener, BillboardItemFragment.OnListFragmentInteractionListener {
    private HomeActivity.TestLoopAdapter mLoopAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TabLayout tableLayout = (TabLayout) findViewById(R.id.id_tab_view);
        ViewPager viewPager = (ViewPager) findViewById(R.id.id_viewpager);
        RollPagerView rollPagerView = (RollPagerView) findViewById(R.id.rollviewpager);
        rollPagerView.setAdapter(mLoopAdapter = new HomeActivity.TestLoopAdapter(rollPagerView));


        viewPager.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager()));
        tableLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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
            Glide.with(HomeActivity.this)
                    .load(a.get(position).getPic_small())
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
