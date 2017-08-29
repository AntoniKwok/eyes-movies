package com.droidapp.ivanelv.eyesmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.droidapp.ivanelv.eyesmovies.API.ApiClient;
import com.droidapp.ivanelv.eyesmovies.API.ApiConfig;
import com.droidapp.ivanelv.eyesmovies.API.IEndpoint;
import com.droidapp.ivanelv.eyesmovies.Adapter.MovieReviewAdapter;
import com.droidapp.ivanelv.eyesmovies.Adapter.MovieTrailerAdapter;
import com.droidapp.ivanelv.eyesmovies.Model.LocalMovie;
import com.droidapp.ivanelv.eyesmovies.Model.Movie;
import com.droidapp.ivanelv.eyesmovies.Model.MovieReview;
import com.droidapp.ivanelv.eyesmovies.Model.MovieReviewDetail;
import com.droidapp.ivanelv.eyesmovies.Model.MovieTrailer;
import com.droidapp.ivanelv.eyesmovies.Model.MovieTrailerDetail;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.droidapp.ivanelv.eyesmovies.API.Contract.PATH_IMAGE_780_SIZE;

public class MovieDetailActivity extends AppCompatActivity
{
    private Movie movieData;

    private ImageView ivMovieDetail;

    private TextView
                tvTitle,
                tvReleaseDate,
                tvPopularity,
                tvRating,
                tvSynopsisContent;

    private RecyclerView rvTrailer, rvReview;

    private MovieTrailerAdapter tAdapter;

    private MovieReviewAdapter rAdapter;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        this.setTitle(getString(R.string.movie_detail_title));

        // Initialize FloatingActionButton
        fab = (FloatingActionButton) findViewById(R.id.btn_fav);

        // When Intent Started, Load From Intent Parcelable Data
        movieData = getIntent().getParcelableExtra("MOVIE_DATA");

        // Set Up Image
        ivMovieDetail = (ImageView) findViewById(R.id.iv_movie_detail_picture);

        // Set Up Movie Detail Title
        tvTitle = (TextView) findViewById(R.id.tv_movie_detail_title);

        // Set Up Movie Detail Release Date
        tvReleaseDate = (TextView) findViewById(R.id.tv_movie_detail_release_date);

        // Set Up Text View Popularity
        tvPopularity = (TextView) findViewById(R.id.tv_movie_detail_popularity);

        // Set Up The Movie Rating
        tvRating = (TextView) findViewById(R.id.tv_movie_detail_top_rated);

        // Set Up Movie Synopsis Content
        tvSynopsisContent = (TextView) findViewById(R.id.tv_content_synopsis);

        // Set Up Movie Trailer
        rvTrailer = (RecyclerView) findViewById(R.id.rv_trailer);
        getMovieTrailer(movieData.getId());

        // Set Up Review
        rvReview = (RecyclerView) findViewById(R.id.rv_content_review);
        getMovieReview(movieData.getId());

