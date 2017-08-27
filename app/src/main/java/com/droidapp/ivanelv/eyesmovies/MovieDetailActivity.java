package com.droidapp.ivanelv.eyesmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.droidapp.ivanelv.eyesmovies.Model.Movie;

public class MovieDetailActivity extends AppCompatActivity
{
    private Movie movieData;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movieData = getIntent().getParcelableExtra("MOVIE_DATA");

        this.setTitle(getString(R.string.movie_detail_title));
    }
}
