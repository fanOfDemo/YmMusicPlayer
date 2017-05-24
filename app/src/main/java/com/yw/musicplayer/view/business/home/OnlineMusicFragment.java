package com.yw.musicplayer.view.business.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yw.musicplayer.R;
import com.yw.musicplayer.domain.interactor.GameListUseCase;
import com.yw.musicplayer.domain.model.BaiduMHotList;
import com.yw.musicplayer.view.adapter.NetMusicItemRecyclerViewAdapter;
import com.yw.musicplayer.view.base.BaseFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class OnlineMusicFragment extends BaseFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private NetMusicItemRecyclerViewAdapter netMusicItemRecyclerViewAdapter;
    private BaiduMHotList baiduMHotList;

    @Inject
    GameListUseCase mGameListUseCase;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OnlineMusicFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static OnlineMusicFragment newInstance(int columnCount) {
        OnlineMusicFragment fragment = new OnlineMusicFragment();
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        ((HomeActivity)getActivity()).getComponent().inject(this);
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_name_item_list, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            netMusicItemRecyclerViewAdapter =
                    new NetMusicItemRecyclerViewAdapter(new ArrayList<BaiduMHotList.SongListEntity>());
            recyclerView.setAdapter(netMusicItemRecyclerViewAdapter);

            netMusicItemRecyclerViewAdapter.setListener(new OnListFragmentInteractionListener() {
                @Override
                public void onListFragmentInteraction(int item) {

                }
            });
        }
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        getGameList();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void getGameList() {
        if (baiduMHotList == null || baiduMHotList.getSong_list() == null || baiduMHotList.getSong_list().isEmpty()) {
            mGameListUseCase.setSize(20, mColumnCount, 0);
            mGameListUseCase.execute(new Observer<BaiduMHotList>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull BaiduMHotList baiduMHotList) {
                    OnlineMusicFragment.this.baiduMHotList = baiduMHotList;
                    netMusicItemRecyclerViewAdapter.updateItems(baiduMHotList.getSong_list());
                }

                @Override
                public void onError(@NonNull Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
        } else {
            netMusicItemRecyclerViewAdapter.updateItems(baiduMHotList.getSong_list());
        }


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
