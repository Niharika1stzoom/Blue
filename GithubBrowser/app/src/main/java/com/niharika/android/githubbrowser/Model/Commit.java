package com.niharika.android.githubbrowser.Model;
   public class Commit{
       String message;
       String sha;
       Committer committer;
       public String getSha() {
           return sha;
       }
       public Committer getCommitter() {
           return committer;
       }
       public String getMessage() {
           return message;
       }
   }
