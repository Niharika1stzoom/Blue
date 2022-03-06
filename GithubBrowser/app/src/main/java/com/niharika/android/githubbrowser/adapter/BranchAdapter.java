package com.niharika.android.githubbrowser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;


import com.niharika.android.githubbrowser.Model.Branch;
import com.niharika.android.githubbrowser.utilities.RepoUtility;

import java.util.List;

public class BranchAdapter extends RecyclerView.Adapter<BranchAdapter.BranchViewHolder> {
    Context mContext;
    private List<Branch> mBranchList;
    public BranchAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public BranchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new BranchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BranchViewHolder holder, int position) {
        Branch branch=mBranchList.get(position);
        holder.bind(branch);
    }

    @Override
    public int getItemCount() {
        if (mBranchList == null) {
            return 0;
        }
        return mBranchList.size();
    }
    public void setList(List<Branch> BranchList) {
        mBranchList = BranchList;
        notifyDataSetChanged();
    }


    public class BranchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mBranchNameText;
        public BranchViewHolder(@NonNull View itemView) {
            super(itemView);
            mBranchNameText= (TextView) itemView;
            itemView.setOnClickListener(this);
        }

        public void bind(Branch branch) {
            if(branch.getName()!=null)
            mBranchNameText.setText(branch.getName());
        }
        @Override
        public void onClick(View view) {
            Branch branch=mBranchList.get(getAdapterPosition());
            Navigation.findNavController(view).navigate(
                    R.id.branchCommitFragment, RepoUtility.getBranchBundle(branch));
        }
    }
}