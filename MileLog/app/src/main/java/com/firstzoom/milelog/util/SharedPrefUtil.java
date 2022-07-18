package com.firstzoom.milelog.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.firstzoom.milelog.model.TempLocationList;

import java.util.Date;
import java.util.List;

public class SharedPrefUtil {
    synchronized public static void setLastId(Context context, int id) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(AppConstants.KEY_LAST_ID,id);
        editor.apply();
    }
    public static int getLastId(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(AppConstants.KEY_LAST_ID, 0);
    }

    public static void removeLastID(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(AppConstants.KEY_LAST_ID);
    }

/*
    synchronized public static void setLocationList(Context context, TempLocationList temp) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Log.d(AppConstants.TAG,"Setting loc in shared"+temp.getLocations().size());
        editor.putString(AppConstants.KEY_TEMP,GsonUtils.getGsonObject(temp));
        Log.d(AppConstants.TAG,"Getting from shared in write"+SharedPrefUtil.getLocationList(context).getLocations().get(0).getLatitude());
        editor.apply();
    }

    public static TempLocationList getLocationList(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String temp = prefs.getString(AppConstants.KEY_TEMP, "");

        if(TextUtils.isEmpty(temp))
            return null;
        else
            return GsonUtils.getModelObjectUser(temp) ;
    }

    synchronized public static void delLocationList(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(AppConstants.KEY_TEMP);
        editor.apply();
    }
    public static void addLocation(Context context,Location location) {
      TempLocationList temp=SharedPrefUtil.getLocationList(context);
        Log.d(AppConstants.TAG,"Before adding location in shared pref"+location.getLatitude());
      temp.getLocations().add(location);
      Log.d(AppConstants.TAG,"Adding location in shared pref"+temp.getLocations().get(temp.getLocations().size()-1).getLatitude());
      SharedPrefUtil.setLocationList(context,temp);
        Log.d(AppConstants.TAG,"Getting from shared in add"+SharedPrefUtil.getLocationList(context).getLocations().get(0).getLatitude());
    }
*/
}
