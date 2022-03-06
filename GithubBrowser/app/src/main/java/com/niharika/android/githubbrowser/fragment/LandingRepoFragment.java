package com.niharika.android.githubbrowser.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.niharika.android.githubbrowser.R;
import com.niharika.android.githubbrowser.adapter.RepoAdapter;
import com.niharika.android.githubbrowser.databinding.LandingRepoFragmentBinding;
import com.niharika.android.githubbrowser.viewmodel.LandingRepoViewModel;

import dagger.hilt.android.AndroidEntryPoint;

import static android.widget.LinearLayout.VERTICAL;

@AndroidEntryPoint
public class LandingRepoFragment extends Fragment {
    LandingRepoFragmentBinding mBinding;
    private LandingRepoViewModel mViewModel;
    private RepoAdapter mRepoAdapter;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.landing_repo_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavController navController = NavHostFragment.findNavController(this);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        initViewModel();
    }

    public static LandingRepoFragment newInstance() {
        return new LandingRepoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding=LandingRepoFragmentBinding.inflate(inflater,container,false);
        View view=mBinding.getRoot();
        mBinding.addRepoButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.addRepoFragment));
        mViewModel.getRepoList().observe(this, repoList -> {
            if(repoList==null || repoList.size()==0) {
                mBinding.repoRecyclerView.setVisibility(View.INVISIBLE);
                mBinding.addRepoGroup.setVisibility(View.VISIBLE);
            }
            else {
                mBinding.repoRecyclerView.setVisibility(View.VISIBLE);
                mBinding.addRepoGroup.setVisibility(View.GONE);
                mRepoAdapter.setList(repoList);
            }
        });
        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        mBinding.repoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity().getApplicationContext(), VERTICAL);
        mBinding.repoRecyclerView.addItemDecoration(decoration);
        mRepoAdapter=new RepoAdapter(getContext());
        mBinding.repoRecyclerView.setAdapter(mRepoAdapter);
    }
    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(LandingRepoViewModel.class);
    }
}