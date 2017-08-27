package com.droidapp.ivanelv.eyesmovies.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by if on 22/08/17.
 */

public class Movie implements Parcelable
{
    @SerializedName("vote_count")
    int vote_count;

    @SerializedName("id")
    int id;

    @SerializedName("vote_average")
    float vote_average;

    @SerializedName("popularity")
    float popularity;

    @SerializedName("video")
    boolean video;

    @SerializedName("adult")
    boolean adult;

    @SerializedName("title")
    String title;

    @SerializedName("poster_path")
    String poster_path;

    @SerializedName("original_language")
    String original_language;

    @SerializedName("original_title")
    String original_title;

    @SerializedName("backdrop_path")
    String backdrop_path;

    @SerializedName("overview")
    String overview;

    @SerializedName("release_date")
    String release_date;

    @SerializedName("genre_ids")
    List<Integer> genre_ids;

    public Movie(int vote_count, int id, float vote_average, float popularity, boolean video, boolean adult, String title, String poster_path, String original_language, String original_title, String backdrop_path, String overview, String release_date, List<Integer> genre_ids)
    {
        this.vote_count = vote_count;
        this.id = id;
        this.vote_average = vote_average;
        this.popularity = popularity;
        this.video = video;
        this.adult = adult;
        this.title = title;
        this.poster_path = poster_path;
        this.original_language = original_language;
        this.original_title = original_title;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.release_date = release_date;
        this.genre_ids = genre_ids;
    }

    public int getVote_count()
    {
        return vote_count;
    }

    public void setVote_count(int vote_count)
    {
        this.vote_count = vote_count;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public float getVote_average()
    {
        return vote_average;
    }

    public void setVote_average(float vote_average)
    {
        this.vote_average = vote_average;
    }

    public float getPopularity()
    {
        return popularity;
    }

    public void setPopularity(float popularity)
    {
        this.popularity = popularity;
    }

    public boolean isVideo()
    {
        return video;
    }

    public void setVideo(boolean video)
    {
        this.video = video;
    }

    public boolean isAdult()
    {
        return adult;
    }

    public void setAdult(boolean adult)
    {
        this.adult = adult;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getPoster_path()
    {
        return poster_path;
    }

    public void setPoster_path(String poster_path)
    {
        this.poster_path = poster_path;
    }

    public String getOriginal_language()
    {
        return original_language;
    }

    public void setOriginal_language(String original_language)
    {
        this.original_language = original_language;
    }

    public String getOriginal_title()
    {
        return original_title;
    }

    public void setOriginal_title(String original_title)
    {
        this.original_title = original_title;
    }

    public String getBackdrop_path()
    {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path)
    {
        this.backdrop_path = backdrop_path;
    }

    public String getOverview()
    {
        return overview;
    }

    public void setOverview(String overview)
    {
        this.overview = overview;
    }

    public String getRelease_date()
    {
        return release_date;
    }

    public void setRelease_date(String release_date)
    {
        this.release_date = release_date;
    }

    public List<Integer> getGenre_ids()
    {
        return genre_ids;
    }

    public void setGenre_ids(List<Integer> genre_ids)
    {
        this.genre_ids = genre_ids;
    }
    
    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(this.vote_count);
        dest.writeInt(this.id);
        dest.writeFloat(this.vote_average);
        dest.writeFloat(this.popularity);
        dest.writeByte(this.video ? (byte) 1 : (byte) 0);
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.title);
        dest.writeString(this.poster_path);
        dest.writeString(this.original_language);
        dest.writeString(this.original_title);
        dest.writeString(this.backdrop_path);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
        dest.writeList(this.genre_ids);
    }

    protected Movie(Parcel in)
    {
        this.vote_count = in.readInt();
        this.id = in.readInt();
        this.vote_average = in.readFloat();
        this.popularity = in.readFloat();
        this.video = in.readByte() != 0;
        this.adult = in.readByte() != 0;
        this.title = in.readString();
        this.poster_path = in.readString();
        this.original_language = in.readString();
        this.original_title = in.readString();
        this.backdrop_path = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
        this.genre_ids = new ArrayList<Integer>();
        in.readList(this.genre_ids, Integer.class.getClassLoader());
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>()
    {
        @Override
        public Movie createFromParcel(Parcel source)
        {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size)
        {
            return new Movie[size];
        }
    };
}
