package com.yw.musicplayer.domain.repository;

import com.yw.musicplayer.domain.model.BaiduMHotList;

import io.reactivex.Observable;

/**
 * Created by wengyiming on 2017/5/5.
 */

public interface GameRepository {

    Observable<BaiduMHotList> getList(int size, int type, int offset);

}
