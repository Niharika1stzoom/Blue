package com.niharika.android.githubbrowser.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.niharika.android.githubbrowser.Model.Repo;
import com.niharika.android.githubbrowser.repository.RepoRepository;
import com.niharika.android.githubbrowser.room.BaseRepo;


import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RepoDetailViewModel extends ViewModel {
    public MutableLiveData<Repo> liveData;
    @Inject
    RepoRepository mRepoRepository;

    @Inject
    public RepoDetailViewModel() {
        liveData = new MutableLiveData();
    }

    public void getRepo(String repoName, String owner) {
        mRepoRepository.getRepo(repoName, owner, liveData);
    }

    public void delRepo(BaseRepo repo) {
        mRepoRepository.delRepo(repo);
    }

    public LiveData<Repo> getRepoLiveData() {
        return liveData;
    }
}