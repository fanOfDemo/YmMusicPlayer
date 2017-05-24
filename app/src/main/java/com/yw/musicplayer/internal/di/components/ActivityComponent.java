
package com.yw.musicplayer.internal.di.components;

import android.app.Activity;

import com.yw.musicplayer.internal.di.YmActivity;
import com.yw.musicplayer.internal.di.module.ActivityModule;

import dagger.Component;

/**
 * A base component upon which fragment's components may depend.
 * Activity-level components should extend this component.
 * <p>
 * Subtypes of ActivityComponent should be decorated with annotation:
 * {@link com.yw.musicplayer.internal.di.YmActivity}
 */
@YmActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    // Exposed to sub-graphs.
    Activity activity();
}
