package com.droidapp.ivanelv.eyesmovies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.droidapp.ivanelv.eyesmovies.Model.Movie;
import com.droidapp.ivanelv.eyesmovies.MovieDetailActivity;
import com.droidapp.ivanelv.eyesmovies.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import static com.droidapp.ivanelv.eyesmovies.API.Contract.PATH_IMAGE_MOBILE_SIZE;

/**
 * Created by if on 22/08/17.
 */

public class MainAdapter extends BaseAdapter
{
    private Context mContext;
    private List<Movie> movies;

    public MainAdapter(Context mContext, List<Movie> movies)
    {
        this.mContext = mContext;
        this.movies = movies;
    }

    @Override
    public int getCount()
    {
        return movies.size();
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.content_movie, parent, false);
        }

        // Set Up The Movie Image
        final ImageView ivMovie = (ImageView) convertView.findViewById(R.id.iv_movie);

        Picasso
                .with(mContext)
                .load(PATH_IMAGE_MOBILE_SIZE + movies.get(position).getPoster_path())
                .placeholder(R.drawable.iv_placeholder)
                .fit()
                .into(ivMovie);

        // Set Up The Movie Title
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_movie_title);
        tvTitle.setText(movies.get(position).getTitle());

        DecimalFormat decimalFormat = new DecimalFormat("#.#");

        // Set Up The Movie Popularity
        TextView tvPopularity = (TextView) convertView.findViewById(R.id.tv_movie_popularity);
        String formattedPopularity = decimalFormat.format(movies.get(position).getPopularity());
        tvPopularity.setText(formattedPopularity);

        // Set Up The Movie Rating
        TextView tvRate = (TextView) convertView.findViewById(R.id.tv_movie_rate);
        String formattedRating = decimalFormat.format(movies.get(position).getVote_average());
        tvRate.setText(formattedRating);

        // Set Up Movie Clicked
        final ConstraintLayout rootView = (ConstraintLayout) convertView;
        rootView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Initialize Intent
                Intent intent = new Intent(mContext, MovieDetailActivity.class);
                intent.putExtra("MOVIE_DATA", movies.get(position));

                // Go To Movie Detail Activity
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }
}
