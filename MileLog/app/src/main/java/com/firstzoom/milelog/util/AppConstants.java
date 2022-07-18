package com.firstzoom.milelog.util;

import android.Manifest;

import com.google.android.gms.location.DetectedActivity;

public class AppConstants {
    public static final String TAG="MileDebug";
    public static final int IN_VEHICLE= 0;
    public static final String TRACKER ="tracker" ;
    public static final String TRANSITIONS_RECEIVER_ACTION = "com.firstzoom.milelog.ACTION_PROCESS_ACTIVITY_TRANSITIONS";
    public static final String FOREGROUND_CHANNEL_ID = "MileLog";
    public static final CharSequence CHANNEL_NAME ="MileLog" ;
    public static final int FOREGROUND_NOTIFICATION_ID =123 ;
    public static final int PENDING_TRANSITION_CODE = 25;
    public static final String KEY_LAST_ID = "last_id";
    public static final String LOCATION_WORKER = "location_worker";
    public static final String KEY_TEMP = "temp";
    public static final String KEY_TRIP_ID = "trip_id";
    public static final int FOREGROUND_LOCATION_NOTIFICATION_ID =324 ;
}
