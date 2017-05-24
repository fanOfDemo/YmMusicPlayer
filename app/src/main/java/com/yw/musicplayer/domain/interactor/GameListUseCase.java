
package com.yw.musicplayer.domain.interactor;

import com.yw.musicplayer.domain.executor.PostExecutionThread;
import com.yw.musicplayer.domain.executor.ThreadExecutor;
import com.yw.musicplayer.domain.model.BaiduMHotList;
import com.yw.musicplayer.domain.repository.GameRepository;

import io.reactivex.Observable;

public class GameListUseCase extends UseCase<BaiduMHotList> {

    private GameRepository mGameRepository;
    private int size,  type,  offset;

    public GameListUseCase(
                           ThreadExecutor threadExecutor,
                           PostExecutionThread postExecutionThread,GameRepository userRepository) {
        super(threadExecutor, postExecutionThread);
        this.mGameRepository = userRepository;
    }

    public void setSize(int size,int type,int offset) {
        this.size = size;
        this.type = type;
        this.offset = offset;
    }


    @Override
    protected Observable<BaiduMHotList> buildUseCaseObservable() {
        return this.mGameRepository.getList(size, type,offset);
    }
}
