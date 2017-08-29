package com.droidapp.ivanelv.eyesmovies.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.droidapp.ivanelv.eyesmovies.API.Contract.PATH_IMAGE_BASE;

/**
 * Created by if on 22/08/17.
 */

public class ApiClient
{
    private static Retrofit retrofit = null;

    public static Retrofit getClient()
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Contract.PATH_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    public Retrofit getImageClient()
    {
        return new Retrofit.Builder()
                .baseUrl(PATH_IMAGE_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
