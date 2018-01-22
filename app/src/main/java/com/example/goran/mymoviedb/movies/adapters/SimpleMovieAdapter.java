package com.example.goran.mymoviedb.movies.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.goran.mymoviedb.R;
import com.example.goran.mymoviedb.data.model.list.Movie;
import com.example.goran.mymoviedb.movies.util.MovieUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Goran on 8.11.2017..
 */

public class SimpleMovieAdapter extends RecyclerView.Adapter<SimpleMovieAdapter.ViewHolder> {

    private static final String IMG_BASE_URL = "https://image.tmdb.org/t/p/w300";

    private List<Movie> movies;
    private MovieAdapterListener listener;

    public SimpleMovieAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    public SimpleMovieAdapter() {
        this.movies = new ArrayList<>();
    }

    public void setDataSource(List<Movie> movies) {
        this.movies = movies;
    }

    public void setListener(MovieAdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RelativeLayout listItem = (RelativeLayout) LayoutInflater.from(
                parent.getContext()).inflate(R.layout.list_item_simple, parent, false);

        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {

            RelativeLayout listItem = holder.listItem;

            Movie movie = movies.get(position);

            SimpleDraweeView imgPoster = listItem.findViewById(R.id.img_item_poster_s);
            TextView txtTitle = listItem.findViewById(R.id.txt_item_title_s);

            txtTitle.setText(MovieUtils.formatTitle(movie.getTitle(), movie.getReleaseDate()));
            imgPoster.setImageURI(Uri.parse(IMG_BASE_URL + movie.getPosterPath()));

            if (position == movies.size() - 1) {

                listener.onBottomReached();
            }

        } catch (Exception e) {
            // list response empty
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout listItem;

        ViewHolder(RelativeLayout relativeLayout) {
            super(relativeLayout);

            listItem = relativeLayout;

            listItem.setOnClickListener(v -> {

                Movie selectedMovie = movies.get(getAdapterPosition());

                if (listener != null) {
                    listener.onClick(selectedMovie.getId());
                }
            });
        }
    }
}