package com.example.goran.mymoviedb.movies.adapters;

import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.goran.mymoviedb.R;
import com.example.goran.mymoviedb.data.model.list.Movie;
import com.example.goran.mymoviedb.movies.util.BaseUrl;
import com.example.goran.mymoviedb.movies.util.MovieUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Goran on 8.11.2017..
 */

public class SimpleMovieAdapter extends RecyclerView.Adapter<SimpleMovieAdapter.ViewHolder> implements BaseMovieAdapter {

    private List<Movie> movies;
    private MovieAdapterListener listener;

    public SimpleMovieAdapter() {
        this.movies = new ArrayList<>();
    }

    @Override
    public void addMovies(List<Movie> movies) {
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    public void clear() {
        movies.clear();
        notifyDataSetChanged();
    }

    @Override
    public void setListener(MovieAdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CardView listItem = (CardView) LayoutInflater.from(
                parent.getContext()).inflate(R.layout.list_item_simple, parent, false);

        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CardView listItem = holder.listItem;

        Movie movie = movies.get(position);

        SimpleDraweeView imgPoster = listItem.findViewById(R.id.img_item_poster_s);
        TextView txtTitle = listItem.findViewById(R.id.txt_item_title_s);

        txtTitle.setText(MovieUtils.formatTitle(movie.getTitle(), movie.getReleaseDate()));
        imgPoster.setImageURI(Uri.parse(BaseUrl.IMG_SMALL + movie.getPosterPath()));

        if (position == movies.size() - 1) {

            listener.onBottomReached();
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CardView listItem;

        ViewHolder(CardView cardView) {
            super(cardView);

            listItem = cardView;

            listItem.setOnClickListener(v -> {

                int id = movies.get(getAdapterPosition()).getId();

                if (listener != null) {
                    listener.onClick(id);
                }
            });
        }
    }
}