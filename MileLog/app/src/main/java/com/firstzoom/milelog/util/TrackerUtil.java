package com.firstzoom.milelog.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.util.Log;

import com.firstzoom.milelog.model.LocationPath;
import com.firstzoom.milelog.model.TempLocationList;
import com.firstzoom.milelog.worker.LocationWorker;
import com.google.maps.android.SphericalUtil;

import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.firstzoom.milelog.MainActivity;
import com.firstzoom.milelog.model.Tag;
import com.firstzoom.milelog.model.Trip;
import com.firstzoom.milelog.room.AppDatabase;
import com.firstzoom.milelog.worker.UserInVehicleService;
import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TrackerUtil {
   // LocationRequest locationRequest=getLocationRequest();


  /*  private LocationRequest getLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(50000);
        locationRequest.setFastestInterval(20000);
        locationRequest.setNumUpdates(1);
        return locationRequest;
    }*/

    void getLocation() {

    }

/*    @SuppressLint("MissingPermission")
    public static void startTracking(Context context){
       Log.d(AppConstants.TAG,"Start tracking");

        Task<Location> locationTask= LocationServices.getFusedLocationProviderClient(context.getApplicationContext())
               .getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY,null);
       locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
           @SuppressLint("NewApi")
           @Override
           public void onSuccess(Location location) {
               Log.d("MileDebug", "Got Start location"+location );

               Trip trip=new Trip("Testing trip",null,location.getLatitude(),location.getLongitude(),
                       null,null,new Date(),null);
               ArrayList<Tag> tags=new ArrayList<>();
               tags.add(new Tag("Office"));
               tags.add(new Tag("House"));
               trip.setTags(tags);
               AppExecutors.databaseWriteExecutor.execute(() ->{
                       long i=AppDatabase.getDatabase(context.getApplicationContext()).tripDao().insert(trip);

                       SharedPrefUtil.setLastId(context.getApplicationContext(), Math.toIntExact(i));
                           TempLocationList temp=new TempLocationList(Math.toIntExact(i));
                           temp.getLocations().add(location);
                           SharedPrefUtil.setLocationList(context.getApplicationContext(),temp);
                       //startLocationTrackerWorker(context);

                       Log.d(AppConstants.TAG,"insert "+ i);
           }
               );

               //TODO: save the trip details in database
           }
       }).addOnFailureListener(e -> Log.d("MileDebug", "Failed"+e.getLocalizedMessage() ));
        OneTimeWorkRequest trackWorkRequest =
                new OneTimeWorkRequest.Builder(UserActivityTransitionWorker.class)
                        // Additional configuration
                        .build();
        manager.enqueueUniqueWork(AppConstants.TRACKER, ExistingWorkPolicy.KEEP,  trackWorkRequest);
    }

 */

    public static void startLocationTrackerWorker(Context context,int id){
        //Better to start a service

        /* WorkManager manager=WorkManager.getInstance(context.getApplicationContext());
        Data.Builder data= new Data.Builder();
        Log.d(AppConstants.TAG,"Got id"+id);
        data.putInt(AppConstants.KEY_TRIP_ID,id);
        PeriodicWorkRequest workRequest= new PeriodicWorkRequest.Builder(LocationWorker.class,30, TimeUnit.SECONDS)
                .setInputData(data.build())
                .setInitialDelay(30,TimeUnit.SECONDS)
                .addTag(AppConstants.LOCATION_WORKER).build();
        manager.enqueue(workRequest);

        */

    }

    @SuppressLint("MissingPermission")
   public static void stopTracking(Context context){
        Task<Location> locationTask= LocationServices.getFusedLocationProviderClient(context.getApplicationContext())
               .getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY,null);
       locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
           @Override
           public void onSuccess(Location location) {
               //TODO: save the trip details in database
               int lastId=SharedPrefUtil.getLastId(context.getApplicationContext());
               if(lastId==0)
                   return;
               LocationUtil.stopForegroundService(context.getApplicationContext());
               Double distance=0.0;
               AppExecutors.databaseWriteExecutor.execute(() ->{
                   long j=AppDatabase.getDatabase(context.getApplicationContext()).locationPathDao()
                           .insert(new LocationPath(location.getLatitude(), location.getLongitude(),SharedPrefUtil.getLastId(context) ));
                   AppDatabase.getDatabase(context.getApplicationContext()).tripDao().updateEndTrip(location.getLatitude(),
                           location.getLongitude(),new Date(),distance,SharedPrefUtil.getLastId(context.getApplicationContext()));
                     SharedPrefUtil.removeLastID(context);

               });
           }
       }).addOnFailureListener(e -> Log.d("MileDebug", "Failed"+e.getLocalizedMessage() ));

    }

    private static void addLocationPath(Location location, Context context) {
    }


    private static void stopLocationTrackerWorker(Context context) {
        WorkManager.getInstance(context).cancelAllWorkByTag(AppConstants.LOCATION_WORKER);
    }

    public static Boolean isTracking(Context context){
        Log.d(AppConstants.TAG,"Is tracking");

     /*   try {
            if(manager.getWorkInfosForUniqueWork(AppConstants.TRACKER).get()!=null)
                return true;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {

            e.printStackTrace();
        }*/
        return false;


    }

    public static void startForegroundService(MainActivity mainActivity, Class<UserInVehicleService> service) {
        Intent intent = new Intent(mainActivity,service );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mainActivity.getApplicationContext().startForegroundService(intent);
        }
    }

    public static void stopForegroundService(MainActivity mainActivity, Class<UserInVehicleService> service) {
        Intent intent = new Intent(mainActivity,service);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mainActivity.getApplicationContext().stopService(intent);
        }
    }

    public static ActivityTransitionRequest getActivityTransitionRequest(){
        ActivityTransitionRequest request = new ActivityTransitionRequest(getActivityTransitionList());
        return request;
    }
    public static String toActivityString(int activity) {
        switch (activity) {
            case DetectedActivity.STILL:
                return "STILL";
            case DetectedActivity.WALKING:
                return "WALKING";
            case DetectedActivity.IN_VEHICLE:
                return "IN_VEHICLE";

            default:
                return "UNKNOWN";
        }

    }

    public static String toTransitionType(int transitionType) {
        switch (transitionType) {
            case ActivityTransition.ACTIVITY_TRANSITION_ENTER:
                return "ENTER";
            case ActivityTransition.ACTIVITY_TRANSITION_EXIT:
                return "EXIT";
            default:
                return "UNKNOWN";
        }
    }

    private static List<ActivityTransition> getActivityTransitionList() {
       List<ActivityTransition> activityTransitionList=new ArrayList<>();


        activityTransitionList.add(new ActivityTransition.Builder()
                .setActivityType(AppConstants.IN_VEHICLE)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                .build());

        activityTransitionList.add(new ActivityTransition.Builder()
                .setActivityType(AppConstants.IN_VEHICLE)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                .build());
       /* activityTransitionList.add(new ActivityTransition.Builder()
                .setActivityType(DetectedActivity.WALKING)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                .build());

        activityTransitionList.add(new ActivityTransition.Builder()
                .setActivityType(DetectedActivity.WALKING)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                .build());
        activityTransitionList.add(new ActivityTransition.Builder()
                .setActivityType(DetectedActivity.IN_VEHICLE)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                .build());
        activityTransitionList.add(new ActivityTransition.Builder()
                .setActivityType(DetectedActivity.IN_VEHICLE)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                .build());

*/
        return activityTransitionList;
    }
    public static Double  calDistance(Double lat1,Double long1,Double lat2,Double long2)
    {
        if(lat1!=null && long1!=null && lat2!=null && long2!=null){
        ArrayList<LatLng> latLngs=new ArrayList();
        latLngs.add(new LatLng(lat1,long1));
        latLngs.add(new LatLng(lat2,long2));
        Double dist = SphericalUtil.computeLength(latLngs);
        return dist/1000;}
        return 0.0;
    }
    public static Double   calDistance(List<LocationPath> location)
    {
        ArrayList<LatLng> latLngs=new ArrayList();
        for (LocationPath loc :  location){
            if(loc.getLatitude()!=null && loc.getLongitude()!=null)
            latLngs.add(new LatLng(loc.getLatitude(), loc.getLongitude()));
        }
        Double dist = SphericalUtil.computeLength(latLngs)/1000;
        return dist;

    }
}
