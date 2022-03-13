package com.niharika.android.githubtrends.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.niharika.android.githubtrends.model.Repo;
import com.niharika.android.githubtrends.repository.RepoRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<Repo>> mRepoList;
    @Inject
    RepoRepository mRepoRepository;

    @Inject
    public HomeViewModel() {
        mRepoList = new MutableLiveData();
    }

    public LiveData<List<Repo>> getRepoList(String language) {
        mRepoRepository.getRepoList(mRepoList,language);
        return mRepoList;
    }
}