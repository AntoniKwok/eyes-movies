package com.droidapp.ivanelv.eyesmovies.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by if on 28/08/17.
 */

public class MovieReview implements Parcelable
{
    @SerializedName("id")
    private int id;

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<MovieReviewDetail> results;

    @SerializedName("total_pages")
    private int total_pages;

    @SerializedName("total_results")
    private int total_results;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getPage()
    {
        return page;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    public List<MovieReviewDetail> getResults()
    {
        return results;
    }

    public void setResults(List<MovieReviewDetail> results)
    {
        this.results = results;
    }

    public int getTotal_pages()
    {
        return total_pages;
    }

    public void setTotal_pages(int total_pages)
    {
        this.total_pages = total_pages;
    }

    public int getTotal_results()
    {
        return total_results;
    }

    public void setTotal_results(int total_results)
    {
        this.total_results = total_results;
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
        dest.writeInt(this.page);
        dest.writeList(this.results);
        dest.writeInt(this.total_pages);
        dest.writeInt(this.total_results);
    }

    public MovieReview()
    {
    }

    protected MovieReview(Parcel in)
    {
        this.id = in.readInt();
        this.page = in.readInt();
        this.results = new ArrayList<MovieReviewDetail>();
        in.readList(this.results, MovieReviewDetail.class.getClassLoader());
        this.total_pages = in.readInt();
        this.total_results = in.readInt();
    }

    public static final Parcelable.Creator<MovieReview> CREATOR = new Parcelable.Creator<MovieReview>()
    {
        @Override
        public MovieReview createFromParcel(Parcel source)
        {
            return new MovieReview(source);
        }

        @Override
        public MovieReview[] newArray(int size)
        {
            return new MovieReview[size];
        }
    };
}
