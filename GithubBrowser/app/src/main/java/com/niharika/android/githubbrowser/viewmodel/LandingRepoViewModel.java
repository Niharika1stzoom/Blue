package com.niharika.android.githubbrowser.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.niharika.android.githubbrowser.repository.RepoRepository;
import com.niharika.android.githubbrowser.room.BaseRepo;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LandingRepoViewModel extends ViewModel {
    private LiveData<List<BaseRepo>> mRepoList;
    @Inject
    RepoRepository mRepoRepository;
    @Inject
    public LandingRepoViewModel() {
        mRepoList = new MutableLiveData();
    }

    public LiveData<List<BaseRepo>> getRepoList() {
        mRepoList=mRepoRepository.getRepoList();
        return mRepoList;
    }

}