        // Switch FAB Image Resource Depend On Movie Favourite Status
        if (isFavourite())
        {
            fab.setImageResource(R.drawable.ic_favorite_color_24dp);
        }
        else
        {
            fab.setImageResource(R.drawable.ic_favorite_border_color_24dp);
        }
    }

    public void getMovieTrailer(int id)
    {
        IEndpoint apiService = ApiClient.getClient().create(IEndpoint.class);

        Call<MovieTrailer> call = apiService.getMovieTrailer(id, ApiConfig.MyAPIKey);
        call.enqueue(new Callback<MovieTrailer>()
        {
            @Override
            public void onResponse(Call<MovieTrailer> call, Response<MovieTrailer> response)
            {
                if (response.body().getResults() != null)
                {
                    List<MovieTrailerDetail> trailerList = response.body().getResults();

                    tAdapter = new MovieTrailerAdapter(MovieDetailActivity.this, trailerList);
                    rvTrailer.setAdapter(tAdapter);
                    rvTrailer.setLayoutManager(new LinearLayoutManager(MovieDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
                }
            }

            @Override
            public void onFailure(Call<MovieTrailer> call, Throwable t)
            {
                Log.e("ERROR_TRAILER", t.toString());
            }
        });
    }

    public void getMovieReview(int id)
    {
        IEndpoint apiService = ApiClient.getClient().create(IEndpoint.class);

        Call<MovieReview> call = apiService.getMovieReview(id, ApiConfig.MyAPIKey);
        call.enqueue(new Callback<MovieReview>()
        {
            @Override
            public void onResponse(Call<MovieReview> call, Response<MovieReview> response)
            {
                if (response.body().getResults() != null)
                {
                    List<MovieReviewDetail> reviewList = response.body().getResults();

                    rAdapter = new MovieReviewAdapter(MovieDetailActivity.this, reviewList);
                    rvReview.setAdapter(rAdapter);
                    rvReview.setLayoutManager(new LinearLayoutManager(MovieDetailActivity.this, LinearLayoutManager.VERTICAL, false));
                }
            }

            @Override
            public void onFailure(Call<MovieReview> call, Throwable t)
            {
                Log.e("ERROR_REVIEW", t.toString());
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        if (movieData != null)
        {
            outState.putParcelable("MOVIE_DATA", movieData);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);

        movieData = savedInstanceState.getParcelable("MOVIE_DATA");
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        // Image Load Logic
        String favMoviesBackdropImagePath = this.getFilesDir() + movieData.getBackdrop_path();
        File backdropImage = new File(favMoviesBackdropImagePath);

        if (backdropImage.exists())
        {
            Picasso
                    .with(this)
                    .load(backdropImage)
                    .placeholder(R.drawable.iv_placeholder)
                    .fit()
                    .into(ivMovieDetail);
        }
        else if (!backdropImage.exists())
        {
            Picasso
                    .with(this)
                    .load(PATH_IMAGE_780_SIZE + movieData.getBackdrop_path())
                    .placeholder(R.drawable.iv_placeholder)
                    .fit()
                    .into(ivMovieDetail, new com.squareup.picasso.Callback()
                    {
                        @Override
                        public void onSuccess()
                        {
                            downloadImage();
                        }

                        @Override
                        public void onError()
                        {

                        }
                    });
        }

        tvTitle.setText(movieData.getTitle());

        tvReleaseDate.setText(movieData.getRelease_date());

        // Initialize Formatter The Float Number of Popularity and Rating
        DecimalFormat decimalFormat = new DecimalFormat("#.#");

        // Set Up The Movie Popularity
        String formattedPopularity = decimalFormat.format(movieData.getPopularity());
        tvPopularity.setText(formattedPopularity);

        // Set Up The Movie Rating
        String formattedRating = decimalFormat.format(movieData.getVote_average());
        tvRating.setText(formattedRating);
        tvSynopsisContent.setText(movieData.getOverview());
    }

    public void markAsFavourite(View v)
    {
        if (!isFavourite())
        {
            LocalMovie favMovie = new LocalMovie(
                    movieData.getVote_count(),
                    null,
                    movieData.getId(),
                    movieData.getVote_average(),
                    movieData.getPopularity(),
                    movieData.isVideo(),
                    movieData.isAdult(),
                    movieData.getTitle(),
                    movieData.getPoster_path(),
                    movieData.getOriginal_language(),
                    movieData.getOriginal_title(),
                    movieData.getBackdrop_path(),
                    movieData.getOverview(),
                    movieData.getRelease_date()
            );

            favMovie.save();

            fab.setImageResource(R.drawable.ic_favorite_color_24dp);
        }
        else
        {
            LocalMovie favMovie =
                    (LocalMovie) Select.from(LocalMovie.class)
                            .where(Condition.prop("movieid").eq(movieData.getId()))
                            .first();
            favMovie.delete();

            fab.setImageResource(R.drawable.ic_favorite_border_color_24dp);
        }
    }

    private boolean isFavourite()
    {
        try
        {
            return Select.from(LocalMovie.class)
                    .where(Condition.prop("movieid").eq(movieData.getId()))
                    .first() != null;
        }
        catch (Exception ex)
        {
            Log.w("ERROR_IS_FAVOURITE", ex.toString());
            return false;
        }
    }

    public void openTrailer(View v)
    {
        TextView tvSiteLink = (TextView) v.findViewById(R.id.tv_site_link);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tvSiteLink.getText().toString()));

        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }
    }

    public void openReview(View v)
    {
        TextView tvReviewUrl = (TextView) v.findViewById(R.id.tv_review_url);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tvReviewUrl.getText().toString()));

        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }
    }

    private void downloadImage()
    {
        Retrofit retrofitImage = new ApiClient().getImageClient();
        IEndpoint apiService = retrofitImage.create(IEndpoint.class);

        Call<ResponseBody> call = apiService.getBackdropImage(movieData.getBackdrop_path());
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
                    fos = new FileOutputStream(getApplicationContext().getFilesDir() + movieData.getBackdrop_path());
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
