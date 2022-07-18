package com.firstzoom.milelog.worker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ListenableWorker;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.firstzoom.milelog.model.LocationPath;
import com.firstzoom.milelog.model.Tag;
import com.firstzoom.milelog.model.TempLocationList;
import com.firstzoom.milelog.model.Trip;
import com.firstzoom.milelog.repository.Repository;
import com.firstzoom.milelog.room.AppDatabase;
import com.firstzoom.milelog.util.AppConstants;
import com.firstzoom.milelog.util.AppExecutors;
import com.firstzoom.milelog.util.LocationUtil;
import com.firstzoom.milelog.util.SharedPrefUtil;
import com.firstzoom.milelog.util.TrackerUtil;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


public class StartTripWorker extends Worker {
    Context mContext;


    public StartTripWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mContext = context;
    }

    @NonNull
    @Override
    @SuppressLint("MissingPermission")
    public ListenableWorker.Result doWork() {
        Task<Location> locationTask = LocationServices.getFusedLocationProviderClient(mContext)
                .getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, null);
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(Location location) {
                Log.d("MileDebug", "Got Start location" + location);
                Trip trip = new Trip("Testing trip", null, location.getLatitude(), location.getLongitude(),
                        null, null, new Date(), null);

                ArrayList<Tag> tags = new ArrayList<>();
                tags.add(new Tag("Office"));
                tags.add(new Tag("House"));
                trip.setTags(tags);
                AppExecutors.databaseWriteExecutor.execute(() -> {
                            long i = AppDatabase.getDatabase(mContext.getApplicationContext()).tripDao().insert(trip);
                            //repository.insertLocationPath(Math.toIntExact(i),location);
                             long j=AppDatabase.getDatabase(mContext.getApplicationContext()).locationPathDao()
                                   .insert(new LocationPath(location.getLatitude(), location.getLongitude(),Math.toIntExact(i) ));
                            SharedPrefUtil.setLastId(mContext.getApplicationContext(), Math.toIntExact(i));
                            LocationUtil.startForegroundService(mContext, Math.toIntExact(i));
                }
                );

            }
        }).addOnFailureListener(e -> Log.d("MileDebug", "Failed" + e.getLocalizedMessage()));

        return ListenableWorker.Result.success();
    }


}
