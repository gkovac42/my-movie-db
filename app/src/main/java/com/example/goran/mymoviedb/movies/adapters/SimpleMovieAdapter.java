package com.example.goran.mymoviedb.movies.adapters;

import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.goran.mymoviedb.R;
import com.example.goran.mymoviedb.data.model.Movie;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Goran on 8.11.2017..
 */

public class SimpleMovieAdapter extends RecyclerView.Adapter<SimpleMovieAdapter.ViewHolder> {

    private static final String IMG_BASE_URL = "https://image.tmdb.org/t/p/w300";

    private List<Movie> movies;
    private ItemClickListener listener;

    public SimpleMovieAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    public void setDataSource(List<Movie> movies) {
        this.movies = movies;
    }

    public interface ItemClickListener {
        void onClick(int position);
    }

    public void setListener(ItemClickListener listener) {
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

        txtTitle.setText((movie.getTitle() + " (" + movie.getReleaseDate().substring(0, 4) + ")"));
        imgPoster.setImageURI(Uri.parse(IMG_BASE_URL + movie.getPosterPath()));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CardView listItem;

        ViewHolder(CardView cv) {
            super(cv);

            listItem = cv;

            listItem.setOnClickListener(v -> {

                int position = getAdapterPosition();

                if (listener != null) {
                    listener.onClick(position);
                }
            });
        }
    }
}