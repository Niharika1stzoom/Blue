package com.niharika.android.githubtrends.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.niharika.android.githubtrends.R;
import com.niharika.android.githubtrends.databinding.ListItemRepoBinding;
import com.niharika.android.githubtrends.model.Repo;
import com.niharika.android.githubtrends.utility.RepoUtil;

import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {
    ListItemRepoBinding mBinding;
    Context mContext;
    private List<Repo> mRepoList;
    public RepoAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding=ListItemRepoBinding.inflate(LayoutInflater.from(mContext),parent,false);
        return new RepoViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        Repo repo=mRepoList.get(position);
        holder.bind(repo);
    }

    @Override
    public int getItemCount() {
        if (mRepoList == null) {
            return 0;
        }
        return mRepoList.size();
    }
    public void setList(List<Repo> repoList) {
        mRepoList = repoList;
        notifyDataSetChanged();
    }


    public class RepoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ListItemRepoBinding mBinding;
        public RepoViewHolder(@NonNull ListItemRepoBinding binding) {
            super(binding.getRoot());
            mBinding=binding;
            binding.getRoot().setOnClickListener(this);
        }
        public void bind(Repo repo) {
            if(repo.getFull_name()!=null)
            mBinding.itemTitle.setText(repo.getFull_name());
            if(repo.getDescription()!=null)
            mBinding.itemDesc.setText(repo.getDescription());
            if(repo.getLanguage()!=null)
            mBinding.itemImgLanguage.setText(repo.getLanguage());
            mBinding.itemTime.setText(String.format(mContext.getString(R.string.item_date),
                    RepoUtil.getDate(repo.getCreated_at()),
                    RepoUtil.getTime(repo.getCreated_at())));
            if (repo.getOwner().getAvatar_url() != null)
            RepoUtil.setImage(mContext, repo.getOwner().getAvatar_url(), mBinding.itemProfileImg);
            if(repo.getStarsCount().toString()!=null)
            mBinding.itemStars.setText(repo.getStarsCount().toString());
            if(repo.getForks_count().toString()!=null)
            mBinding.itemForks.setText(repo.getForks_count().toString());
        }
        @Override
        public void onClick(View view) {
            if(view.getId()==mBinding.btnShare.getId())
            {
                RepoUtil.shareRepo(mContext,mRepoList.get(getAdapterPosition()));
            }
            else
                viewRepo(mRepoList.get(getAdapterPosition()));
        }
        private void viewRepo(Repo repo) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(repo.getHtml_url()));
            mContext.startActivity(Intent.createChooser(browserIntent, "Choose one"));
        }
    }
}

