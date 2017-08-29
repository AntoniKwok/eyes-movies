package com.droidapp.ivanelv.eyesmovies;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.droidapp.ivanelv.eyesmovies.API.ApiClient;
import com.droidapp.ivanelv.eyesmovies.API.ApiConfig;
import com.droidapp.ivanelv.eyesmovies.API.IEndpoint;
import com.droidapp.ivanelv.eyesmovies.Adapter.MainAdapter;
import com.droidapp.ivanelv.eyesmovies.Adapter.MainFavouriteAdapter;
import com.droidapp.ivanelv.eyesmovies.Model.LocalMovie;
import com.droidapp.ivanelv.eyesmovies.Model.Movie;
import com.droidapp.ivanelv.eyesmovies.Model.MovieResponse;
import com.droidapp.ivanelv.eyesmovies.Receiver.NetworkChangeReceiver;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = MainActivity.class.getSimpleName();

    private String currentSortMode;

    public String getCurrentSortMode()
    {
        return currentSortMode;
    }

    private Toolbar toolbar;

    public GridView getGridView()
    {
        return gridView;
    }

    private GridView gridView;

    private Parcelable parcelableData;

    private List<LocalMovie> parcelableLocalMovie;

    private NetworkChangeReceiver networkChangeReceiver;

    private ProgressBar progressBar;

    private TextView tvNoView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Set Up Toolbar
        toolbar = (Toolbar) findViewById(R.id.included_toolbar_main);
        this.setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));

        // Initialize Connectivity Manager
        networkChangeReceiver = new NetworkChangeReceiver();

        // Set Up Progress Bar
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        // Set Up No View Text
        tvNoView = (TextView) findViewById(R.id.tv_no_views);

        // Initialize GridView
        gridView = (GridView) findViewById(R.id.grid_view);

        if (savedInstanceState == null)
        {
            // Change ActionBar Subtitle Sorted By Status
            changeSortBy(getString(R.string.subtitle_popularity));

            // Default Sort Mode
            getPopularMovies();
        }
    }

    public void getPopularMovies()
    {
        progressBar.setVisibility(View.VISIBLE);
        gridView.setAdapter(null);

        IEndpoint apiService = ApiClient.getClient().create(IEndpoint.class);

        Call<MovieResponse> call = apiService.getPopularMovies(ApiConfig.MyAPIKey);
        call.enqueue(new Callback<MovieResponse>()
        {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response)
            {
                parcelableData = response.body();

                List<Movie> listMovies = response.body().getResults();
                gridView.setAdapter(new MainAdapter(MainActivity.this, listMovies));
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t)
            {
                Log.e(TAG, t.toString());
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        tvNoView.setVisibility(View.INVISIBLE);

        currentSortMode = getString(R.string.subtitle_popularity);
    }

    public void getTopRatedMovies()
    {
        progressBar.setVisibility(View.VISIBLE);
        gridView.setAdapter(null);

        IEndpoint apiService = ApiClient.getClient().create(IEndpoint.class);

        Call<MovieResponse> call = apiService.getTopRatedMovies(ApiConfig.MyAPIKey);
        call.enqueue(new Callback<MovieResponse>()
        {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response)
            {
                parcelableData = response.body();

                List<Movie> listMovies = response.body().getResults();
                gridView.setAdapter(new MainAdapter(MainActivity.this, listMovies));
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t)
            {
                Log.e(TAG, t.toString());
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        tvNoView.setVisibility(View.INVISIBLE);

        currentSortMode = getString(R.string.subtitle_top_rated);
    }

    public void getFavouriteMovies()
    {
        progressBar.setVisibility(View.INVISIBLE);

        try
        {
            List<LocalMovie> favMovies = LocalMovie.listAll(LocalMovie.class);

            if (favMovies.size() > 0)
            {
                if (parcelableLocalMovie == null)
                {
                    parcelableLocalMovie = favMovies;
                }

                gridView.setAdapter(new MainFavouriteAdapter(MainActivity.this, favMovies));
                tvNoView.setVisibility(View.INVISIBLE);
            }
            else
            {
                gridView.setAdapter(null);
                tvNoView.setVisibility(View.VISIBLE);
            }
        }
        catch (Exception ex)
        {
            gridView.setAdapter(null);
            tvNoView.setVisibility(View.VISIBLE);
        }

        currentSortMode = getString(R.string.subtitle_favourite);
    }

    public void changeSortBy(String sortBy)
    {
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setSubtitle(sortBy);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        intentFilter.addAction(Intent.ACTION_MANAGE_NETWORK_USAGE);
        this.registerReceiver(networkChangeReceiver, intentFilter);
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        unregisterReceiver(networkChangeReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int menuId = item.getItemId();

        switch (menuId)
        {
            case R.id.popular:
                changeSortBy(getString(R.string.subtitle_popularity));
                getPopularMovies();
                break;
            case R.id.top_rated:
                changeSortBy(getString(R.string.subtitle_top_rated));
                getTopRatedMovies();
                break;
            case R.id.favourites:
                changeSortBy(getString(R.string.subtitle_favourite));
                getFavouriteMovies();
                break;
        }

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        if (currentSortMode.equals(getString(R.string.subtitle_favourite)))
        {
            outState.putParcelableArrayList(
                    "FAV_MOVIE_LIST",
                    (ArrayList<? extends Parcelable>) parcelableLocalMovie);

            outState.putString("SORT_BY", getString(R.string.subtitle_favourite));
        }

        if (parcelableData != null)
            outState.putParcelable("MOVIE_LIST_DATA", parcelableData);

        outState.putString("SORT_BY", currentSortMode);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        currentSortMode = savedInstanceState.getString("SORT_BY");
        changeSortBy(currentSortMode);

        if (currentSortMode.equals(getString(R.string.subtitle_favourite)))
        {
            // Re-Initialize The Data For onSaveInstance Usage
            parcelableLocalMovie = savedInstanceState.getParcelableArrayList("FAV_MOVIE_LIST");

            List<LocalMovie> favMovies = savedInstanceState.getParcelableArrayList("FAV_MOVIE_LIST");

            if (favMovies != null && favMovies.size() > 0)
            {
                gridView.setAdapter(new MainFavouriteAdapter(MainActivity.this, favMovies));
            }
            else
            {
                tvNoView.setVisibility(View.VISIBLE);
            }
        }
        else if (savedInstanceState.getParcelable("MOVIE_LIST_DATA") != null)
        {
            // Re-Initialize The Data For onSaveInstance Usage
            parcelableData = savedInstanceState.getParcelable("MOVIE_LIST_DATA");

            MovieResponse movieResponse = savedInstanceState.getParcelable("MOVIE_LIST_DATA");
            List<Movie> movieList = movieResponse.getResults();
            gridView.setAdapter(new MainAdapter(MainActivity.this, movieList));
        }

        super.onRestoreInstanceState(savedInstanceState);
    }
}
