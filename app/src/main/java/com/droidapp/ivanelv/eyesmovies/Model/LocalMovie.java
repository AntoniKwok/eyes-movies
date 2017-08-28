package com.droidapp.ivanelv.eyesmovies.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

/**
 * Created by if on 28/08/17.
 */

public class LocalMovie
    extends SugarRecord
    implements Parcelable
{
    int vote_count;

    Long id;

    int movie_id;

    float vote_average;

    float popularity;

    boolean video;

    boolean adult;

    String title;

    String poster_path;

    String original_language;

    String original_title;

    String backdrop_path;

    String overview;

    String release_date;

    public LocalMovie()
    {
    }

    public LocalMovie(int vote_count, Long id, int movie_id, float vote_average, float popularity, boolean video, boolean adult, String title, String poster_path, String original_language, String original_title, String backdrop_path, String overview, String release_date)
    {
        this.vote_count = vote_count;
        this.id = id;
        this.movie_id = movie_id;
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
    }

    public int getVote_count()
    {
        return vote_count;
    }

    public void setVote_count(int vote_count)
    {
        this.vote_count = vote_count;
    }

    @Override
    public Long getId()
    {
        return id;
    }

    @Override
    public void setId(Long id)
    {
        this.id = id;
    }

    public int getMovie_id()
    {
        return movie_id;
    }

    public void setMovie_id(int movie_id)
    {
        this.movie_id = movie_id;
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

    public static Creator<LocalMovie> getCREATOR()
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
        dest.writeInt(this.vote_count);
        dest.writeValue(this.id);
        dest.writeInt(this.movie_id);
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
    }

    protected LocalMovie(Parcel in)
    {
        this.vote_count = in.readInt();
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.movie_id = in.readInt();
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
    }

    public static final Creator<LocalMovie> CREATOR = new Creator<LocalMovie>()
    {
        @Override
        public LocalMovie createFromParcel(Parcel source)
        {
            return new LocalMovie(source);
        }

        @Override
        public LocalMovie[] newArray(int size)
        {
            return new LocalMovie[size];
        }
    };
}
