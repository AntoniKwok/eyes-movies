package com.droidapp.ivanelv.eyesmovies;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.droidapp.ivanelv.eyesmovies.API.ApiClient;
import com.droidapp.ivanelv.eyesmovies.API.ApiConfig;
import com.droidapp.ivanelv.eyesmovies.API.IEndpoint;
import com.droidapp.ivanelv.eyesmovies.Adapter.MovieReviewAdapter;
import com.droidapp.ivanelv.eyesmovies.Adapter.MovieTrailerAdapter;
import com.droidapp.ivanelv.eyesmovies.Model.Movie;
import com.droidapp.ivanelv.eyesmovies.Model.MovieReview;
import com.droidapp.ivanelv.eyesmovies.Model.MovieReviewDetail;
import com.droidapp.ivanelv.eyesmovies.Model.MovieTrailer;
import com.droidapp.ivanelv.eyesmovies.Model.MovieTrailerDetail;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.droidapp.ivanelv.eyesmovies.API.Contract.PATH_IMAGE_MOBILE_SIZE;

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

        fab = (FloatingActionButton) findViewById(R.id.btn_fav);

        if (savedInstanceState != null)
        {
            // When Screen Rotate, Load From Saved Instance State
            movieData = savedInstanceState.getParcelable("MOVIE_DATA");
        }
        else
        {
            // When Intent Started, Load From Intent Parcelable Data
            movieData = getIntent().getParcelableExtra("MOVIE_DATA");
        }

        // Set Up Image
        ivMovieDetail = (ImageView) findViewById(R.id.iv_movie_detail_picture);
        Picasso
                .with(this)
                .load(PATH_IMAGE_MOBILE_SIZE + movieData.getBackdrop_path())
                .placeholder(R.drawable.iv_placeholder)
                .fit()
                .into(ivMovieDetail);

        // Set Up Movie Detail Title
        tvTitle = (TextView) findViewById(R.id.tv_movie_detail_title);
        tvTitle.setText(movieData.getTitle());

        // Set Up Movie Detail Release Date
        tvReleaseDate = (TextView) findViewById(R.id.tv_movie_detail_release_date);
        tvReleaseDate.setText(movieData.getRelease_date());

        // Initialize Formatter The Float Number of Popularity and Rating
        DecimalFormat decimalFormat = new DecimalFormat("#.#");

        // Set Up The Movie Popularity
        String formattedPopularity = decimalFormat.format(movieData.getPopularity());
        tvPopularity = (TextView) findViewById(R.id.tv_movie_detail_popularity);
        tvPopularity.setText(formattedPopularity);

        // Set Up The Movie Rating
        String formattedRating = decimalFormat.format(movieData.getVote_average());
        tvRating = (TextView) findViewById(R.id.tv_movie_detail_top_rated);
        tvRating.setText(formattedRating);

        // Set Up Movie Synopsis Content
        tvSynopsisContent = (TextView) findViewById(R.id.tv_content_synopsis);
        tvSynopsisContent.setText(movieData.getOverview());

        // Set Up Movie Trailer
        rvTrailer = (RecyclerView) findViewById(R.id.rv_trailer);
        rvTrailer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        getMovieTrailer(movieData.getId());

        // Set Up Review
        rvReview = (RecyclerView) findViewById(R.id.rv_content_review);
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
                List<MovieTrailerDetail> trailerList = response.body().getResults();

                Log.w("TESTETEST", trailerList.toString());

                tAdapter = new MovieTrailerAdapter(MovieDetailActivity.this, trailerList);
                rvTrailer.setAdapter(tAdapter);
            }

            @Override
            public void onFailure(Call<MovieTrailer> call, Throwable t)
            {
                Log.e("ERROR__", t.toString());
            }
        });
    }

    /* TODO Not Done Yet */
    public void getMovieReview(int id)
    {
        IEndpoint apiService = ApiClient.getClient().create(IEndpoint.class);

        Call<MovieReview> call = apiService.getMovieReview(id, ApiConfig.MyAPIKey);
        call.enqueue(new Callback<MovieReview>()
        {
            @Override
            public void onResponse(Call<MovieReview> call, Response<MovieReview> response)
            {
                List<MovieReviewDetail> reviewList = response.body().getResults();

                rAdapter = new MovieReviewAdapter();
                rvTrailer.setAdapter(tAdapter);
            }

            @Override
            public void onFailure(Call<MovieReview> call, Throwable t)
            {

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

    public void openTrailer(View v)
    {
        TextView tvSiteLink = (TextView) v.findViewById(R.id.tv_site_link);

        Toast.makeText(this, tvSiteLink.getText().toString(), Toast.LENGTH_SHORT).show();
    }
}
