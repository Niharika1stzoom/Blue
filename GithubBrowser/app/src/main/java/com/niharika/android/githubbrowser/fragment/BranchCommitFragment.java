package com.niharika.android.githubbrowser.fragment;

import android.databinding.tool.util.StringUtils;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.niharika.android.githubbrowser.Model.Branch;
import com.niharika.android.githubbrowser.Model.Repo;
import com.niharika.android.githubbrowser.R;
import com.niharika.android.githubbrowser.adapter.CommitAdapter;
import com.niharika.android.githubbrowser.databinding.BranchCommitFragmentBinding;
import com.niharika.android.githubbrowser.utilities.RepoUtility;
import com.niharika.android.githubbrowser.viewmodel.BranchCommitViewModel;
import dagger.hilt.android.AndroidEntryPoint;

//Takes Input as Branch
@AndroidEntryPoint
public class BranchCommitFragment extends Fragment {
    private BranchCommitViewModel mViewModel;
    private BranchCommitFragmentBinding mBinding;
    private CommitAdapter mCommitAdapter;
    public static BranchCommitFragment newInstance() {
        return new BranchCommitFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BranchCommitViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding=BranchCommitFragmentBinding.inflate(inflater,container,false);
        View view=mBinding.getRoot();
        if(getArguments()!=null && getArguments().containsKey(RepoUtility.ARG_BRANCH)) {
            Branch branch = (Branch) getArguments().getSerializable(RepoUtility.ARG_BRANCH);
            Repo repo=(Repo)getArguments().getSerializable(RepoUtility.ARG_REPO);
            setTitle(branch);
            observeCommitList(branch,repo);
        }
        initRecyclerView();
        return view;
    }

    private void setTitle(Branch branch) {
        if(((AppCompatActivity) getActivity()).getSupportActionBar()!=null)
        ((AppCompatActivity) getActivity()).getSupportActionBar()
                .setSubtitle(StringUtils.capitalize(branch.getName()));
    }
    @Override
    public void onStop() {
        super.onStop();
        if(((AppCompatActivity) getActivity()).getSupportActionBar()!=null)
        ((AppCompatActivity) getActivity()).getSupportActionBar()
                .setSubtitle("");
    }

    private void observeCommitList(Branch branch,Repo repo) {
        mViewModel.getCommitList(branch,repo).observe(this, commitList -> {

            if (commitList == null || commitList.size()==0) {
                mBinding.commitRecyclerView.setVisibility(View.GONE);
                mBinding.msg.setVisibility(View.VISIBLE);
                if(!RepoUtility.isNetworkAvailableAndConnected(getContext()))
                    RepoUtility.showSnackbar(getView(),getString(R.string.network_err));
            } else {
                mBinding.commitRecyclerView.setVisibility(View.VISIBLE);
                  mBinding.msg.setVisibility(View.GONE);
                mCommitAdapter.setList(commitList);
            }
        });
    }
        private void initRecyclerView() {
            mBinding.commitRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mCommitAdapter=new CommitAdapter(getContext());
            mBinding.commitRecyclerView.setAdapter(mCommitAdapter);
        }
    }
