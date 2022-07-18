package com.firstzoom.milelog.ui;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.work.WorkManager;

import com.firstzoom.milelog.model.Trip;
import com.firstzoom.milelog.repository.Repository;
import com.firstzoom.milelog.util.TrackerUtil;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TripViewModel extends AndroidViewModel {
    Context mContext;
    private LiveData<List<Trip>> mTripList;
    @Inject
    Repository repository;
    @Inject
    public TripViewModel(@NonNull Application application) {
        super(application);
        mContext=application.getApplicationContext();
    }

    public LiveData<List<Trip>> getTripList() {
        return repository.getTripList();
    }

    public LiveData<Trip> getTrip(int id) {
        return repository.getTrip(id);
    }

    public void updateTrip(Trip mTrip) {
        repository.insertTrip(mTrip);
    }
}