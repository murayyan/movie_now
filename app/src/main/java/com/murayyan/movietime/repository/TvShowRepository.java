package com.murayyan.movietime.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.murayyan.movietime.BuildConfig;
import com.murayyan.movietime.model.TvShow;
import com.murayyan.movietime.model.TvShowDetail;
import com.murayyan.movietime.model.TvShowResponse;
import com.murayyan.movietime.services.ApiService;
import com.murayyan.movietime.services.RetrofitServices;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowRepository {
    private ApiService apiService;
    private static TvShowRepository repository;
    String API_KEY = BuildConfig.API_KEY;

    private TvShowRepository(ApiService apiService) {
        this.apiService = apiService;
    }
    public static TvShowRepository getInstance() {
        if (repository == null) {
            repository = new TvShowRepository(RetrofitServices.createService(ApiService.class));
        }
        return repository;
    }

    public MutableLiveData<List<TvShow>> getTvShow() {
        final MutableLiveData<List<TvShow>> listTvShow = new MutableLiveData<>();
        apiService.getTvShow(API_KEY).enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowResponse> call, @NonNull Response<TvShowResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        listTvShow.setValue(response.body().getResults());
                    }
                }
                Log.d("Failure Get Movie 1", response.message());
            }

            @Override
            public void onFailure(@NonNull Call<TvShowResponse> call, @NonNull Throwable t) {
                Log.e("Failure Get Movie 2", t.getMessage());
            }
        });
        return listTvShow;
    }

    public MutableLiveData<TvShowDetail> getTvShowDetail(int tvshowId) {
        MutableLiveData<TvShowDetail> listDetailTvShow = new MutableLiveData<>();
        apiService.getTvShowDetail(tvshowId, BuildConfig.API_KEY).enqueue(new Callback<TvShowDetail>() {
            @Override
            public void onResponse(@NonNull Call<TvShowDetail> call, @NonNull Response<TvShowDetail> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        listDetailTvShow.setValue(response.body());
                    }
                }
                Log.d("Failure Detail TvShow 1", response.message());
            }

            @Override
            public void onFailure(@NonNull Call<TvShowDetail> call, @NonNull Throwable t) {
                Log.e("Failure Detail TvShow 2", t.getMessage());
            }
        });
        return listDetailTvShow;
    }

    public MutableLiveData<List<TvShow>> getSearchTvShow(String query) {
        final MutableLiveData<List<TvShow>> listTvShow = new MutableLiveData<>();
        apiService.getSearchTvShow(query, API_KEY).enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowResponse> call, @NonNull Response<TvShowResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        listTvShow.setValue(response.body().getResults());
                    }
                }
                Log.d("Failure Get Movie 1", response.message());
            }

            @Override
            public void onFailure(@NonNull Call<TvShowResponse> call, @NonNull Throwable t) {
                Log.e("Failure Get Movie 2", t.getMessage());
            }
        });
        return listTvShow;
    }
}
