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
import com.niharika.android.githubbrowser.Model.Issue;
import com.niharika.android.githubbrowser.R;
import com.niharika.android.githubbrowser.utilities.RepoUtility;

import java.util.List;

public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.IssueViewHolder> {
    Context mContext;
    private List<Issue> mIssueList;
    public IssueAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public IssueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_issue, parent, false);
        return new IssueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IssueViewHolder holder, int position) {
        Issue issue=mIssueList.get(position);
        holder.bind(issue);
    }

    @Override
    public int getItemCount() {
        if (mIssueList == null) {
            return 0;
        }
        return mIssueList.size();
    }
    public void setList(List<Issue> IssueList) {
        mIssueList = IssueList;
        notifyDataSetChanged();
    }


    public class IssueViewHolder extends RecyclerView.ViewHolder{
        TextView mIssueCreatorText,mIssueTitleText;
        ImageView mUserAvatar;
        public IssueViewHolder(@NonNull View itemView) {
            super(itemView);
            mIssueTitleText=itemView.findViewById(R.id.issue_title);
            mIssueCreatorText=itemView.findViewById(R.id.issue_username);
            mUserAvatar=itemView.findViewById(R.id.issue_avatar);
        }
        public void bind(Issue issue) {
            if(issue.getTitle()!=null)
            mIssueTitleText.setText(StringUtils.capitalize(issue.getTitle()));
            if(issue.getUser().getLogin()!=null)
                mIssueCreatorText.setText(StringUtils.capitalize(issue.getUser().getLogin()));
            RepoUtility.setImage(mContext,issue.getUser().getAvatar_url(),mUserAvatar);
        }
    }
}
