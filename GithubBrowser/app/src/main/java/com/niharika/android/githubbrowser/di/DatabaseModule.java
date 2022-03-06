package com.niharika.android.roomwordssample.di;

import android.content.Context;


import com.niharika.android.roomwordssample.WordDao;
import com.niharika.android.roomwordssample.WordRoomDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {


    @Provides
    WordDao provideWordDao(WordRoomDatabase database) {
        return database.wordDao();
    }


    @Singleton
    @Provides
    WordRoomDatabase provideDatabase(@ApplicationContext Context context) {
        return WordRoomDatabase.getDatabase(context);

    }
}
