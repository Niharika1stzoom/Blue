package com.niharika.android.githubbrowser.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.niharika.android.githubbrowser.Model.Branch;
import com.niharika.android.githubbrowser.Model.Repo;
import com.niharika.android.githubbrowser.network.GithubApiInterface;
import com.niharika.android.githubbrowser.repository.RepoRepository;
import com.niharika.android.githubbrowser.room.RepoDao;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BranchListViewModel extends ViewModel {
    private MutableLiveData<List<Branch>> mBranchList;
    @Inject
    RepoRepository mRepoRepository;

    @Inject
    public BranchListViewModel() {
        mBranchList = new MutableLiveData();
    }

    public LiveData<List<Branch>> getBranchList(Repo repo) {
        mRepoRepository.getBranchList(repo,mBranchList);
        return mBranchList;
    }
}