package com.murayyan.movietime.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.murayyan.movietime.R;
import com.murayyan.movietime.activities.DetailMovieActivity;
import com.murayyan.movietime.activities.ItemClickSupport;
import com.murayyan.movietime.adapter.MovieAdapter;
import com.murayyan.movietime.db.MovieHelper;
import com.murayyan.movietime.model.Movies;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment {
    private ArrayList<Movies> list = new ArrayList<>();
    private MovieHelper movieHelper;
    private MovieAdapter movieAdapter;

    @BindView(R.id.rv_fav_movies)
    RecyclerView rv_fav_movies;
    @BindView(R.id.empty)
    TextView empty;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    public FavoriteMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        movieHelper = MovieHelper.getInstance(view.getContext());
        movieHelper.open();
        progressBar.setVisibility(View.VISIBLE);
        build(view.getContext());
    }

    private void build(Context context) {

        rv_fav_movies.setLayoutManager(new LinearLayoutManager(context));
        rv_fav_movies.setHasFixedSize(true);
        movieAdapter = new MovieAdapter();
        movieAdapter.setListMovie(list);
        rv_fav_movies.setAdapter(movieAdapter);
        ItemClickSupport.addTo(rv_fav_movies).setOnItemClickListener((recyclerView, position, v) -> {
            Intent intent = new Intent(recyclerView.getContext(), DetailMovieActivity.class);
            intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, list.get(position));
            startActivityForResult(intent, DetailMovieActivity.REQUEST_CODE);
        });
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        list = movieHelper.getAllMovies();
        movieAdapter.setListMovie(list);
        if(list.isEmpty()){
            rv_fav_movies.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }else{
            empty.setVisibility(View.GONE);
            rv_fav_movies.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        movieHelper.close();
    }

}
