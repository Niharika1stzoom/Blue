package com.niharika.android.githubbrowser.adapter;

import android.content.Context;
import android.databinding.tool.util.StringUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.niharika.android.githubbrowser.Model.BranchCommit;
import com.niharika.android.githubbrowser.R;
import com.niharika.android.githubbrowser.utilities.RepoUtility;

import java.util.List;

public class CommitAdapter extends RecyclerView.Adapter<CommitAdapter.CommitViewHolder> {
    Context mContext;
    private List<BranchCommit> mCommitList;

    public CommitAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public CommitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_commit, parent, false);
        return new CommitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommitViewHolder holder, int position) {
        BranchCommit commit = mCommitList.get(position);
        holder.bind(commit);
    }

    @Override
    public int getItemCount() {
        if (mCommitList == null) {
            return 0;
        }
        return mCommitList.size();
    }

    public void setList(List<BranchCommit> CommitList) {
        mCommitList = CommitList;
        notifyDataSetChanged();
    }


    public class CommitViewHolder extends RecyclerView.ViewHolder {
        TextView mCommitMsg, mCommitDate, mCommitCode, mUsername;
        ImageView mAvatar;

        public CommitViewHolder(@NonNull View itemView) {
            super(itemView);
            mCommitMsg = itemView.findViewById(R.id.commit_msg);
            mCommitDate = itemView.findViewById(R.id.date_label);
            mCommitCode = itemView.findViewById(R.id.commit_text);
            mUsername = itemView.findViewById(R.id.commit_username);
            mAvatar = itemView.findViewById(R.id.commit_avatar);
        }

        public void bind(BranchCommit commit) {

           if (commit.getCommit() != null && commit.getCommit().getMessage() != null)
                mCommitMsg.setText(StringUtils.capitalize(commit.getCommit().getMessage()));
           if(commit.getCommitter()!=null)
            if (commit.getCommitter().getAvatar_url() != null)
                RepoUtility.setImage(mContext, commit.getCommitter().getAvatar_url(), mAvatar);

            if (commit.getCommit().getCommitter() != null) {
                if (commit.getCommit().getCommitter().getDate() != null)
                    mCommitDate.setText(RepoUtility.getDateFormat(commit.getCommit().getCommitter().getDate()));
                if (commit.getCommit().getCommitter().getName() != null)
                    mUsername.setText(StringUtils.capitalize(
                            commit.getCommit().getCommitter().getName()));
            }
            if (commit.getSha() != null)
                mCommitCode.setText(commit.getSha().substring(0,6));
        }
    }
}
