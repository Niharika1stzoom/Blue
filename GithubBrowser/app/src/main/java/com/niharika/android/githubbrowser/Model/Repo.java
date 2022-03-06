package com.niharika.android.githubbrowser.Model;

import java.io.Serializable;

//Repo for API
public class Repo implements Serializable {
    String name,description,open_issues_count;
    Owner owner;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getOpen_issues_count() {
        return open_issues_count;
    }

    public Owner getOwner() {
        return owner;
    }

    public class Owner{
        public String getLogin() {
            return login;
        }

        String login;
    }
}



