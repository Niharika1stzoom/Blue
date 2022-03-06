package com.niharika.android.githubbrowser.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.niharika.android.githubbrowser.Model.Repo;
import com.niharika.android.githubbrowser.R;
import com.niharika.android.githubbrowser.adapter.IssueAdapter;
import com.niharika.android.githubbrowser.databinding.IssueListFragmentBinding;
import com.niharika.android.githubbrowser.utilities.RepoUtility;
import com.niharika.android.githubbrowser.viewmodel.IssueListViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class IssueListFragment extends Fragment {
    private IssueListFragmentBinding mBinding;
    private IssueListViewModel mViewModel;
    private IssueAdapter mIssueAdapter;

    public static IssueListFragment newInstance(Repo repo) {
        final IssueListFragment fragment = new IssueListFragment();
        fragment.setArguments(RepoUtility.getRepoBundle(repo));
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(IssueListViewModel.class);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding=IssueListFragmentBinding.inflate(inflater,container,false);
        View view=mBinding.getRoot();
        if(getArguments()!=null && getArguments().containsKey(RepoUtility.ARG_REPO)) {
            Repo repo = (Repo) getArguments().getSerializable(RepoUtility.ARG_REPO);
            observeIssueList(repo);
        }
        initRecyclerView();
        return view;
    }

    private void observeIssueList(Repo repo) {

        mViewModel.getOpenIssuesList(repo).observe(this, issueList -> {
            if(issueList==null || issueList.size()==0) {
                 mBinding.issuesRecyclerView.setVisibility(View.GONE);
                mBinding.msg.setVisibility(View.VISIBLE);
                if(!RepoUtility.isNetworkAvailableAndConnected(getContext()))
                    RepoUtility.showSnackbar(getView(),getString(R.string.network_err));
            }
            else {
                 mBinding.issuesRecyclerView.setVisibility(View.VISIBLE);
                  mBinding.msg.setVisibility(View.GONE);
                mIssueAdapter.setList(issueList);
            }
        });
    }

    private void initRecyclerView() {
        mBinding.issuesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mIssueAdapter=new IssueAdapter(getContext());
        mBinding.issuesRecyclerView.setAdapter(mIssueAdapter);
    }


}