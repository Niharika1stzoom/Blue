package com.niharika.android.githubbrowser.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.niharika.android.githubbrowser.AppExecutors;
import com.niharika.android.githubbrowser.Model.Branch;
import com.niharika.android.githubbrowser.Model.BranchCommit;
import com.niharika.android.githubbrowser.Model.Issue;
import com.niharika.android.githubbrowser.Model.Repo;
import com.niharika.android.githubbrowser.network.GithubApiInterface;
import com.niharika.android.githubbrowser.room.BaseRepo;
import com.niharika.android.githubbrowser.room.RepoDao;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RepoRepository {
    public RepoDao mRepoDao;
    private LiveData<List<BaseRepo>> mRepoList;
    private GithubApiInterface mApiInterface;

    public RepoRepository(RepoDao repoDao, GithubApiInterface apiInterface) {
        mRepoDao = repoDao;
        mApiInterface = apiInterface;
    }

    public void populateRepoListDb() {
        mRepoList = mRepoDao.getRepoList();
    }

    public void insertRepo(BaseRepo repo) {
        AppExecutors.databaseWriteExecutor.execute(() ->
                mRepoDao.insert(repo)
        );
    }

    public LiveData<List<BaseRepo>> getRepoList() {
        if (mRepoList == null)
            populateRepoListDb();
        return mRepoList;
    }

    //validate if the repo exist and den adds to room db
    public void addRepo(String repoName, String owner, MutableLiveData<Repo> liveData) {
        Call<Repo> call = mApiInterface.getRepo(owner, repoName);
        call.enqueue(new Callback<Repo>() {
            @Override
            public void onResponse(@NonNull Call<Repo> call, @NonNull Response<Repo> response) {
                if (response.isSuccessful()) {
                    Repo repo = response.body();
                    liveData.postValue(repo);
                    //insert in database
                    insertRepo(new BaseRepo(repo.getName(), repo.getDescription(), repo.getOwner().getLogin()));
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Repo> call, @NonNull Throwable t) {
                liveData.postValue(null);
            }
        });
    }

    //gets details of a specific repo
    public void getRepo(String repoName, String owner, MutableLiveData<Repo> liveData) {
        Call<Repo> call = mApiInterface.getRepo(owner, repoName);
        call.enqueue(new Callback<Repo>() {
            @Override
            public void onResponse(@NonNull Call<Repo> call, @NonNull Response<Repo> response) {
                if (response.isSuccessful()) {
                    Repo repo = response.body();
                    liveData.postValue(repo);
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Repo> call, @NonNull Throwable t) {
                liveData.postValue(null);
            }
        });
    }

    //del repo from database
    public void delRepo(BaseRepo repo) {
        AppExecutors.databaseWriteExecutor.execute(() ->
                mRepoDao.deleteRepo(repo));

    }

    //Gives the open issues in a repository
    public void getOpenIssueList(Repo repo, MutableLiveData<List<Issue>> liveData) {
        Call<List<Issue>> call = mApiInterface.getOpenIssues(repo.getOwner().getLogin(), repo.getName());
        call.enqueue(new Callback<List<Issue>>() {
            @Override
            public void onResponse(@NonNull Call<List<Issue>> call, @NonNull Response<List<Issue>> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Issue>> call, @NonNull Throwable t) {
                liveData.postValue(null);
            }
        });
    }

    //Gives the branches in a repository
    public void getBranchList(Repo repo, MutableLiveData<List<Branch>> liveData) {
        Call<List<Branch>> call = mApiInterface.getBranches(repo.getOwner().getLogin(), repo.getName());
        call.enqueue(new Callback<List<Branch>>() {
            @Override
            public void onResponse(@NonNull Call<List<Branch>> call, @NonNull Response<List<Branch>> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Branch>> call, @NonNull Throwable t) {
                liveData.postValue(null);
            }
        });
    }

    //Gives the commits in a branch
    public void getCommitList(Branch branch, Repo repo, MutableLiveData<List<BranchCommit>> liveData) {
        Call<List<BranchCommit>> call = mApiInterface.
                getCommitsByBranch(repo.getOwner().getLogin(), repo.getName(),
                        branch.getCommit().getSha());
        call.enqueue(new Callback<List<BranchCommit>>() {
            @Override
            public void onResponse(@NonNull Call<List<BranchCommit>> call, @NonNull Response<List<BranchCommit>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null)
                        liveData.postValue(response.body());
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<BranchCommit>> call, @NonNull Throwable t) {
                liveData.postValue(null);
            }
        });
    }
}
