package com.murayyan.movietime.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.murayyan.movietime.R;
import com.murayyan.movietime.env.Config;
import com.murayyan.movietime.model.Movies;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ListViewHolder>{
    private List<Movies> listMovie;

    public MovieAdapter() {
    }
    public void setListMovie(List<Movies> listMovie) {
        this.listMovie = listMovie;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        holder.bind(listMovie.get(position));

    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_poster)
        ImageView poster;
        @BindView(R.id.txt_title)
        TextView title;
        @BindView(R.id.txt_score)
        TextView score;
        @BindView(R.id.txt_release)
        TextView release;
        @BindView(R.id.txt_desc)
        TextView desc;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Movies movies){
            String urlPhoto = Config.IMAGE_URL_BASE_PATH + movies.getPoster();
            final String OLD_FORMAT = "yyyy-MM-dd";
            final String NEW_FORMAT = "MMMM dd, yyyy";
            String oldDateString = movies.getRelease();
            String newDateString = null;
            SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT, new Locale("en"));
            try {
                Date d = sdf.parse(oldDateString);
                sdf.applyPattern(NEW_FORMAT);
                newDateString = sdf.format(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Glide.with(itemView)
                    .load(urlPhoto)
                    .into(poster);
            title.setText(movies.getTitle());
            desc.setText(movies.getDesc());
            score.setText(String.valueOf(movies.getScore()));
            release.setText(newDateString);
        }
    }
}
