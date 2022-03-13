package com.niharika.android.githubtrends.model;

import java.util.List;

public class GithubApiResponse {
    public List<Repo> getItems() {
        return items;
    }
    List<Repo> items;
}
