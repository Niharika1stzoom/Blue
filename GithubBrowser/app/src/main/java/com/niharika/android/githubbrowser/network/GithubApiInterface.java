package com.niharika.android.githubbrowser.network;

import com.niharika.android.githubbrowser.Model.Branch;
import com.niharika.android.githubbrowser.Model.BranchCommit;
import com.niharika.android.githubbrowser.Model.Issue;
import com.niharika.android.githubbrowser.Model.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GithubApiInterface {

    @GET("{owner}/{repo_name}")
    Call<Repo> getRepo(@Path("owner") String owner,@Path("repo_name") String repoName);


    @GET("{owner}/{repo_name}/branches")
    Call<List<Branch>> getBranches(@Path("owner") String owner, @Path("repo_name") String repoName);


    @GET("{owner}/{repo_name}/commits")
   Call<List<BranchCommit>> getCommitsByBranch(@Path("owner") String owner, @Path("repo_name") String repo_name, @Query("sha") String sha);


   @GET("{owner}/{repo_name}/issues?state=open")
   Call<List<Issue>> getOpenIssues(@Path("owner") String owner, @Path("repo_name") String repoName);


}
