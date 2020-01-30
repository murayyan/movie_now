package com.murayyan.movietime.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import com.murayyan.movietime.R;
import com.murayyan.movietime.adapter.MovieAdapter;
import com.murayyan.movietime.adapter.TvShowAdapter;
import com.murayyan.movietime.model.Movies;
import com.murayyan.movietime.model.TvShow;
import com.murayyan.movietime.viewmodel.MovieViewModel;
import com.murayyan.movietime.viewmodel.TvShowViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {
    public static final String EXTRA_SEARCH = "extra_search";
    public static final String MOVIE_SEARCH = "movie_search";
    public static final String TV_SEARCH = "tv_search";
    private String setAction;
    private MovieAdapter movieSearchAdapter;
    private TvShowAdapter tvShowSearchAdapter;
    @BindView(R.id.rv_search)
    RecyclerView rv_search;
    @BindView(R.id.progress_bar_search)
    ProgressBar progressBar;
    Toolbar toolbar;
//    @BindView(R.id.searchView)
//    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);
        progressBar.setVisibility(View.VISIBLE);
        setAction = getIntent().getAction();
        rv_search.setLayoutManager(new LinearLayoutManager(this));
        rv_search.setHasFixedSize(true);

        String query = getIntent().getStringExtra(EXTRA_SEARCH);

        if (setAction != null) {
            if (setAction.equals(MOVIE_SEARCH)) {
                searchMovie(query);
            } else if (setAction.equals(TV_SEARCH)) {
                searchTvShow(query);
            }
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        searchView.setQuery(query, false);
//        searchView.setIconified(false);
//        searchView.clearFocus();
//
//        InputMethodManager in = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//        in.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                if (setAction != null) {
//                    if (setAction.equals(MOVIE_SEARCH)) {
//                        searchMovie(query);
//                    } else if (setAction.equals(TV_SEARCH)) {
//                        searchTvShow(query);
//                    }
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                return false;
//            }
//        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void searchMovie(String query) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.movie_search_result));
        }


        queryMovie(query);
    }

    private void queryMovie(String query) {

        ViewModelProvider.NewInstanceFactory factory = new ViewModelProvider.NewInstanceFactory(){

            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                Log.d("tes", query);
                return (T) new MovieViewModel(query);
            }
        };
        MovieViewModel movieViewModel = new ViewModelProvider(this, factory).get(MovieViewModel.class);
        movieViewModel.getMovie().observe(this, getSearchMovieData);
    }

    private void searchTvShow(String query) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.movie_search_result));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        tvShowSearchAdapter = new TvShowAdapter();
        ViewModelProvider.NewInstanceFactory factory = new ViewModelProvider.NewInstanceFactory(){

            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new TvShowViewModel(query);
            }
        };
        TvShowViewModel tvShowViewModel = new ViewModelProvider(this, factory).get(TvShowViewModel.class);
        tvShowViewModel.getTvShow().observe(this, getSearchTvShowData);
    }



    private final Observer<List<Movies>> getSearchMovieData = new Observer<List<Movies>>() {
        @Override
        public void onChanged(List<Movies> movieResults) {
            movieSearchAdapter = new MovieAdapter();
            movieSearchAdapter.setListMovie(movieResults);
            movieSearchAdapter.notifyDataSetChanged();
            rv_search.setAdapter(movieSearchAdapter);
            ItemClickSupport.addTo(rv_search).setOnItemClickListener((recyclerView, position, v) -> {
                Intent intent = new Intent(recyclerView.getContext(), DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieResults.get(position));
                startActivityForResult(intent, DetailMovieActivity.REQUEST_CODE);
            });
            progressBar.setVisibility(View.GONE);
        }
    };

    private final Observer<List<TvShow>> getSearchTvShowData = new Observer<List<TvShow>>() {
        @Override
        public void onChanged(List<TvShow> tvshowResults) {
            tvShowSearchAdapter.setListTvShow(tvshowResults);
            tvShowSearchAdapter.notifyDataSetChanged();
            rv_search.setAdapter(tvShowSearchAdapter);

            ItemClickSupport.addTo(rv_search).setOnItemClickListener((recyclerView, position, v) -> {
                Intent intent = new Intent(recyclerView.getContext(), DetailTvShowActivity.class);
                intent.putExtra(DetailTvShowActivity.EXTRA_TVSHOW, tvshowResults.get(position));
                startActivityForResult(intent, DetailTvShowActivity.REQUEST_CODE);
            });
            progressBar.setVisibility(View.GONE);
        }
    };
}
