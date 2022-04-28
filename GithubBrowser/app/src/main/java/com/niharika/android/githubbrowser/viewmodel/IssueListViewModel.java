package com.niharika.android.githubbrowser.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.niharika.android.githubbrowser.Model.Issue;
import com.niharika.android.githubbrowser.Model.Repo;
import com.niharika.android.githubbrowser.network.GithubApiInterface;
import com.niharika.android.githubbrowser.repository.RepoRepository;
import com.niharika.android.githubbrowser.room.RepoDao;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class IssueListViewModel extends ViewModel {
    private MutableLiveData<List<Issue>> mIssueList;
    @Inject
    RepoRepository mRepoRepository;
    @Inject
    public IssueListViewModel() {
        mIssueList = new MutableLiveData();
    }

    public LiveData<List<Issue>> getOpenIssuesList(Repo repo) {
        mRepoRepository.getOpenIssueList(repo,mIssueList);
        return mIssueList;
    }
}