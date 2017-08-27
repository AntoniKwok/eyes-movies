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
import android.widget.GridView;

import com.droidapp.ivanelv.eyesmovies.API.ApiClient;
import com.droidapp.ivanelv.eyesmovies.API.ApiConfig;
import com.droidapp.ivanelv.eyesmovies.API.IEndpoint;
import com.droidapp.ivanelv.eyesmovies.Adapter.MainAdapter;
import com.droidapp.ivanelv.eyesmovies.Model.Movie;
import com.droidapp.ivanelv.eyesmovies.Model.MovieResponse;
import com.droidapp.ivanelv.eyesmovies.Receiver.NetworkChangeReceiver;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = MainActivity.class.getSimpleName();

    private Toolbar toolbar;

    private GridView gridView;

    private Parcelable parcelableData;

    private NetworkChangeReceiver networkChangeReceiver;

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

        // Initialize GridView
        gridView = (GridView) findViewById(R.id.grid_view);

        if (savedInstanceState != null)
        {
            // Now, Parcelable Data Should Be Save The Offline Data
            parcelableData = savedInstanceState.getParcelable("MOVIE_LIST_DATA");

            MovieResponse movieResponse = savedInstanceState.getParcelable("MOVIE_LIST_DATA");
            List<Movie> movieList = movieResponse.getResults();
            gridView.setAdapter(new MainAdapter(MainActivity.this, movieList));

            changeSortBy(savedInstanceState.getString("SORT_BY"));
            return;
        }

        // Change ActionBar Subtitle Sorted By Status
        changeSortBy(getString(R.string.subtitle_popularity));

        getPopularMovies();
    }

    public void getTopRatedMovies()
    {
        IEndpoint apiService  = ApiClient.getClient().create(IEndpoint.class);

        Call<MovieResponse> call = apiService.getTopRatedMovies(ApiConfig.MyAPIKey);
        call.enqueue(new Callback<MovieResponse>()
        {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response)
            {
                parcelableData = response.body();

                List<Movie> listMovies = response.body().getResults();
                gridView.setAdapter(new MainAdapter(MainActivity.this, listMovies));
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t)
            {
                Log.e(TAG, t.toString());
            }
        });
    }

    public void getPopularMovies()
    {
        IEndpoint apiService  = ApiClient.getClient().create(IEndpoint.class);

        Call<MovieResponse> call = apiService.getPopularMovies(ApiConfig.MyAPIKey);
        call.enqueue(new Callback<MovieResponse>()
        {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response)
            {
                parcelableData = response.body();

                List<Movie> listMovies = response.body().getResults();
                gridView.setAdapter(new MainAdapter(MainActivity.this, listMovies));
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t)
            {
                Log.e(TAG, t.toString());
            }
        });
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
    protected void onStop()
    {
        super.onStop();

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
                break;
        }

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        if (parcelableData != null)
        {
            outState.putParcelable("MOVIE_LIST_DATA", parcelableData);
            outState.putString("SORT_BY", getSupportActionBar().getSubtitle().toString());
        }
        super.onSaveInstanceState(outState);
    }
}
