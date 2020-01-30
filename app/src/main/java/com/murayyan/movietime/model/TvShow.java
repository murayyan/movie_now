package com.murayyan.movietime.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TvShow implements Parcelable {

    @SerializedName("id")
    private int ids;

    @SerializedName("poster_path")
    private String poster;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("name")
    private String title;

    @SerializedName("vote_average")
    private Double score;

    @SerializedName("first_air_date")
    private String release;

    @SerializedName("overview")
    private String desc;
    public TvShow() {
    }
    protected TvShow(Parcel in) {
        ids = in.readInt();
        poster = in.readString();
        backdropPath = in.readString();
        title = in.readString();
        if (in.readByte() == 0) {
            score = null;
        } else {
            score = in.readDouble();
        }
        release = in.readString();
        desc = in.readString();
    }

    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel in) {
            return new TvShow(in);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };

    public int getIds() {
        return ids;
    }

    public void setIds(int ids) {
        this.ids = ids;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackDropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(ids);
        parcel.writeString(poster);
        parcel.writeString(backdropPath);
        parcel.writeString(title);
        if (score == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(score);
        }
        parcel.writeString(release);
        parcel.writeString(desc);
    }
}
