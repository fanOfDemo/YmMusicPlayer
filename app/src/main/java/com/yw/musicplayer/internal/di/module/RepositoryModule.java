
package com.yw.musicplayer.internal.di.module;


import com.yw.musicplayer.data.repository.GameDataRepository;
import com.yw.musicplayer.domain.repository.GameRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    GameRepository provideUserRepository(GameDataRepository userDataRepository) {
        return userDataRepository;
    }


}
