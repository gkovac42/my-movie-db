package com.example.goran.mymoviedb.movies.list;

import com.example.goran.mymoviedb.BaseView;
import com.example.goran.mymoviedb.data.model.list.Movie;

import java.util.List;

/**
 * Created by Goran on 11.1.2018..
 */

public interface MovieListContract {

    interface View extends BaseView {

        void navigateToMovie(int movieId);

        void updateAdapter(List<Movie> movies);
    }

    interface Presenter {

        void initPresenter(int category);

        void loadMovies();

        void onClickMovie(int movieId);

        void onBottomReached();

    }
}
