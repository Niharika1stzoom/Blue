package com.niharika.android.githubtrends.network;

import com.niharika.android.githubtrends.model.GithubApiResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface GithubApiInterface {
    @GET("search/repositories?sort=stars&order=desc")
    Call<GithubApiResponse> getRepos(@Query("q") String query);

}
