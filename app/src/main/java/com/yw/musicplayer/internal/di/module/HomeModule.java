
package com.yw.musicplayer.internal.di.module;

import com.yw.musicplayer.domain.executor.PostExecutionThread;
import com.yw.musicplayer.domain.executor.ThreadExecutor;
import com.yw.musicplayer.domain.interactor.GameListUseCase;
import com.yw.musicplayer.domain.repository.GameRepository;
import com.yw.musicplayer.internal.di.YmActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {


    public HomeModule() {
    }

    @Provides
    @YmActivity
    GameListUseCase provideGameListUseCase(ThreadExecutor threadExecutor,
                                            PostExecutionThread postExecutionThread,
                                            GameRepository homeRepository) {
        return new GameListUseCase(threadExecutor, postExecutionThread, homeRepository);
    }

}
