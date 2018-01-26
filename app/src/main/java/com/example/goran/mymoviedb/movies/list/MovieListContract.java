package com.example.goran.mymoviedb.movies.list;

import com.example.goran.mymoviedb.data.model.list.Movie;

import java.util.List;

/**
 * Created by Goran on 11.1.2018..
 */

public interface MovieListContract {

    interface View {

        void navigateToMovie(int movieId);

        void setLinearLargeLayout();

        void setLinearSimpleLayout();

        void updateAdapter(List<Movie> movies);
    }

    interface Presenter {

        void initPresenter(int category);

        void initView(int style);

        void loadMovies();

        void onClickMovie(int movieId);

        void onBottomReached();

    }
}
