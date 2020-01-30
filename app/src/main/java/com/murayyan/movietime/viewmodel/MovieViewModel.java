package com.murayyan.movietime.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.murayyan.movietime.model.MovieDetail;
import com.murayyan.movietime.model.Movies;
import com.murayyan.movietime.repository.MovieRepository;

import java.util.ArrayList;
import java.util.List;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Movies>> listMovie;
    private MutableLiveData<MovieDetail> detailMovie;
    private MovieRepository movieRepository;
    public MovieViewModel() { //constructor untuk view model RV movies
        movieRepository = MovieRepository.getInstance();
        listMovie = movieRepository.getMovie();
    }

    public MovieViewModel(int id) { //constructor untuk view model RV movies
        movieRepository = MovieRepository.getInstance();
        detailMovie = movieRepository.getMovieDetail(id);
    }

    public MovieViewModel(String query) { //constructor untuk view model RV movies
        movieRepository = MovieRepository.getInstance();
        listMovie = movieRepository.getSearchMovie(query);
    }

    public void setMovie(){
        movieRepository = MovieRepository.getInstance();
        listMovie = movieRepository.getMovie();
    }

    public void setDetailMovie(int id){
        movieRepository = MovieRepository.getInstance();
        detailMovie = movieRepository.getMovieDetail(id);
    }

    public LiveData<ArrayList<Movies>> getMovie() {
        return listMovie;
    }

    public LiveData<MovieDetail> getMovieDetail() {
        return detailMovie;
    }

}
