package com.niharika.android.githubbrowser.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.niharika.android.githubbrowser.Model.Branch;
import com.niharika.android.githubbrowser.Model.BranchCommit;
import com.niharika.android.githubbrowser.Model.Repo;
import com.niharika.android.githubbrowser.repository.RepoRepository;


import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BranchCommitViewModel extends ViewModel {
    private MutableLiveData<List<BranchCommit>> mCommitList;
    @Inject
    RepoRepository mRepoRepository;

    @Inject
    public BranchCommitViewModel() {
        mCommitList = new MutableLiveData();
    }

    public LiveData<List<BranchCommit>> getCommitList(Branch branch,Repo repo) {
        mRepoRepository.getCommitList(branch,repo,mCommitList);
        return mCommitList;
    }
}