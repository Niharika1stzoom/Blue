package com.niharika.android.githubbrowser.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.niharika.android.githubbrowser.Model.Branch;
import com.niharika.android.githubbrowser.Model.BranchCommit;
import com.niharika.android.githubbrowser.Model.Repo;
import com.niharika.android.githubbrowser.network.GithubApiInterface;
import com.niharika.android.githubbrowser.repository.RepoRepository;
import com.niharika.android.githubbrowser.room.RepoDao;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BranchCommitViewModel extends ViewModel {
    @Inject
    RepoDao mRepoDao;
    private MutableLiveData<List<BranchCommit>> mCommitList;
    @Inject
    GithubApiInterface mApiInterface;

    @Inject
    public BranchCommitViewModel() {
        mCommitList = new MutableLiveData();
    }

    public LiveData<List<BranchCommit>> getCommitList(Branch branch,Repo repo) {
        RepoRepository repoRepository = new RepoRepository(mRepoDao,mApiInterface);
        repoRepository.getCommitList(branch,repo,mCommitList);
        return mCommitList;
    }
}