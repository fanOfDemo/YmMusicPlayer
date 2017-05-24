
package com.yw.musicplayer.internal.di.module;

import com.yw.musicplayer.data.network.ApiService;
import com.yw.musicplayer.data.network.api.MusicApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApiModule {

    @Provides
    @Singleton
    MusicApi getUserApi() {
        return createApi(MusicApi.class);
    }



    private <T> T createApi(Class<T> clazz) {
        return ApiService.getInstance().createApi(clazz);
    }

}
