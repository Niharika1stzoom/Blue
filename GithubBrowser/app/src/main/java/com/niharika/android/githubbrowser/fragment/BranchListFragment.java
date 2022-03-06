package com.niharika.android.githubbrowser.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niharika.android.githubbrowser.R;
import com.niharika.android.githubbrowser.adapter.BranchAdapter;
import com.niharika.android.githubbrowser.Model.Repo;
import com.niharika.android.githubbrowser.databinding.BranchListFragmentBinding;
import com.niharika.android.githubbrowser.utilities.RepoUtility;
import com.niharika.android.githubbrowser.viewmodel.BranchListViewModel;

import dagger.hilt.android.AndroidEntryPoint;

import static android.widget.LinearLayout.VERTICAL;

@AndroidEntryPoint
public class BranchListFragment extends Fragment {
    private BranchListViewModel mViewModel;
    private BranchListFragmentBinding mBinding;
    private BranchAdapter mBranchAdapter;

    public static BranchListFragment newInstance(Repo repo) {
        final BranchListFragment fragment = new BranchListFragment();
        fragment.setArguments(RepoUtility.getRepoBundle(repo));
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BranchListViewModel.class);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding=BranchListFragmentBinding.inflate(inflater,container,false);
        View view=mBinding.getRoot();
        if(getArguments()!=null && getArguments().containsKey(RepoUtility.ARG_REPO)) {
            Repo repo = (Repo) getArguments().getSerializable(RepoUtility.ARG_REPO);
            observeBranchList(repo);
        }
        initRecyclerView();
        return view;
    }
    private void observeBranchList(Repo repo) {
        mViewModel.getBranchList(repo).observe(this, branchList -> {
            if(branchList==null || branchList.size()==0) {
                mBinding.branchRecyclerView.setVisibility(View.GONE);
                mBinding.msg.setVisibility(View.VISIBLE);
                if(!RepoUtility.isNetworkAvailableAndConnected(getContext()))
                    RepoUtility.showSnackbar(getView(),getString(R.string.network_err));
            }
            else {
                mBinding.branchRecyclerView.setVisibility(View.VISIBLE);
                mBinding.msg.setVisibility(View.GONE);
                mBranchAdapter.setList(branchList,repo);
            }
        });
    }
    private void initRecyclerView() {
        mBinding.branchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBranchAdapter=new BranchAdapter(getContext());
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity().getApplicationContext(), VERTICAL);
        mBinding.branchRecyclerView.addItemDecoration(decoration);
        mBinding.branchRecyclerView.setAdapter(mBranchAdapter);
    }
}