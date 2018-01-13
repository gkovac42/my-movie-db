package com.example.goran.mymoviedb.movies.list;

import com.example.goran.mymoviedb.data.model.Movie;

import java.util.List;

/**
 * Created by Goran on 11.1.2018..
 */

public interface MovieListContract {

    interface View {

        int getCategory();

        void navigateToMovie(int movieId);

        void addMoviesToAdapter(List<Movie> movies);
    }

    interface Presenter {

        void loadMovies();

        void onClickMovie(int position);

        void onBottomReached();
    }
}
