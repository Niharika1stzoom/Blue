package com.niharika.android.githubbrowser.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.niharika.android.githubbrowser.Model.Repo;
import com.niharika.android.githubbrowser.R;
import com.niharika.android.githubbrowser.databinding.AddRepoFragmentBinding;
import com.niharika.android.githubbrowser.utilities.RepoUtility;
import com.niharika.android.githubbrowser.viewmodel.AddRepoViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddRepoFragment extends Fragment {
    public MutableLiveData<Repo> mRepo;
    private AddRepoViewModel mViewModel;
    AddRepoFragmentBinding mBinding;

    public static AddRepoFragment newInstance() {
        return new AddRepoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepo = new MutableLiveData<>();
        initViewModel();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = AddRepoFragmentBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();
        addClickListener();
        addObserver();
        return view;
    }

    private void addObserver() {
        mRepo.observe(getViewLifecycleOwner(), repo -> {
            if (repo != null)
                NavHostFragment.findNavController(this).navigate(R.id.landingRepoFragment);
            else {
                if (!RepoUtility.isNetworkAvailableAndConnected(getContext()))
                    RepoUtility.showSnackbar(getView(), getString(R.string.network_err));
                else {
                    RepoUtility.showSnackbar(getView(), getString(R.string.repo_not_found));
                }
            }
        });
    }

    private void addClickListener() {
        mBinding.addButton.setOnClickListener(view -> {
            String repoName = mBinding.repoNameValue.getText().toString().trim();
            String owner = mBinding.ownerValue.getText().toString().trim();
            if (!validateInput(repoName, owner))
                return;
            mViewModel.addRepo(repoName, owner, mRepo);
        });
    }

    boolean validateInput(String repoName, String owner) {
        boolean valid = true;
        if (repoName.isEmpty()) {
            valid = false;
            mBinding.repoNameValue.setError(getString(R.string.err_enter_owner));
        }
        if (owner.isEmpty()) {
            valid = false;
            mBinding.ownerValue.setError(getString(R.string.err_enter_name));
        }
        return valid;

    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(AddRepoViewModel.class);
    }

}