package com.example.goran.mymoviedb.movies.details;

import com.example.goran.mymoviedb.BaseView;
import com.example.goran.mymoviedb.data.model.details.MovieDetails;
import com.example.goran.mymoviedb.data.model.list.Movie;

import java.util.List;

/**
 * Created by Goran on 12.1.2018..
 */

public interface MovieDetailsContract {

    interface View extends BaseView {

        void enableUserFeatures();

        void checkFavorite();

        void checkRated();

        void uncheckFavorite();

        void uncheckRated();

        void showRatingDialog();

        void dismissRatingDialog();

        void displayMovieDetails(MovieDetails movieDetails);

        void displaySimilarMovies(List<Movie> movieList);

        void navigateToSimilar(int id);

        void scheduleNotification(String title, Long releaseDate);

        void cancelNotification(String title);

        void showNotification(String message);

    }

    interface Presenter {

        void initPresenter(int movieId);

        void loadMovieData();

        void onClickSimilar(int movieId);

        void onClickRate();

        void onClickDlgRate(double rating);

        void onClickDlgClear();

        void onClickFavorite();

    }
}
