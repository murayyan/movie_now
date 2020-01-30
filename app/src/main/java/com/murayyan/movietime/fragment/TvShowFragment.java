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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.murayyan.movietime.R;
import com.murayyan.movietime.activities.DetailTvShowActivity;
import com.murayyan.movietime.activities.ItemClickSupport;
import com.murayyan.movietime.activities.SearchActivity;
import com.murayyan.movietime.adapter.TvShowAdapter;
import com.murayyan.movietime.model.TvShow;
import com.murayyan.movietime.viewmodel.MovieViewModel;
import com.murayyan.movietime.viewmodel.TvShowViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {
    private ArrayList<TvShow> list = new ArrayList<>();
    @BindView(R.id.rv_tvshows)
    RecyclerView rv_tvshow;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private TvShowAdapter tvShowAdapter;
    private TvShowViewModel tvShowViewModel;

    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
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
        progressBar.setVisibility(View.VISIBLE);
        rv_tvshow.setLayoutManager(new LinearLayoutManager(context));
        rv_tvshow.setHasFixedSize(true);
        tvShowAdapter = new TvShowAdapter();
        ViewModelProvider.NewInstanceFactory factory = new ViewModelProvider.NewInstanceFactory(){

            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new TvShowViewModel();
            }
        };
        tvShowViewModel = new ViewModelProvider(this, factory).get(TvShowViewModel.class);
        tvShowViewModel.getTvShow().observe(getViewLifecycleOwner(), getTvShowData);
    }

    private final Observer<List<TvShow>> getTvShowData = new Observer<List<TvShow>>() {
        @Override
        public void onChanged(List<TvShow> tvshowResults) {
            tvShowAdapter.setListTvShow(tvshowResults);
            tvShowAdapter.notifyDataSetChanged();
            rv_tvshow.setAdapter(tvShowAdapter);
            progressBar.setVisibility(View.GONE);
            ItemClickSupport.addTo(rv_tvshow).setOnItemClickListener((recyclerView, position, v) -> {
                Intent intent = new Intent(recyclerView.getContext(), DetailTvShowActivity.class);
                intent.putExtra(DetailTvShowActivity.EXTRA_TVSHOW, tvshowResults.get(position));
                startActivityForResult(intent, DetailTvShowActivity.REQUEST_CODE);
            });
            progressBar.setVisibility(View.GONE);
        }
    };

    private void search(Menu menu) {
        SearchManager searchManager = (SearchManager) Objects.requireNonNull(getContext()).getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(Objects.requireNonNull(getActivity()).getComponentName()));
            searchView.setQueryHint(getString(R.string.search_tvshow));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Intent searchIntent = new Intent(getContext(), SearchActivity.class);
                    searchIntent.putExtra(SearchActivity.EXTRA_SEARCH, query);
                    searchIntent.setAction(SearchActivity.TV_SEARCH);
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
