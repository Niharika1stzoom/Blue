package com.niharika.android.githubbrowser.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
         void insert(BaseRepo baseRepo);

    @Query("DELETE from repo_table")
        void deleteAll();

    @Delete
    void deleteRepo(BaseRepo baseRepo);

    @Query("Select * from repo_table")
        LiveData<List<BaseRepo>> getRepoList();
}
