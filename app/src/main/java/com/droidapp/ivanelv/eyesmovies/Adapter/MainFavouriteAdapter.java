package com.droidapp.ivanelv.eyesmovies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.droidapp.ivanelv.eyesmovies.API.ApiClient;
import com.droidapp.ivanelv.eyesmovies.API.IEndpoint;
import com.droidapp.ivanelv.eyesmovies.Model.LocalMovie;
import com.droidapp.ivanelv.eyesmovies.Model.Movie;
import com.droidapp.ivanelv.eyesmovies.MovieDetailActivity;
import com.droidapp.ivanelv.eyesmovies.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.droidapp.ivanelv.eyesmovies.API.Contract.PATH_IMAGE_MOBILE_SIZE;

/**
 * Created by if on 22/08/17.
 */

public class MainFavouriteAdapter extends BaseAdapter
{
    private Context mContext;
    private List<LocalMovie> movies;

    public MainFavouriteAdapter(Context mContext, List<LocalMovie> movies)
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

        String favMoviesPosterImagePath = mContext.getFilesDir() + movies.get(position).getPoster_path();
        File posterImage = new File(favMoviesPosterImagePath);

        if (posterImage.exists())
        {
            Picasso
                    .with(mContext)
                    .load(posterImage)
                    .placeholder(R.drawable.iv_placeholder)
                    .fit()
                    .into(ivMovie);
        }
        else
        {
            Picasso
                    .with(mContext)
                    .load(PATH_IMAGE_MOBILE_SIZE + movies.get(position).getPoster_path())
                    .placeholder(R.drawable.iv_placeholder)
                    .fit()
                    .into(ivMovie, new Callback()
                    {
                        @Override
                        public void onSuccess()
                        {
                            downloadImage(position);
                        }

                        @Override
                        public void onError()
                        {

                        }
                    });
        }

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

                Movie movie = new Movie(
                        movies.get(position).getVote_count(),
                        movies.get(position).getMovie_id(),
                        movies.get(position).getVote_average(),
                        movies.get(position).getPopularity(),
                        movies.get(position).isVideo(),
                        movies.get(position).isAdult(),
                        movies.get(position).getTitle(),
                        movies.get(position).getPoster_path(),
                        movies.get(position).getOriginal_language(),
                        movies.get(position).getOriginal_title(),
                        movies.get(position).getBackdrop_path(),
                        movies.get(position).getOverview(),
                        movies.get(position).getRelease_date(),
                        new ArrayList<Integer>()
                );

                intent.putExtra("MOVIE_DATA", movie);

                // Go To Movie Detail Activity
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    private void downloadImage(final int position)
    {
        Retrofit retrofitImage = new ApiClient().getImageClient();
        IEndpoint apiService = retrofitImage.create(IEndpoint.class);

        Call<ResponseBody> call = apiService.getBackdropImage(movies.get(position).getPoster_path());
        call.enqueue(new retrofit2.Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                InputStream is = null;
                FileOutputStream fos = null;

                try
                {
                    is = response.body().byteStream();
                    fos = new FileOutputStream(mContext.getFilesDir() + movies.get(position).getPoster_path());
                    int c;

                    while ((c = is.read()) != -1)
                    {
                        fos.write(c);
                    }
                }
                catch (FileNotFoundException e)
                {
                    Log.w("__ERRROR__", e.toString());
                }
                catch (IOException e)
                {
                    Log.w("__ERRROR__", e.toString());
                }
                finally
                {
                    if (is != null)
                    {
                        try
                        {
                            is.close();
                        }
                        catch (IOException e)
                        {
                            Log.w("__ERRROR__", e.toString());
                        }
                    }
                    if (fos != null)
                    {
                        try
                        {
                            fos.close();
                        }
                        catch (IOException e)
                        {
                            Log.w("__ERRROR__", e.toString());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {

            }
        });
    }
}
