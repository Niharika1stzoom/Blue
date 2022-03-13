package com.niharika.android.githubtrends.ui.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.niharika.android.githubtrends.R;
import com.niharika.android.githubtrends.databinding.HomeFragmentBinding;
import com.niharika.android.githubtrends.model.Repo;
import com.niharika.android.githubtrends.utility.RepoUtil;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {
    HomeFragmentBinding mBinding;
    private HomeViewModel mViewModel;
    NavController mNavController;
    private RepoAdapter mAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding=HomeFragmentBinding.inflate(inflater,container,false);
        View view=mBinding.getRoot();
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        mNavController=NavHostFragment.findNavController(this);
        checkSignIn();
        getLanguagesSpinner();
        initRecyclerView();
        return view;
    }
    public void setNavHeader() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
            if (navigationView != null) {
                TextView textviewEmail = navigationView.getHeaderView(0).findViewById(R.id.email_id);
                textviewEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                TextView textviewName = navigationView.getHeaderView(0).findViewById(R.id.name);
                if(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()!=null)
                textviewName.setText("Welcome " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            }

        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        setNavHeader();
    }

    private void getLanguagesSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.array_name, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBinding.spinner.setAdapter(adapter);
        mBinding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                String lang= (String) adapterView.getItemAtPosition(pos);
                observeRepoList(lang);
                displayLoader();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void initRecyclerView() {
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter=new RepoAdapter(getContext());
        mBinding.recyclerView.setAdapter(mAdapter);
    }
    private void checkSignIn() {
        if(FirebaseAuth.getInstance().getCurrentUser()==null)
            mNavController.navigate(R.id.loginFragment);
    }
    private void observeRepoList(String language) {
        mViewModel.getRepoList(language).observe(this, repoList -> {
            if(repoList==null || repoList.size()==0) {
                if(RepoUtil.isNetworkAvailableAndConnected(getContext()))
                    displayEmptyView();
                else
                    RepoUtil.showSnackbar(getView(),getString(R.string.network_err));

                mBinding.recyclerView.setVisibility(View.INVISIBLE);
            }
            else {
                displayDataView(repoList);
            }
        });
    }
    private void displayLoader() {
        mBinding.viewLoader.rootView.setVisibility(View.VISIBLE);
        mBinding.recyclerView.setVisibility(View.GONE);
    }
    private void displayDataView(List<Repo> repositories) {
        hideLoader();
        mBinding.recyclerView.setVisibility(View.VISIBLE);
        mBinding.viewEmpty.emptyContainer.setVisibility(View.GONE);
        mAdapter.setList(repositories);
        mBinding.recyclerView.scheduleLayoutAnimation();
    }
    private void hideLoader() {
        mBinding.viewLoader.rootView.setVisibility(View.GONE);
    }
    private void displayEmptyView() {
        hideLoader();
        mBinding.recyclerView.setVisibility(View.GONE);
        mBinding.viewEmpty.emptyContainer.setVisibility(View.VISIBLE);
    }

}