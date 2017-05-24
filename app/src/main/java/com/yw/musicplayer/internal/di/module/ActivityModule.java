
package com.yw.musicplayer.internal.di.module;

import android.app.Activity;

import com.yw.musicplayer.internal.di.YmActivity;

import dagger.Module;
import dagger.Provides;

/**
 * A module to wrap the Activity state and expose it to the graph.
 */
@Module
public class ActivityModule {

    private final Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides
    @YmActivity
    Activity activity() {
        return mActivity;
    }
}
