package com.yw.musicplayer.data.repository;

import com.yw.musicplayer.data.network.api.MusicApi;
import com.yw.musicplayer.domain.model.BaiduMHotList;
import com.yw.musicplayer.domain.repository.GameRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by wengyiming on 2017/5/5.
 */
@Singleton
public class GameDataRepository implements GameRepository {

    @Inject
    protected MusicApi mMusicApi;


    @Inject
    public GameDataRepository(){

    }

    /**
     * 这里的返回结果可以进行拦截过滤，比如判断是否为空，code是否正确
     * @param size
     * @param type
     * @param offset
     * @return
     */
    public Observable<BaiduMHotList> getList(int size, int type, int offset) {
        return mMusicApi.getList(size,type,offset);
    }


}
