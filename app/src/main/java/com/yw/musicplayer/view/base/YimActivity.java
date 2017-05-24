package com.yw.musicplayer.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yw.musicplayer.App;
import com.yw.musicplayer.internal.di.components.ApplicationComponent;
import com.yw.musicplayer.internal.di.module.ActivityModule;
import com.yw.musicplayer.uiframework.base.BaseActivity;

/**
 * Created by wengyiming on 2017/5/5.
 */

public abstract class YimActivity extends BaseActivity {
    private ActivityModule mActivityModule;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);

    }

    /**
     * Get the Main Application component for dependency injection.
     *
     * @return {@link ApplicationComponent}
     */
    protected ApplicationComponent getApplicationComponent() {
        return ((App) getApplication()).getApplicationComponent();
    }

    /**
     * Get an Activity module for dependency injection.
     *
     * @return {@link ActivityModule}
     */
    protected ActivityModule getActivityModule() {
        if (mActivityModule == null) {
            mActivityModule = new ActivityModule(this);
        }
        return mActivityModule;
    }

}
