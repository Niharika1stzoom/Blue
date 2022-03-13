package com.niharika.android.githubtrends.ui.login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niharika.android.githubtrends.R;
import com.niharika.android.githubtrends.databinding.LoginFragmentBinding;
import com.niharika.android.githubtrends.utility.RepoUtil;

public class LoginFragment extends Fragment {
    LoginFragmentBinding mBinding;
    private LoginViewModel mViewModel;
    public static String LOGIN_SUCCESSFUL = "LOGIN_SUCCESSFUL";
    private SavedStateHandle savedStateHandle;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding=LoginFragmentBinding.inflate(inflater,container,false);
        View view=mBinding.getRoot();
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        savedStateHandle = NavHostFragment.findNavController(this)
                .getPreviousBackStackEntry()
                .getSavedStateHandle();
        savedStateHandle.set(LOGIN_SUCCESSFUL, false);
        addLoginButtonListener();
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        ActionBar supportActionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ActionBar supportActionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.show();
    }
    private void addLoginButtonListener() {
        mBinding.loginButton.setOnClickListener(view -> {
            displayLoader();
            mViewModel.signIn(getContext()).observe(this, authResult -> {
                if(authResult==null) {
                    hideLoader();
                    if(RepoUtil.isNetworkAvailableAndConnected(getContext()))
                    RepoUtil.showSnackbar(view,getString(R.string.login_err));
                    else
                        RepoUtil.showSnackbar(view,getString(R.string.network_err));
                }
                else {
                    savedStateHandle.set(LOGIN_SUCCESSFUL, true);
                    NavHostFragment.findNavController(this).popBackStack();
                    NavController navController=NavHostFragment.findNavController(this);
                    navController.navigate(R.id.homeFragment);
                }
            });
        });
    }
    private void displayLoader() {
        mBinding.groupDisplay.setVisibility(View.GONE);
        mBinding.viewLoader.rootView.setVisibility(View.VISIBLE);

    }
    private void hideLoader() {
        mBinding.viewLoader.rootView.setVisibility(View.GONE);
        mBinding.groupDisplay.setVisibility(View.VISIBLE);
    }

}


