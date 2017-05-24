
package com.yw.musicplayer.internal.di.module;

import android.content.Context;

import com.yw.musicplayer.App;
import com.yw.musicplayer.data.executor.JobExecutor;
import com.yw.musicplayer.data.executor.UIThread;
import com.yw.musicplayer.domain.executor.PostExecutionThread;
import com.yw.musicplayer.domain.executor.ThreadExecutor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {

    private final App mApplication;

    public ApplicationModule(App application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return mApplication;
    }


    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }


}
