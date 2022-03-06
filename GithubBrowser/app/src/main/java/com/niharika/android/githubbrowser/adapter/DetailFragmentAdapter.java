package com.niharika.android.githubbrowser.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.niharika.android.githubbrowser.Model.Repo;
import com.niharika.android.githubbrowser.fragment.BranchListFragment;
import com.niharika.android.githubbrowser.fragment.IssueListFragment;

public class DetailFragmentAdapter extends FragmentStateAdapter {
    private static final int TAB_ITEM_SIZE = 2;
    public static final String[] TAB_NAMES={"BRANCHES","ISSUES"};
    Repo mRepo;

    public String getTabName(int position)
    {
        if(position==1)
            return TAB_NAMES[position]+"("+mRepo.getOpen_issues_count()+")";
        else
            return TAB_NAMES[position];
    }


    public DetailFragmentAdapter(@NonNull Fragment fragment,Repo repo) {
        super(fragment);
        mRepo=repo;
    }

    @NonNull @Override public Fragment createFragment(int position) {
    if(position==0)
        return BranchListFragment.newInstance(mRepo);
    else
        return IssueListFragment.newInstance(mRepo);
    }
    @Override public int getItemCount() {
        return TAB_ITEM_SIZE;
    }

}