package com.niharika.android.githubbrowser.utilities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.navigation.NavOptions;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.niharika.android.githubbrowser.BuildConfig;
import com.niharika.android.githubbrowser.Model.Branch;
import com.niharika.android.githubbrowser.Model.BranchCommit;
import com.niharika.android.githubbrowser.Model.Repo;
import com.niharika.android.githubbrowser.R;
import com.niharika.android.githubbrowser.network.GithubApiInterface;
import com.niharika.android.githubbrowser.room.BaseRepo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RepoUtility {
    public static final String ARG_REPO="repo";
    public static final String ARG_BRANCH="branch";
    public static final String ARG_COMMIT="commit";
    private static final String DATE_FORMAT = "dd MMM yy";
    static String baseURL = "https://github.com/";

    public  static Bundle getRepoBundle(BaseRepo baseRepo){
        Bundle bundle=new Bundle();
        bundle.putSerializable(ARG_REPO,baseRepo);
        return bundle;
    }
    public  static Bundle getRepoBundle(Repo repo){
        Bundle bundle=new Bundle();
        bundle.putSerializable(ARG_REPO,repo);
        return bundle;
    }

    public static Bundle getBranchBundle(Branch branch,Repo repo) {
        Bundle bundle=new Bundle();
        bundle.putSerializable(ARG_BRANCH,branch);
        bundle.putSerializable(ARG_REPO,repo);
        return bundle;
    }


    public static void setImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .centerCrop()
                .into(imageView);
    }

    public static String getDateFormat(Date date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String getRepoUrl(BaseRepo baseRepo){
        String url= baseURL+baseRepo.getOwner()+"/"+baseRepo.getName();
        return url;
    }
    public static void shareRepo(Context context,BaseRepo baseRepo) {
        String repoDesc=baseRepo.getDescription();
        if(repoDesc==null)
            repoDesc="";
        else
            repoDesc=repoDesc+"\n";
        String repoDetail=baseRepo.getName()+"\n"+repoDesc;
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
        shareIntent.putExtra(Intent.EXTRA_TEXT, repoDetail+getRepoUrl(baseRepo));
        context.startActivity(Intent.createChooser(shareIntent, "Choose one"));
    }
    public static void showSnackbar(View view,String msg){
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    public static boolean isNetworkAvailableAndConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;

    }
}
