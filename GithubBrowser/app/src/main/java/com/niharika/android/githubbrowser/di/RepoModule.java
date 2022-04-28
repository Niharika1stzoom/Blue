package com.niharika.android.githubbrowser.di;

import android.content.Context;

import com.niharika.android.githubbrowser.network.GithubApiInterface;
import com.niharika.android.githubbrowser.repository.RepoRepository;
import com.niharika.android.githubbrowser.room.RepoDao;
import com.niharika.android.githubbrowser.room.RepoRoomDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class RepoModule {

    @Singleton
    @Provides
    RepoRepository provideRepository(RepoDao dao, GithubApiInterface apiInterface){
        return new RepoRepository(dao,apiInterface);
    }
}
