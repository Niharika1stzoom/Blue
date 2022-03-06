package com.niharika.android.githubbrowser.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.niharika.android.githubbrowser.network.GithubApiInterface;
import com.niharika.android.githubbrowser.repository.RepoRepository;
import com.niharika.android.githubbrowser.room.BaseRepo;
import com.niharika.android.githubbrowser.room.RepoDao;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LandingRepoViewModel extends ViewModel {
    @Inject
    RepoDao mRepoDao;
    private LiveData<List<BaseRepo>> mRepoList;
    @Inject
    GithubApiInterface mApiInterface;
    @Inject
    public LandingRepoViewModel() {
        mRepoList = new MutableLiveData();
    }

    public LiveData<List<BaseRepo>> getRepoList() {
        RepoRepository repoRepository = new RepoRepository(mRepoDao,mApiInterface);
        mRepoList=repoRepository.getRepoList();
        return mRepoList;
    }

}