package com.niharika.android.githubbrowser.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.niharika.android.githubbrowser.Model.Repo;
import com.niharika.android.githubbrowser.network.GithubApiInterface;
import com.niharika.android.githubbrowser.repository.RepoRepository;
import com.niharika.android.githubbrowser.room.RepoDao;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AddRepoViewModel extends ViewModel {
    MutableLiveData<Repo> liveData;
    @Inject
    RepoRepository mRepoRepository;
    @Inject
    public AddRepoViewModel() { liveData = new MutableLiveData(); }

    public MutableLiveData<Repo> getLiveData() {
        return liveData;
    }

    public void addRepo(String repoName, String owner, MutableLiveData<Repo> repo) {
        mRepoRepository.addRepo(repoName,owner,repo);
    }
}