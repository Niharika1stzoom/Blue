package com.niharika.android.githubbrowser.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.niharika.android.githubbrowser.Model.Repo;
import com.niharika.android.githubbrowser.network.GithubApiInterface;
import com.niharika.android.githubbrowser.repository.RepoRepository;
import com.niharika.android.githubbrowser.room.BaseRepo;
import com.niharika.android.githubbrowser.room.RepoDao;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RepoDetailViewModel extends ViewModel {
    public MutableLiveData<Repo> liveData;
    @Inject
    RepoDao mRepoDao;
    @Inject
    GithubApiInterface mApiInterface;

    @Inject
    public RepoDetailViewModel() {
        liveData = new MutableLiveData();
    }

    public void getRepo(String repoName, String owner) {
        RepoRepository repoRepository = new RepoRepository(mRepoDao, mApiInterface);
        repoRepository.getRepo(repoName, owner, liveData);
    }

    public void delRepo(BaseRepo repo) {
        RepoRepository repoRepository = new RepoRepository(mRepoDao, mApiInterface);
        repoRepository.delRepo(repo);
    }

    public LiveData<Repo> getRepoLiveData() {
        return liveData;
    }
}