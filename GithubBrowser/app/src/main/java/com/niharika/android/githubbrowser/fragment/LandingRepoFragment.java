package com.niharika.android.githubbrowser.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niharika.android.githubbrowser.R;

public class LandingRepoFragment extends Fragment {

    private LandingRepoViewModel mViewModel;

    public static LandingRepoFragment newInstance() {
        return new LandingRepoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.landing_repo_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LandingRepoViewModel.class);
        // TODO: Use the ViewModel
    }

}