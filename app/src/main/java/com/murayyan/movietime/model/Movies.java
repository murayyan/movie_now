package com.murayyan.movietime.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Movies implements Parcelable {

    @SerializedName("id")
    private int ids;

    @SerializedName("poster_path")
    private String poster;

    @SerializedName("title")
    private String title;

    @SerializedName("vote_average")
    private Double score;

    @SerializedName("release_date")
    private String release;

    @SerializedName("overview")
    private String desc;

    @SerializedName("backdrop_path")
    private String backdropPath;

    public Movies() {
    }
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


    protected Movies(Parcel in) {
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

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

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
