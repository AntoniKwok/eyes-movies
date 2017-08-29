package com.droidapp.ivanelv.eyesmovies.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.droidapp.ivanelv.eyesmovies.Model.MovieReviewDetail;
import com.droidapp.ivanelv.eyesmovies.R;

import java.util.List;

/**
 * Created by if on 28/08/17.
 */

public class MovieReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context mContext;
    private List<MovieReviewDetail> reviewList;

    private static class BaseHolder extends RecyclerView.ViewHolder
    {
        public BaseHolder(@LayoutRes int resId, ViewGroup parent)
        {
            super(LayoutInflater.from(parent.getContext()).inflate(resId, parent, false));
        }
    }

    private static class MovieReviewViewHolder extends BaseHolder
    {
        private TextView
                    tvAuthor,
                    tvReviewContent,
                    tvReviewUrl;

        public MovieReviewViewHolder(ViewGroup parent)
        {
            super(R.layout.content_movie_review, parent);
            tvAuthor = (TextView) itemView.findViewById(R.id.tv_author);
            tvReviewContent = (TextView) itemView.findViewById(R.id.tv_review_content);
            tvReviewUrl = (TextView) itemView.findViewById(R.id.tv_review_url);
        }

        public void setAuthor(String authorName)
        {
            tvAuthor.setText(authorName);
        }

        public void setReviewContent(String content)
        {
            tvReviewContent.setText(content);
        }

        public void setReviewUrl(String url)
        {
            tvReviewUrl.setText(url);
        }
    }

    public MovieReviewAdapter(Context mContext, List<MovieReviewDetail> reviewList)
    {
        this.mContext = mContext;
        this.reviewList = reviewList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new MovieReviewViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        MovieReviewViewHolder viewHolder = (MovieReviewViewHolder) holder;

        viewHolder.setAuthor(reviewList.get(position).getAuthor());
        viewHolder.setReviewContent(reviewList.get(position).getContent());
        viewHolder.setReviewUrl(reviewList.get(position).getUrl());
    }

    @Override
    public int getItemCount()
    {
        return reviewList == null ? 0 : reviewList.size();
    }
}
