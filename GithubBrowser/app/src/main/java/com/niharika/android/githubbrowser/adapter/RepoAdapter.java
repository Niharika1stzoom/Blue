package com.niharika.android.githubbrowser.adapter;

import android.content.Context;
import android.databinding.tool.util.StringUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.niharika.android.githubbrowser.R;
import com.niharika.android.githubbrowser.room.BaseRepo;
import com.niharika.android.githubbrowser.utilities.RepoUtility;

import java.util.List;


public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {
    Context mContext;
    private List<BaseRepo> mRepoList;
    public RepoAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_repo, parent, false);
        return new RepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        BaseRepo repo=mRepoList.get(position);
        holder.bind(repo);
    }

    @Override
    public int getItemCount() {
        if (mRepoList == null) {
            return 0;
        }
        return mRepoList.size();
    }
    public void setList(List<BaseRepo> repoList) {
        mRepoList = repoList;
        notifyDataSetChanged();
    }


    public class RepoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mRepoNameText,mRepoDescText;
        ImageButton mShareButton;
        public RepoViewHolder(@NonNull View itemView) {
            super(itemView);
            mRepoNameText=itemView.findViewById(R.id.repoName);
            mRepoDescText=itemView.findViewById(R.id.repoDesc);
            mShareButton=itemView.findViewById(R.id.shareButton);
            mShareButton.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }
        public void bind(BaseRepo repo) {
            mRepoNameText.setText(StringUtils.capitalize(repo.getName()));
            if(repo.getDescription()!=null)
            mRepoDescText.setText(StringUtils.capitalize(repo.getDescription()));
        }
        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.shareButton)
            {
                RepoUtility.shareRepo(mContext,mRepoList.get(getAdapterPosition()));
            }
            else {
                BaseRepo baseRepo = mRepoList.get(getAdapterPosition());
                Navigation.findNavController(view).navigate(
                        R.id.repoDetailFragment, RepoUtility.getRepoBundle(baseRepo));
            }
        }
    }
}
