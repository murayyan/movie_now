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
import com.murayyan.movietime.BuildConfig;
import com.murayyan.movietime.R;
import com.murayyan.movietime.db.TvShowHelper;
import com.murayyan.movietime.env.Config;
import com.murayyan.movietime.model.Genre;
import com.murayyan.movietime.model.TvShow;
import com.murayyan.movietime.model.TvShowDetail;
import com.murayyan.movietime.viewmodel.TvShowViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailTvShowActivity extends AppCompatActivity {
    public static final String EXTRA_TVSHOW = "extra_tvshow";
    public static final Integer REQUEST_CODE = 110;
    private TvShow tvshowResults;
    private TvShowHelper tvshowHelper;
    private boolean isFavorite;
    String API_KEY = BuildConfig.API_KEY;
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
        setContentView(R.layout.activity_detail_tv_show);
        ButterKnife.bind(this);
        progressBar.setVisibility(View.VISIBLE);
        tvshowResults = getIntent().getParcelableExtra(EXTRA_TVSHOW);
        ViewModelProvider.NewInstanceFactory factory = new ViewModelProvider.NewInstanceFactory(){

            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new TvShowViewModel(tvshowResults.getIds());
            }
        };
        TvShowViewModel detailTvShowViewModel = new ViewModelProvider(this, factory).get(TvShowViewModel.class);
        detailTvShowViewModel.getTvShowDetail().observe(this, getTvShowDetailData);
    }

    private final Observer<TvShowDetail> getTvShowDetailData = new Observer<TvShowDetail>() {
        @Override
        public void onChanged(TvShowDetail tvshowDetailData) {
            tvshowHelper = TvShowHelper.getInstance(getApplicationContext());
            tvshowHelper.open();
            isFavorite = false;
            checkFavorite();
            setIconFavorite();
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            title.setTitle(tvshowDetailData.getTitle());
            desc.setText(tvshowDetailData.getOverview());
            score.setText(String.valueOf(tvshowDetailData.getVoteAverage()));
            final String OLD_FORMAT = "yyyy-MM-dd";
            final String NEW_FORMAT = "MMMM dd, yyyy";
            String oldDateString = tvshowDetailData.getReleaseDate();
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
            String urlPhoto = Config.IMAGE_URL_BASE_PATH + tvshowDetailData.getPosterPath();
            Glide.with(getBaseContext())
                    .load(urlPhoto)
                    .into(poster);
            String urlBackdrop = Config.IMAGE_URL_BASE_PATH + tvshowDetailData.getBackdropPath();
            Glide.with(getBaseContext())
                    .load(urlBackdrop)
                    .into(backdrop);
            genre.setText("");
            for (int i = 0; i < tvshowDetailData.getGenres().size(); i++) {
                Genre movieGenres = tvshowDetailData.getGenres().get(i);
                if (i < tvshowDetailData.getGenres().size() - 1) {
                    genre.append(movieGenres.getName() + ", ");
                } else {
                    genre.append(movieGenres.getName());
                }
            }
            favorite.setOnClickListener(v -> {
                Log.d("== My activity ===","OnClick is called");
                favoriteButtonPressed(tvshowResults, v);
            });
            progressBar.setVisibility(View.GONE);
        }
    };

    private void favoriteButtonPressed(TvShow tvshow, View view){
        if (isFavorite) {
            tvshowHelper.deleteTvShow(tvshow.getIds());
            isFavorite = false;
            Toast.makeText(view.getContext(), // <- Line changed
                    tvshow.getTitle()+" "+getString(R.string.remove_favorite_tv),
                    Toast.LENGTH_LONG).show();
            setIconFavorite();
        } else {
            tvshowHelper.insertTvShow(tvshow);
            isFavorite = true;
            Toast.makeText(view.getContext(), // <- Line changed
                    tvshow.getTitle()+" "+getString(R.string.add_favorite_tv),
                    Toast.LENGTH_LONG).show();
            setIconFavorite();
        }

    }

    private void checkFavorite() {
        ArrayList<TvShow> tvshowInDatabase = tvshowHelper.getAllTvShows();

        for (TvShow tvshow: tvshowInDatabase){
            if (this.tvshowResults.getIds() == tvshow.getIds()){
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
