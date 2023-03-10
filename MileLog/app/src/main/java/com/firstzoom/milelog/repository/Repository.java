package com.firstzoom.milelog.repository;

import android.location.Location;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.firstzoom.milelog.model.LocationPath;
import com.firstzoom.milelog.model.Trip;
import com.firstzoom.milelog.room.AppDatabase;
import com.firstzoom.milelog.room.LocationPathDao;
import com.firstzoom.milelog.room.TripDao;
import com.firstzoom.milelog.util.AppConstants;
import com.firstzoom.milelog.util.AppExecutors;
import com.firstzoom.milelog.util.LocationUtil;
import com.firstzoom.milelog.util.SharedPrefUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Repository {
    public TripDao tripDao;
    public LocationPathDao locationPathDao;

    public Repository(TripDao tripDao, LocationPathDao locationPathDao) {
        this.tripDao = tripDao;
        this.locationPathDao = locationPathDao;
    }

    public void insertList(List<Trip> tripList) {
        AppExecutors.databaseWriteExecutor.execute(() ->
                tripDao.insertList(tripList)
        );
    }

    public void insertTrip(Trip trip) {
        AppExecutors.databaseWriteExecutor.execute(() ->
                tripDao.insert(trip)
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LiveData<List<Trip>> getTripList() {
        // if (mRepoList == null)
        //populateRepoListDb();
       // return tripDao.getTripList();
        Calendar c1=Calendar.getInstance();
        c1.set(Calendar.HOUR,0);
        c1.set(Calendar.MINUTE,0);
        c1.set(Calendar.SECOND,0);
        Date startOfDay=c1.getTime();
        Log.d(AppConstants.TAG,"Start"+startOfDay.toString());
        return tripDao.getTripList(startOfDay,new Date());
    }

    public LiveData<Trip> getTrip(int id) {
        return tripDao.getTrip(id);
    }


    public void insertLocationPath(int id, Location location) {
        AppExecutors.databaseWriteExecutor.execute(() -> {
            long j = locationPathDao
                    .insert(new LocationPath(location.getLatitude(), location.getLongitude(), id));
        });
    }
}
