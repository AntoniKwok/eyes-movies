package com.droidapp.ivanelv.eyesmovies.API;

import com.droidapp.ivanelv.eyesmovies.Model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by if on 22/08/17.
 */

public interface IEndpoint
{
    @GET(Contract.PATH_POPULAR_MOVIE)
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET(Contract.PATH_TOP_RATED_MOVIE)
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);
}
