package com.niharika.android.githubtrends.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Repo implements Serializable {
    private String name,starred_at,html_url;
    @SerializedName("stargazers_count")
    private Long starsCount;
    private Long forks_count;
    private String language;
    String contributors_url;
    Owner owner;
    private String full_name;
    private String created_at;
    private String description;

    public String getDescription() {
        return description;
    }
    public String getName() {
        return name;
    }
    public String getStarred_at() {
        return starred_at;
    }
    public String getHtml_url() {
        return html_url;
    }

    public Long getStarsCount() {
        return starsCount;
    }

    public Long getForks_count() {
        return forks_count;
    }

    public String getLanguage() {
        return language;
    }

    public String getContributors_url() {
        return contributors_url;
    }

    public Owner getOwner() {
        return owner;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getCreated_at() {
        return created_at;
    }



}
