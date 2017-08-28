package com.droidapp.ivanelv.eyesmovies.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by if on 28/08/17.
 */

public class MovieTrailer implements Parcelable
{
    @SerializedName("id")
    private int id;

    @SerializedName("results")
    private List<MovieTrailerDetail> results;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public List<MovieTrailerDetail> getResults()
    {
        return results;
    }

    public void setResults(List<MovieTrailerDetail> results)
    {
        this.results = results;
    }

    public static Creator<MovieTrailer> getCREATOR()
    {
        return CREATOR;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(this.id);
        dest.writeList(this.results);
    }

    public MovieTrailer()
    {
    }

    protected MovieTrailer(Parcel in)
    {
        this.id = in.readInt();
        this.results = new ArrayList<MovieTrailerDetail>();
        in.readList(this.results, MovieTrailerDetail.class.getClassLoader());
    }

    public static final Parcelable.Creator<MovieTrailer> CREATOR = new Parcelable.Creator<MovieTrailer>()
    {
        @Override
        public MovieTrailer createFromParcel(Parcel source)
        {
            return new MovieTrailer(source);
        }

        @Override
        public MovieTrailer[] newArray(int size)
        {
            return new MovieTrailer[size];
        }
    };
}
