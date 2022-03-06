package com.niharika.android.githubbrowser.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.niharika.android.githubbrowser.Model.Branch;
import com.niharika.android.githubbrowser.Model.Repo;
import com.niharika.android.githubbrowser.network.GithubApiInterface;
import com.niharika.android.githubbrowser.room.BaseRepo;
import com.niharika.android.githubbrowser.room.RepoDao;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepoRepository {
    private RepoDao mRepoDao;
    private LiveData<List<BaseRepo>> mRepoList;

    public RepoRepository(RepoDao repoDao) {
        mRepoDao=repoDao;
    }

    public void populateRepoListDb() {

        mRepoList=mRepoDao.getRepoList();
    }

    public LiveData<List<BaseRepo>> getRepoList() {
        return mRepoList;
    }
}
