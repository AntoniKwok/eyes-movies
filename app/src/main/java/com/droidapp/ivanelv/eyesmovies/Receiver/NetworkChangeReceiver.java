package com.droidapp.ivanelv.eyesmovies.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;

import com.droidapp.ivanelv.eyesmovies.MainActivity;
import com.droidapp.ivanelv.eyesmovies.R;

/**
 * Created by if on 27/08/17.
 */

public class NetworkChangeReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();

        try
        {
            if (context instanceof MainActivity)
            {
                ConstraintLayout connectivityStateWrapper =
                        (ConstraintLayout) ((MainActivity) context).findViewById(R.id.tv_internet_state_wrapper);

                if (netInfo != null && netInfo.isConnectedOrConnecting())
                {
                    connectivityStateWrapper.setVisibility(View.INVISIBLE);

                    if (((MainActivity) context).getGridView().getAdapter() == null
                        && !((MainActivity) context).getCurrentSortMode().equals(context.getString(R.string.subtitle_favourite)))
                    {
                        ((MainActivity) context).changeSortBy(context.getString(R.string.subtitle_popularity));
                        ((MainActivity) context).getPopularMovies();
                    }
                }
                else
                {
                    connectivityStateWrapper.setVisibility(View.VISIBLE);
                }
            }
        }
        catch(Exception ex)
        {
            Log.w("RECEIVERRR", ex.toString());
        }
    }
}
