
package com.yw.musicplayer.internal.di.components;

import android.content.Context;

import com.yw.musicplayer.domain.executor.PostExecutionThread;
import com.yw.musicplayer.domain.executor.ThreadExecutor;
import com.yw.musicplayer.domain.repository.GameRepository;
import com.yw.musicplayer.internal.di.module.ApiModule;
import com.yw.musicplayer.internal.di.module.ApplicationModule;
import com.yw.musicplayer.internal.di.module.RepositoryModule;
import com.yw.musicplayer.uiframework.base.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, ApiModule.class , RepositoryModule.class})
public interface ApplicationComponent {

    void inject(BaseActivity baseActivity);

    // Exposed to sub-graphs.
    Context context();



    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();

    GameRepository gameRepository();

}
