package com.firstzoom.milelog.worker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Operation;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.firstzoom.milelog.MainActivity;
import com.firstzoom.milelog.model.LocationPath;
import com.firstzoom.milelog.room.AppDatabase;
import com.firstzoom.milelog.util.AppConstants;
import com.firstzoom.milelog.util.AppExecutors;
import com.firstzoom.milelog.util.AppUtil;
import com.firstzoom.milelog.util.NotificationUtil;
import com.firstzoom.milelog.util.SharedPrefUtil;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.Date;

public class LocationWorker extends Worker {
    Context mContext;
    int tripId;
    public LocationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mContext=context;
    }

    @NonNull
    @Override
    @SuppressLint("MissingPermission")
    public Result doWork() {
        tripId=getInputData().getInt(AppConstants.KEY_TRIP_ID,0);
        if(tripId==0)
            Log.d("MileDebug", "No trip id the worker not running" );
        else {
            Task<Location> locationTask = LocationServices.getFusedLocationProviderClient(mContext.getApplicationContext())
                    .getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, null);
            locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    Log.d("MileDebug", "Got New location" + location);
                    Log.d(AppConstants.TAG, "Foreign key in " + tripId);
                    AppExecutors.databaseWriteExecutor.execute(() -> {
                        long j = AppDatabase.getDatabase(getApplicationContext().getApplicationContext()).locationPathDao()
                                .insert(new LocationPath(location.getLatitude(), location.getLongitude(), tripId));
                        NotificationUtil.
                                sendNotification(getApplicationContext(),"Worker started","Time is "+
                                        AppUtil.getDisplayDate(new Date()),new Intent(getApplicationContext(),MainActivity.class));
                    });
                }
            }).addOnFailureListener(e -> Log.d("MileDebug", "Failed" + e.getLocalizedMessage()));
        }
        return Result.success();
    }
}
