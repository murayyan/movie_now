package com.murayyan.movietime.services;

import com.murayyan.movietime.model.MovieDetail;
import com.murayyan.movietime.model.MovieResponse;
import com.murayyan.movietime.model.TvShowDetail;
import com.murayyan.movietime.model.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("discover/movie")
    Call<MovieResponse> getMovie(@Query("api_key") String API_KEY);

    @GET("discover/tv")
    Call<TvShowResponse> getTvShow(@Query("api_key") String API_KEY);

    @GET("movie/{movie_id}")
    Call<MovieDetail> getMovieDetail(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

    @GET("tv/{tvshow_id}")
    Call<TvShowDetail> getTvShowDetail(@Path("tvshow_id") int tvshowId, @Query("api_key") String apiKey);

    @GET("search/movie")
    Call<MovieResponse> getSearchMovie(@Query("query") String query, @Query("api_key") String apiKey);

    @GET("search/tv")
    Call<TvShowResponse> getSearchTvShow(@Query("query") String query, @Query("api_key") String apiKey);

}
