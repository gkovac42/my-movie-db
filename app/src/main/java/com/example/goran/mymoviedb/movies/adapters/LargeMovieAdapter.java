package com.example.goran.mymoviedb.movies.adapters;

import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.goran.mymoviedb.R;
import com.example.goran.mymoviedb.data.model.GenreUtils;
import com.example.goran.mymoviedb.data.model.Movie;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Goran on 8.11.2017..
 */

public class LargeMovieAdapter extends RecyclerView.Adapter<LargeMovieAdapter.ViewHolder> {

    private static final String IMG_BASE_URL = "https://image.tmdb.org/t/p/w600";

    private List<Movie> movies;
    private AdapterListener listener;

    public LargeMovieAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    public void setDataSource(List<Movie> movies) {
        this.movies = movies;
    }

    public void setListener(AdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CardView listItem = (CardView) LayoutInflater.from(
                parent.getContext()).inflate(R.layout.list_item_movie, parent, false);

        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CardView listItem = holder.listItem;

        Movie movie = movies.get(position);

        SimpleDraweeView imgPoster = listItem.findViewById(R.id.img_item_poster);
        TextView txtTitle = listItem.findViewById(R.id.txt_item_title);
        TextView txtDesc = listItem.findViewById(R.id.txt_item_desc);
        TextView txtRating = listItem.findViewById(R.id.txt_item_rating);
        TextView txtVotes = listItem.findViewById(R.id.txt_item_votes);
        TextView txtGenre = listItem.findViewById(R.id.txt_item_genre);

        txtTitle.setText((movie.getTitle() + " (" + movie.getReleaseDate().substring(0, 4) + ")"));
        txtDesc.setText(movie.getOverview());
        txtRating.setText(String.valueOf(movie.getVoteAverage()));
        txtVotes.setText(String.valueOf(movie.getVoteCount()));
        txtGenre.setText(GenreUtils.getGenreName(movie.getGenreIds().get(0)));
        imgPoster.setImageURI(Uri.parse(IMG_BASE_URL + movie.getPosterPath()));

        if (position == movies.size() - 1){

            listener.onBottomReached();

        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CardView listItem;

        public CardView getListItem() {
            return listItem;
        }

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