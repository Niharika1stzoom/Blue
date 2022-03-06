package com.niharika.android.githubbrowser.Model;

import java.io.Serializable;
import java.util.Date;
//Commit in a branch
public class BranchCommit implements Serializable {
    public String getSha() {
        return sha;
    }
    String sha;
    public Committer getCommitter() {
        return committer;
    }

    public Commit getCommit() {
        return commit;
    }

    Committer committer;
    Commit commit;


}


