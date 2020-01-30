package com.murayyan.movietime.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.murayyan.movietime.R;
import com.murayyan.movietime.db.MovieHelper;
import com.murayyan.movietime.env.Config;
import com.murayyan.movietime.model.Genre;
import com.murayyan.movietime.model.MovieDetail;
import com.murayyan.movietime.model.Movies;
import com.murayyan.movietime.viewmodel.MovieViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    public static final Integer REQUEST_CODE = 110;
    private Movies movieResults;
    private MovieHelper movieHelper;
    private MovieViewModel detailMovieViewModel;
    private boolean isFavorite;
    @BindView(R.id.movie_poster)
    ImageView poster;
    @BindView(R.id.expandedImage)
    ImageView backdrop;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout title;
    @BindView(R.id.txt_score)
    TextView score;
    @BindView(R.id.txt_desc)
    TextView desc;
    @BindView(R.id.txt_genre)
    TextView genre;
    @BindView(R.id.txt_release)
    TextView release;
    @BindView(R.id.fab)
    ImageView favorite;
    @BindView(R.id.progressBarDetail)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);

        progressBar.setVisibility(View.VISIBLE);
        movieResults = getIntent().getParcelableExtra(EXTRA_MOVIE);
        ViewModelProvider.NewInstanceFactory factory = new ViewModelProvider.NewInstanceFactory(){

            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new MovieViewModel(movieResults.getIds());
            }
        };
        detailMovieViewModel = new ViewModelProvider(this, factory).get(MovieViewModel.class);
//        detailMovieViewModel.setDetailMovie(299536);
        detailMovieViewModel.getMovieDetail().observe(this, movieDetailData -> {
            movieHelper = MovieHelper.getInstance(getApplicationContext());
            movieHelper.open();
            isFavorite = false;
            checkFavorite();
            setIconFavorite();
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            title.setTitle(movieDetailData.getTitle());
            desc.setText(movieDetailData.getOverview());
            score.setText(String.valueOf(movieDetailData.getVoteAverage()));
            final String OLD_FORMAT = "yyyy-MM-dd";
            final String NEW_FORMAT = "MMMM dd, yyyy";
            String oldDateString = movieDetailData.getReleaseDate();
            String newDateString = null;
            SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT, new Locale("en"));
            try {
                Date d = sdf.parse(oldDateString);
                sdf.applyPattern(NEW_FORMAT);
                newDateString = sdf.format(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            release.setText(newDateString);
            String urlPhoto = Config.IMAGE_URL_BASE_PATH + movieDetailData.getPosterPath();
            Glide.with(getApplicationContext())
                    .load(urlPhoto)
                    .into(poster);
            String urlBackdrop = Config.IMAGE_URL_BASE_PATH + movieDetailData.getBackdropPath();
            Glide.with(getApplicationContext())
                    .load(urlBackdrop)
                    .into(backdrop);
            genre.setText("");
            for (int i = 0; i < movieDetailData.getGenres().size(); i++) {
                Genre movieGenres = movieDetailData.getGenres().get(i);
                if (i < movieDetailData.getGenres().size() - 1) {
                    genre.append(movieGenres.getName() + ", ");
                } else {
                    genre.append(movieGenres.getName());
                }
            }
            favorite.setOnClickListener(v -> {
                Log.d("== My activity ===","OnClick is called");
                favoriteButtonPressed(movieResults, v);
            });

            progressBar.setVisibility(View.GONE);
        });

    }

    private final Observer<MovieDetail> getMovieDetailData = new Observer<MovieDetail>() {
        @Override
        public void onChanged(MovieDetail movieDetailData) {
            movieHelper = MovieHelper.getInstance(getApplicationContext());
            movieHelper.open();
            isFavorite = false;
            checkFavorite();
            setIconFavorite();
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            title.setTitle(movieDetailData.getTitle());
            desc.setText(movieDetailData.getOverview());
            score.setText(String.valueOf(movieDetailData.getVoteAverage()));
            final String OLD_FORMAT = "yyyy-MM-dd";
            final String NEW_FORMAT = "MMMM dd, yyyy";
            String oldDateString = movieDetailData.getReleaseDate();
            String newDateString = null;
            SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT, new Locale("en"));
            try {
                Date d = sdf.parse(oldDateString);
                sdf.applyPattern(NEW_FORMAT);
                newDateString = sdf.format(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            release.setText(newDateString);
            String urlPhoto = Config.IMAGE_URL_BASE_PATH + movieDetailData.getPosterPath();
            Glide.with(getApplicationContext())
                    .load(urlPhoto)
                    .into(poster);
            String urlBackdrop = Config.IMAGE_URL_BASE_PATH + movieDetailData.getBackdropPath();
            Glide.with(getApplicationContext())
                    .load(urlBackdrop)
                    .into(backdrop);
            genre.setText("");
            for (int i = 0; i < movieDetailData.getGenres().size(); i++) {
                Genre movieGenres = movieDetailData.getGenres().get(i);
                if (i < movieDetailData.getGenres().size() - 1) {
                    genre.append(movieGenres.getName() + ", ");
                } else {
                    genre.append(movieGenres.getName());
                }
            }
            favorite.setOnClickListener(v -> {
                Log.d("== My activity ===","OnClick is called");
                favoriteButtonPressed(movieResults, v);
            });

            progressBar.setVisibility(View.GONE);
        }
    };


    private void favoriteButtonPressed(Movies movies, View view){
        if (isFavorite) {
            movieHelper.deleteMovie(movies.getIds());
            isFavorite = false;
            Toast.makeText(view.getContext(), // <- Line changed
                    movies.getTitle()+" "+getString(R.string.remove_favorite_movie),
                    Toast.LENGTH_LONG).show();
            setIconFavorite();
        } else {
            movieHelper.insertMovie(movies);
            isFavorite = true;
            Toast.makeText(view.getContext(), // <- Line changed
                    movies.getTitle()+" "+getString(R.string.add_favorit_movie),
                    Toast.LENGTH_LONG).show();
            setIconFavorite();
        }

    }

    private void checkFavorite() {
        ArrayList<Movies> moviesInDatabase = movieHelper.getAllMovies();

        for (Movies movies: moviesInDatabase){
            if (this.movieResults.getIds() == movies.getIds()){
                isFavorite = true;
            }

            if (isFavorite == true) {
                break;
            }
        }
    }
    private void setIconFavorite(){
        if (isFavorite) {
           favorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_white_24dp));
        } else {
            favorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));
        }
    }
}
