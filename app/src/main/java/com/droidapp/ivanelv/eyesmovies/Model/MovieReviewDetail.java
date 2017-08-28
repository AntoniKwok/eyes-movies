package com.droidapp.ivanelv.eyesmovies.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by if on 28/08/17.
 */

public class MovieReviewDetail implements Parcelable
{
    @SerializedName("id")
    private String id;

    @SerializedName("author")
    private String author;

    @SerializedName("content")
    private String content;

    @SerializedName("url")
    private String url;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }


    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.id);
        dest.writeString(this.author);
        dest.writeString(this.content);
        dest.writeString(this.url);
    }

    public MovieReviewDetail()
    {
    }

    protected MovieReviewDetail(Parcel in)
    {
        this.id = in.readString();
        this.author = in.readString();
        this.content = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<MovieReviewDetail> CREATOR = new Parcelable.Creator<MovieReviewDetail>()
    {
        @Override
        public MovieReviewDetail createFromParcel(Parcel source)
        {
            return new MovieReviewDetail(source);
        }

        @Override
        public MovieReviewDetail[] newArray(int size)
        {
            return new MovieReviewDetail[size];
        }
    };
}
