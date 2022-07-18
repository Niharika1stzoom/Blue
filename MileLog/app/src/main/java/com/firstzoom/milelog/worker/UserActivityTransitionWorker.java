package com.firstzoom.milelog.worker;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.work.Operation;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.firstzoom.milelog.MainActivity;
import com.firstzoom.milelog.util.AppConstants;

public class UserActivityTransitionWorker extends Worker {
    Context mContext;
    public UserActivityTransitionWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mContext=context;
    }

    @NonNull
    @Override
    public Result doWork() {
       /* Intent intent = new Intent(getApplicationContext(),TripService.class);
       PendingIntent mActivityTransitionsPendingIntent =
                PendingIntent.getService(mContext, 0, intent, 0);*/
        return Result.success();
    }
}
