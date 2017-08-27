package com.droidapp.ivanelv.eyesmovies.API;

/**
 * Created by if on 22/08/17.
 */

public class Contract
{
    public static final String PATH_BASE = "http://api.themoviedb.org/3/";

    public static final String PATH_IMAGE_BASE = "http://image.tmdb.org/t/p/";

    public static final String SIZE_MOBILE = "w185";

    /*
            http://image.tmdb.org/t/p/w185
     */
    public static final String PATH_IMAGE_MOBILE_SIZE = PATH_IMAGE_BASE + SIZE_MOBILE;

    public static final String PATH_POPULAR_MOVIE = "movie/popular";
    public static final String PATH_TOP_RATED_MOVIE = "movie/top_rated";
}
