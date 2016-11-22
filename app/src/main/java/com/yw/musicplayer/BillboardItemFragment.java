package com.yw.musicplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yw.musicplayer.adapter.NetMusicItemRecyclerViewAdapter;
import com.yw.musicplayer.po.BaiduMHotList;
import com.yw.musicplayer.po.MusicData;
import com.yw.musicplayer.service.ApiService;
import com.yw.musicplayer.service.MusicApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class BillboardItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    List<BaiduMHotList.SongListEntity> list = new ArrayList<>();
    private NetMusicItemRecyclerViewAdapter netMusicItemRecyclerViewAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BillboardItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static BillboardItemFragment newInstance(int columnCount) {
        BillboardItemFragment fragment = new BillboardItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_name_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                if (mColumnCount > 1)
                    recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
            }

//            recyclerView.setAdapter(netMusicItemRecyclerViewAdapter = new MyCommonAdapter(getActivity(), R.layout.fragment_name_item, list));
            recyclerView.setAdapter(netMusicItemRecyclerViewAdapter = new NetMusicItemRecyclerViewAdapter(new ArrayList<BaiduMHotList.SongListEntity>(), new OnListFragmentInteractionListener() {
                @Override
                public void onListFragmentInteraction(final int item) {
                    final BaiduMHotList.SongListEntity songListEntity = list.get(item);
                    subscription.add(ApiService.getInstance().createApi(MusicApi.class).play(songListEntity.getSong_id())
                            .observeOn(Schedulers.io())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<MusicData>() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void call(MusicData t) {
                                    if (t == null) return;
                                    if (t.getError_code() == 22000) {
                                        songListEntity.setMusicData(t);
                                        Intent mIntent = new Intent(getActivity(), MusicPlayerActivity.class);
                                        mIntent.putExtra("position", item);
                                        mIntent.putExtra("list", (Serializable) list);
                                        startActivity(mIntent);
                                    }
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                }
                            }));

                }
            }));
//            recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).color(Color.GRAY).sizeResId(R.dimen.divider).marginResId(R.dimen.leftmargin, R.dimen.rightmargin).build());
        }
        return view;
    }

    protected CompositeSubscription subscription = new CompositeSubscription();

    @Override
    public void onResume() {
        super.onResume();
        subscription = new CompositeSubscription();
        getGameList();
    }

    @Override
    public void onPause() {
        super.onPause();
        subscription.unsubscribe();
    }

    private void getGameList() {
        subscription.add(ApiService.getInstance().createApi(MusicApi.class).login(20 * mColumnCount, mColumnCount)
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
                            list = a;
                            netMusicItemRecyclerViewAdapter.updateItems(list);
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                }));


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(int item);
    }
}
