package com.droidapp.ivanelv.eyesmovies;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

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

    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        intentFilter.addAction(Intent.ACTION_MANAGE_NETWORK_USAGE);
        this.registerReceiver(networkChangeReceiver, intentFilter);

        /* TODO (1) Set Subtitle For ActionBar, Tell Sorted By ?*/
        /* TODO (2) Create Pull Down To Refresh */

        gridView = (GridView) findViewById(R.id.grid_view);

        getTopRatedMovies();
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
                Toast.makeText(this, "Popular", Toast.LENGTH_SHORT).show();
                break;
            case R.id.top_rated:
                Toast.makeText(this, "Top Rated", Toast.LENGTH_SHORT).show();
                break;
            case R.id.favourites:
                Toast.makeText(this, "Favourites", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }
}
