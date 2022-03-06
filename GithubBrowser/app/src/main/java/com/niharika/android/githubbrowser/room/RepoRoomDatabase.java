package com.niharika.android.githubbrowser.room;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {BaseRepo.class}, version = 2, exportSchema = false)
public abstract class RepoRoomDatabase extends RoomDatabase {
    private static RepoRoomDatabase sInstance;
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "repo_database";

    public abstract RepoDao repoDao();
    public static RepoRoomDatabase getDatabase(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        RepoRoomDatabase.class, RepoRoomDatabase.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return sInstance;
    }
}
