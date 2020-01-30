package com.murayyan.movietime.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.murayyan.movietime.BuildConfig;
import com.murayyan.movietime.model.MovieDetail;
import com.murayyan.movietime.model.MovieResponse;
import com.murayyan.movietime.model.Movies;
import com.murayyan.movietime.services.ApiService;
import com.murayyan.movietime.services.RetrofitServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private ApiService apiService;
    private static MovieRepository repository;
    String API_KEY = BuildConfig.API_KEY;

    private MovieRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public static MovieRepository getInstance() {
        if (repository == null) {
            repository = new MovieRepository(RetrofitServices.createService(ApiService.class));
        }
        return repository;
    }

    public MutableLiveData<ArrayList<Movies>> getMovie() {
        final MutableLiveData<ArrayList<Movies>> listMovie = new MutableLiveData<>();
        apiService.getMovie(API_KEY).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        listMovie.setValue(response.body().getResults());
                    }
                }
                Log.d("Failure Get Movie 1", response.message());
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                Log.e("Failure Get Movie 2", t.getMessage());
            }
        });
        return listMovie;
    }

    public MutableLiveData<MovieDetail> getMovieDetail(int movieId) {
        MutableLiveData<MovieDetail> listDetailMovie = new MutableLiveData<>();
        apiService.getMovieDetail(movieId, API_KEY).enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(@NonNull Call<MovieDetail> call, @NonNull Response<MovieDetail> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        listDetailMovie.setValue(response.body());
                    }
                }
                Log.d("Failure Detail Movie 1", response.message());
            }

            @Override
            public void onFailure(@NonNull Call<MovieDetail> call, @NonNull Throwable t) {
                Log.e("Failure Detail Movie 2", t.getMessage());
            }
        });
        return listDetailMovie;
    }

    public MutableLiveData<ArrayList<Movies>> getSearchMovie(String query) {
        final MutableLiveData<ArrayList<Movies>> listMovie = new MutableLiveData<>();
        apiService.getSearchMovie(query, API_KEY).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        listMovie.setValue(response.body().getResults());
                    }
                }
                Log.d("Failure Get Movie 1", response.message());
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                Log.e("Failure Get Movie 2", t.getMessage());
            }
        });
        return listMovie;
    }
}
