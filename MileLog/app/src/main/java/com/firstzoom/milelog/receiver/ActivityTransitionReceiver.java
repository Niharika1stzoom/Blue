package com.firstzoom.milelog.receiver;

import static com.firstzoom.milelog.util.AppConstants.TRANSITIONS_RECEIVER_ACTION;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.firstzoom.milelog.MainActivity;
import com.firstzoom.milelog.R;
import com.firstzoom.milelog.util.AppConstants;
import com.firstzoom.milelog.util.LocationUtil;
import com.firstzoom.milelog.util.TrackerUtil;
import com.firstzoom.milelog.worker.StartTripWorker;
import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.ActivityTransitionEvent;
import com.google.android.gms.location.ActivityTransitionResult;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.material.math.MathUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class ActivityTransitionReceiver extends BroadcastReceiver {
    Context mContext;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(AppConstants.TAG,"Receive on Intent");
        mContext=context;
        if (!TextUtils.equals(TRANSITIONS_RECEIVER_ACTION, intent.getAction())) {

            Log.d(AppConstants.TAG,"Received an unsupported action in TransitionsReceiver: action = " +
                    intent.getAction());
            return;
        }
        if (ActivityTransitionResult.hasResult(intent)) {
            ActivityTransitionResult result = ActivityTransitionResult.extractResult(intent);
            if(result!=null && result.getTransitionEvents().size()>0)
            Log.d(AppConstants.TAG,"Found events"+result.getTransitionEvents().size());
            for (ActivityTransitionEvent event : result.getTransitionEvents()) {
                if(event.getActivityType()== AppConstants.IN_VEHICLE && event.getTransitionType()== ActivityTransition.ACTIVITY_TRANSITION_ENTER){
                //TrackerUtil.startTracking(mContext);
                    OneTimeWorkRequest trackWorkRequest =
                            new OneTimeWorkRequest.Builder(StartTripWorker.class)
                                    // Additional configuration
                                    .build();
                    WorkManager.getInstance(context).enqueue(trackWorkRequest);

                }
                else
                if(event.getActivityType()== AppConstants.IN_VEHICLE && event.getTransitionType()==
                        ActivityTransition.ACTIVITY_TRANSITION_EXIT) {

                TrackerUtil.stopTracking(mContext);
                    LocationUtil.stopForegroundService(mContext);
                }

                String info = "Transition: " + TrackerUtil.toActivityString(event.getActivityType()) +
                        " (" + TrackerUtil.toTransitionType(event.getTransitionType()) + ")" + "   " +
                        new SimpleDateFormat("HH:mm:ss", Locale.US).format(new Date());
                Log.d("MileDebug",info);
                Intent inten = new Intent(context.getApplicationContext(), MainActivity.class);
                showNotification(context, "Activity"+TrackerUtil.toActivityString(event.getActivityType()),info, inten, 2);
            }
        }
    }

        public void showNotification(Context context, String title, String message, Intent intent, int reqCode) {
        //SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager.getInstance(context);
            int requestID = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        String CHANNEL_ID = "channel_name";// The id of the channel.
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel Name";// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        notificationManager.notify(reqCode, notificationBuilder.build()); // 0 is the request code, it should be unique id

        Log.d("showNotification", "showNotification: " + reqCode);
    }
}