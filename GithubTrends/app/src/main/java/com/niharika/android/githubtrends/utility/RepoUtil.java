package com.niharika.android.githubtrends.utility;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.niharika.android.githubtrends.R;
import com.niharika.android.githubtrends.model.Repo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.niharika.android.githubtrends.utility.AppConstants.DATE_TIME_FORMAT;

public class RepoUtil {
    public static String getDate(String dateString) {

        try{
            SimpleDateFormat format1 = new SimpleDateFormat(DATE_TIME_FORMAT);
            Date date = format1.parse(dateString);
            DateFormat sdf = new SimpleDateFormat("MMM d, yyyy");
            return sdf.format(date);
        }
        catch(Exception ex) {
            ex.printStackTrace();
            return "xx";
        }
    }

    public static String getTime(String dateString) {

        try{
            SimpleDateFormat format1 = new SimpleDateFormat(DATE_TIME_FORMAT);
            Date date = format1.parse(dateString);
            DateFormat sdf = new SimpleDateFormat("h:mm a");
            Date netDate = (date);
            return sdf.format(netDate);
        }
        catch(Exception ex) {
            ex.printStackTrace();
            return "xx";
        }
    }

    public static void setImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.ic_placeholder)
                .centerCrop()
                .into(imageView);
    }
    public static void shareRepo(Context context,Repo repo) {
        String repoDesc=repo.getDescription();
        if(repoDesc==null)
            repoDesc="";
        else
            repoDesc=repoDesc+"\n";
        String repoDetail=repo.getName()+"\n"+repoDesc;
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
        shareIntent.putExtra(Intent.EXTRA_TEXT, repoDetail+repo.getHtml_url());
        context.startActivity(Intent.createChooser(shareIntent, "Choose one"));
    }
    public static void showSnackbar(View view, String msg){
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
