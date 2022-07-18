package com.firstzoom.milelog.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firstzoom.milelog.MainActivity;
import com.firstzoom.milelog.R;
import com.firstzoom.milelog.databinding.TripFragmentBinding;
import com.firstzoom.milelog.model.LocationPath;
import com.firstzoom.milelog.model.Trip;
import com.firstzoom.milelog.util.AppConstants;
import com.firstzoom.milelog.util.PermissionUtil;
import com.firstzoom.milelog.util.SharedPrefUtil;
import com.firstzoom.milelog.util.TrackerUtil;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import pub.devrel.easypermissions.EasyPermissions;
@AndroidEntryPoint
public class TripFragment extends Fragment {
    private TripViewModel mViewModel;
    private TripFragmentBinding mBinding;
    TripAdapter mAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //verifyPermissions();
    }

    private void verifyPermissions() {
        //if(!PermissionUtil.checkPermissions(getContext()))
            PermissionUtil.checkPermissions(getActivity());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding=TripFragmentBinding.inflate(inflater,container,false);
        return mBinding.getRoot() ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TripViewModel.class);
        initRecyclerView();
        getTrips();

    }

    private void getTrips() {
        //Log.d(AppConstants.TAG,"acti "+AppConstants.IN_VEHICLE);
        displayLoader();
        mViewModel.getTripList().observe(getViewLifecycleOwner(), tripList -> {
            hideLoader();
            if(tripList==null){
                displayEmptyView();
            }
            else{
                mAdapter.setList(tripList);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
       // ((AppCompatActivity) getActivity()).getSupportActionBar().getCustomView().findViewById();

    }

    private void hideLoader() {
        mBinding.viewLoader.rootView.setVisibility(View.GONE);
        mBinding.recyclerView.setVisibility(View.VISIBLE);
    }

    private void displayEmptyView() {
        mBinding.recyclerView.setVisibility(View.GONE);
        //mBinding.viewEmpty.emptyText.setText(getString(R.string.empty_order));
        mBinding.viewEmpty.emptyContainer.setVisibility(View.VISIBLE);
    }

    private void displayLoader() {
        mBinding.viewLoader.rootView.setVisibility(View.VISIBLE);
        mBinding.recyclerView.setVisibility(View.GONE);
    }

    private void initRecyclerView() {

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new TripAdapter(getContext());
        mBinding.recyclerView.setAdapter(mAdapter);

    }
    
}