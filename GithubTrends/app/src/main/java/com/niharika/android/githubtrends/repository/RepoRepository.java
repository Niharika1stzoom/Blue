package com.niharika.android.githubtrends.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.niharika.android.githubtrends.model.GithubApiResponse;
import com.niharika.android.githubtrends.model.Repo;
import com.niharika.android.githubtrends.network.GithubApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepoRepository {
    private GithubApiInterface mApiInterface;
    public RepoRepository( GithubApiInterface apiInterface) {
        mApiInterface = apiInterface;
    }

    public void getRepoList( MutableLiveData<List<Repo>> liveData,String language) {
        Call<GithubApiResponse> call = mApiInterface.getRepos("language:"+language);
        call.enqueue(new Callback<GithubApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<GithubApiResponse> call, @NonNull Response<GithubApiResponse> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body().getItems());
                } else {
                    liveData.postValue(null);
                }
            }
            @Override
            public void onFailure(@NonNull Call<GithubApiResponse> call, @NonNull Throwable t) {
                liveData.postValue(null);
            }
        });
    }
}
