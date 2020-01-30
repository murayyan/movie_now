package com.murayyan.movietime.fragment;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.murayyan.movietime.R;
import com.murayyan.movietime.activities.DetailMovieActivity;
import com.murayyan.movietime.activities.ItemClickSupport;
import com.murayyan.movietime.activities.SearchActivity;
import com.murayyan.movietime.adapter.MovieAdapter;
import com.murayyan.movietime.model.Movies;
import com.murayyan.movietime.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private ArrayList<Movies> list = new ArrayList<>();

    @BindView(R.id.rv_movies)
    RecyclerView rv_movies;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private MovieAdapter movieAdapter;
    private MovieViewModel movieViewModel;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        progressBar.setVisibility(View.VISIBLE);
        build(view.getContext());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        search(menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    private void build(Context context) {

        rv_movies.setLayoutManager(new LinearLayoutManager(context));
        rv_movies.setHasFixedSize(true);
        movieAdapter = new MovieAdapter();
        ViewModelProvider.NewInstanceFactory factory = new ViewModelProvider.NewInstanceFactory(){

            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new MovieViewModel();
            }
        };

        movieViewModel = new ViewModelProvider(this, factory).get(MovieViewModel.class);
//        movieViewModel.setMovie();
        movieViewModel.getMovie().observe(getViewLifecycleOwner(), movieResults -> {
            movieAdapter.setListMovie(movieResults);
            movieAdapter.notifyDataSetChanged();
            rv_movies.setAdapter(movieAdapter);

            ItemClickSupport.addTo(rv_movies).setOnItemClickListener((recyclerView, position, v) -> {
                Intent intent = new Intent(recyclerView.getContext(), DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieResults.get(position));
                startActivityForResult(intent, DetailMovieActivity.REQUEST_CODE);
            });
            progressBar.setVisibility(View.GONE);
        });
    }

//    private final Observer<List<Movies>> getMovieData = new Observer<List<Movies>>() {
//        @Override
//        public void onChanged(List<Movies> movieResults) {
//            movieAdapter.setListMovie(movieResults);
//            movieAdapter.notifyDataSetChanged();
//            rv_movies.setAdapter(movieAdapter);
//
//            ItemClickSupport.addTo(rv_movies).setOnItemClickListener((recyclerView, position, v) -> {
//                Intent intent = new Intent(recyclerView.getContext(), DetailMovieActivity.class);
//                intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieResults.get(position));
//                startActivityForResult(intent, DetailMovieActivity.REQUEST_CODE);
//            });
//            progressBar.setVisibility(View.GONE);
//        }
//    };

    private void search(Menu menu) {
        SearchManager searchManager = (SearchManager) Objects.requireNonNull(getContext()).getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
           SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(Objects.requireNonNull(getActivity()).getComponentName()));
            searchView.setQueryHint(getString(R.string.search_movie));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Intent searchIntent = new Intent(getContext(), SearchActivity.class);
                    searchIntent.putExtra(SearchActivity.EXTRA_SEARCH, query);
                    searchIntent.setAction(SearchActivity.MOVIE_SEARCH);
                    startActivity(searchIntent);
                    return true;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
    }

}
