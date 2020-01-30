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
import com.murayyan.movietime.activities.DetailTvShowActivity;
import com.murayyan.movietime.activities.ItemClickSupport;
import com.murayyan.movietime.adapter.TvShowAdapter;
import com.murayyan.movietime.db.TvShowHelper;
import com.murayyan.movietime.model.TvShow;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvShowFragment extends Fragment {
    private ArrayList<TvShow> list = new ArrayList<>();
    private TvShowHelper tvshowHelper;
    private TvShowAdapter tvshowAdapter;

    @BindView(R.id.rv_fav_tvshows)
    RecyclerView rv_fav_tvshows;
    @BindView(R.id.empty)
    TextView empty;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    public FavoriteTvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        tvshowHelper = TvShowHelper.getInstance(view.getContext());
        tvshowHelper.open();
        progressBar.setVisibility(View.VISIBLE);
        build(view.getContext());
    }

    private void build(Context context) {

        rv_fav_tvshows.setLayoutManager(new LinearLayoutManager(context));
        rv_fav_tvshows.setHasFixedSize(true);
        tvshowAdapter = new TvShowAdapter();
        tvshowAdapter.setListTvShow(list);
        rv_fav_tvshows.setAdapter(tvshowAdapter);
        ItemClickSupport.addTo(rv_fav_tvshows).setOnItemClickListener((recyclerView, position, v) -> {
            Intent intent = new Intent(recyclerView.getContext(), DetailTvShowActivity.class);
            intent.putExtra(DetailTvShowActivity.EXTRA_TVSHOW, list.get(position));
            startActivityForResult(intent, DetailTvShowActivity.REQUEST_CODE);
        });
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        list = tvshowHelper.getAllTvShows();
        tvshowAdapter.setListTvShow(list);
        if(list.isEmpty()){
            rv_fav_tvshows.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }else{
            empty.setVisibility(View.GONE);
            rv_fav_tvshows.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tvshowHelper.close();
    }
}
