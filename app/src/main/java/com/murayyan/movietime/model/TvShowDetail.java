package com.murayyan.movietime.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowDetail implements Parcelable {

    @SerializedName("overview")
    private String overview;

    @SerializedName("name")
    private String title;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("first_air_date")
    private String releaseDate;

    @SerializedName("genres")
    private List<Genre> genres;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("id")
    private int id;

    protected TvShowDetail(Parcel in) {
        overview = in.readString();
        title = in.readString();
        posterPath = in.readString();
        backdropPath = in.readString();
        releaseDate = in.readString();
        genres = in.createTypedArrayList(Genre.CREATOR);
        voteAverage = in.readDouble();
        id = in.readInt();
    }

    public static final Creator<TvShowDetail> CREATOR = new Creator<TvShowDetail>() {
        @Override
        public TvShowDetail createFromParcel(Parcel in) {
            return new TvShowDetail(in);
        }

        @Override
        public TvShowDetail[] newArray(int size) {
            return new TvShowDetail[size];
        }
    };

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackDropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(overview);
        parcel.writeString(title);
        parcel.writeString(posterPath);
        parcel.writeString(backdropPath);
        parcel.writeString(releaseDate);
        parcel.writeTypedList(genres);
        parcel.writeDouble(voteAverage);
        parcel.writeInt(id);
    }
}
