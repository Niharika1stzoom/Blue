package com.niharika.android.githubbrowser.fragment;

import android.content.Intent;
import android.databinding.tool.util.StringUtils;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.tabs.TabLayoutMediator;
import com.niharika.android.githubbrowser.Model.Repo;
import com.niharika.android.githubbrowser.R;
import com.niharika.android.githubbrowser.adapter.DetailFragmentAdapter;
import com.niharika.android.githubbrowser.databinding.RepoDetailFragmentBinding;
import com.niharika.android.githubbrowser.room.BaseRepo;
import com.niharika.android.githubbrowser.utilities.RepoUtility;
import com.niharika.android.githubbrowser.viewmodel.RepoDetailViewModel;

import dagger.hilt.android.AndroidEntryPoint;

//Input a Repo
@AndroidEntryPoint
public class RepoDetailFragment extends Fragment {
    private RepoDetailViewModel mViewModel;
    private RepoDetailFragmentBinding mBinding;
    private BaseRepo mBaseRepo;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.detail_menu, menu);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mViewModel = new ViewModelProvider(this).get(RepoDetailViewModel.class);
      if(getArguments()!=null && getArguments().containsKey(RepoUtility.ARG_REPO))
          mBaseRepo= (BaseRepo) getArguments().getSerializable(RepoUtility.ARG_REPO);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavController navController = NavHostFragment.findNavController(this);
        switch(item.getItemId()){
            case R.id.repo_del:delListener();
                     return true;
            case R.id.view_repo:viewRepoListener();
                return true;
            default:return NavigationUI.onNavDestinationSelected(item, navController)
                    || super.onOptionsItemSelected(item);
        }

    }
    public static RepoDetailFragment newInstance() {
        return new RepoDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding= RepoDetailFragmentBinding.inflate(inflater,container,false);
        View view=mBinding.getRoot();
        initRepoDetails();
        getRepoIssueCount();
        return view;
    }

    //gets the Repo details online and sends input repo to create tab layout
    private void getRepoIssueCount() {
        mViewModel.getRepo(mBaseRepo.getName(), mBaseRepo.getOwner());
        mViewModel.getRepoLiveData().observe(getViewLifecycleOwner(), repo -> {
            if (repo == null){
                mBinding.ll.setVisibility(View.GONE);
                mBinding.msg.setVisibility(View.VISIBLE);
                if(!RepoUtility.isNetworkAvailableAndConnected(getContext()))
                    RepoUtility.showSnackbar(getView(),getString(R.string.network_err));
            }
            else{
                mBinding.ll.setVisibility(View.VISIBLE);
                mBinding.msg.setVisibility(View.GONE);
                initTabLayout(repo);
            }
        });
    }
    private void viewRepoListener() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(RepoUtility.getRepoUrl(mBaseRepo)));
        startActivity(Intent.createChooser(browserIntent, "Choose one"));
    }

    private void delListener() {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.alert_dialog_del_title)
                .setMessage(R.string.alert_del_text)
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    mViewModel.delRepo(mBaseRepo);
                    NavHostFragment.findNavController(getParentFragment()).navigate(R.id.landingRepoFragment);
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }



    //sets the Repo details name and description
    private void initRepoDetails() {
        mBinding.repoName.setText(StringUtils.capitalize(mBaseRepo.getName()));
        if(mBaseRepo.getDescription()!=null)
        mBinding.repoDesc.setText(StringUtils.capitalize(mBaseRepo.getDescription()));
    }

    //creates a tab layout with Branches and Issues Fragment input Repo details
    private void initTabLayout(Repo repo) {
        DetailFragmentAdapter adapter=new DetailFragmentAdapter(this,repo);
        mBinding.pager.setAdapter(adapter);
        new TabLayoutMediator(mBinding.tabLayout, mBinding.pager,
                (tab, position) -> tab.setText(adapter.getTabName(position))).attach();
    }
}
