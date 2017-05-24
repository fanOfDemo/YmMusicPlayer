
package com.yw.musicplayer.internal.di.components;


import com.yw.musicplayer.internal.di.YmActivity;
import com.yw.musicplayer.internal.di.module.ActivityModule;
import com.yw.musicplayer.internal.di.module.HomeModule;
import com.yw.musicplayer.view.business.home.HomeActivity;
import com.yw.musicplayer.view.business.home.OnlineMusicFragment;

import dagger.Component;


@YmActivity
@Component(dependencies = ApplicationComponent.class,
            modules = {ActivityModule.class, HomeModule.class,})
public interface HomeComponent extends ActivityComponent {
    void inject(HomeActivity homeActivity);

    void inject(OnlineMusicFragment fragment);
}
