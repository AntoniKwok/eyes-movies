package com.droidapp.ivanelv.eyesmovies.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.droidapp.ivanelv.eyesmovies.Model.MovieTrailerDetail;
import com.droidapp.ivanelv.eyesmovies.R;

import java.util.List;

/**
 * Created by if on 28/08/17.
 */

public class MovieTrailerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context mContext;

    private List<MovieTrailerDetail> movieTrailerList;

    public static class BaseHolder extends RecyclerView.ViewHolder
    {
        public BaseHolder(@LayoutRes int resId, ViewGroup parent)
        {
            super(LayoutInflater
                    .from(parent.getContext())
                    .inflate(resId, parent, false));
        }
    }

    public static class MovieTrailerViewHolder extends BaseHolder
    {
        private TextView
                    tvSiteLink,
                    tvTrailerNumber;

        public MovieTrailerViewHolder(ViewGroup parent)
        {
            super(R.layout.content_movie_trailer, parent);
            tvSiteLink = (TextView) itemView.findViewById(R.id.tv_site_link);
            tvTrailerNumber = (TextView) itemView.findViewById(R.id.tv_trailer_number);
        }

        public void setSiteLink(String siteLink)
        {
            tvSiteLink.setText(siteLink);
        }

        public void setTrailerNumber(int number) { tvTrailerNumber.setText("Trailer " + number); }
    }

    public MovieTrailerAdapter(Context context, List<MovieTrailerDetail> movieTrailers)
    {
        this.mContext = context;
        this.movieTrailerList = movieTrailers;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new MovieTrailerViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        MovieTrailerViewHolder mainViewHolder = (MovieTrailerViewHolder) holder;

        String LINK_YOUTUBE_WATCH = "https://www.youtube.com/watch";
        String KEY_YOUTUBE_MOVIE_TRAILER = movieTrailerList.get(position).getKey();

        String PATH_FINAL_MOVIE_TRAILER = LINK_YOUTUBE_WATCH + "?v=" +KEY_YOUTUBE_MOVIE_TRAILER;

        mainViewHolder.setTrailerNumber(position + 1);
        mainViewHolder.setSiteLink(PATH_FINAL_MOVIE_TRAILER);
    }

    @Override
    public int getItemCount()
    {
        return movieTrailerList.size();
    }
}
