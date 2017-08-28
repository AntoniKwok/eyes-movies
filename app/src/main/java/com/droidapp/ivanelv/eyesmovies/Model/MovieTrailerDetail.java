package com.droidapp.ivanelv.eyesmovies.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by if on 28/08/17.
 */

public class MovieTrailerDetail implements Parcelable
{
    @SerializedName("id")
    private String id;

    @SerializedName("iso_639_1")
    private String iso_1;

    @SerializedName("iso_3166_1")
    private String iso_2;

    @SerializedName("key")
    private String key;

    @SerializedName("name")
    private String name;

    @SerializedName("site")
    private String site;

    @SerializedName("size")
    private String size;

    @SerializedName("type")
    private String type;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getIso_1()
    {
        return iso_1;
    }

    public void setIso_1(String iso_1)
    {
        this.iso_1 = iso_1;
    }

    public String getIso_2()
    {
        return iso_2;
    }

    public void setIso_2(String iso_2)
    {
        this.iso_2 = iso_2;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSite()
    {
        return site;
    }

    public void setSite(String site)
    {
        this.site = site;
    }

    public String getSize()
    {
        return size;
    }

    public void setSize(String size)
    {
        this.size = size;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
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
        dest.writeString(this.iso_1);
        dest.writeString(this.iso_2);
        dest.writeString(this.key);
        dest.writeString(this.name);
        dest.writeString(this.site);
        dest.writeString(this.size);
        dest.writeString(this.type);
    }

    public MovieTrailerDetail()
    {
    }

    protected MovieTrailerDetail(Parcel in)
    {
        this.id = in.readString();
        this.iso_1 = in.readString();
        this.iso_2 = in.readString();
        this.key = in.readString();
        this.name = in.readString();
        this.site = in.readString();
        this.size = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<MovieTrailerDetail> CREATOR = new Parcelable.Creator<MovieTrailerDetail>()
    {
        @Override
        public MovieTrailerDetail createFromParcel(Parcel source)
        {
            return new MovieTrailerDetail(source);
        }

        @Override
        public MovieTrailerDetail[] newArray(int size)
        {
            return new MovieTrailerDetail[size];
        }
    };
}
