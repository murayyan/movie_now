package com.murayyan.movietime.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.murayyan.movietime.model.TvShow;
import com.murayyan.movietime.model.TvShowDetail;
import com.murayyan.movietime.repository.TvShowRepository;

import java.util.List;

public class TvShowViewModel extends ViewModel {
    private MutableLiveData<List<TvShow>> listTvShow;
    private MutableLiveData<TvShowDetail> detailTvShow;
    private TvShowRepository tvshowRepository;
    public TvShowViewModel() {
        tvshowRepository = TvShowRepository.getInstance();
        listTvShow = tvshowRepository.getTvShow();
    }

    public TvShowViewModel(String query) {
        tvshowRepository = TvShowRepository.getInstance();
        listTvShow = tvshowRepository.getSearchTvShow(query);
    }

    public TvShowViewModel(int id) {
        tvshowRepository = TvShowRepository.getInstance();
        detailTvShow = tvshowRepository.getTvShowDetail(id);
    }

    public LiveData<List<TvShow>> getTvShow() {
        return listTvShow;
    }

    public LiveData<TvShowDetail> getTvShowDetail() {
        return detailTvShow;
    }


}
