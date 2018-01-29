package com.example.goran.mymoviedb.movies.details;

import com.example.goran.mymoviedb.data.model.details.MovieDetails;
import com.example.goran.mymoviedb.data.model.list.Movie;

import java.util.List;

/**
 * Created by Goran on 12.1.2018..
 */

public interface MovieDetailsContract {

    interface View {

        void showProgressDialog();

        void hideProgressDialog();

        void enableUserFeatures();

        boolean isFavorite();

        boolean isRated();

        void checkFavorite();

        void checkRated();

        void uncheckFavorite();

        void uncheckRated();

        void showRatingDialog();

        void dismissRatingDialog();

        void displayMovieDetails(MovieDetails movieDetails);

        void displaySimilarMovies(List<Movie> movieList);

        void navigateToSimilar(int id);

        void showNotification(String message);

    }

    interface Presenter {

        void initPresenter(int movieId);

        void getMovieDetails();

        void getSimilarMovies();

        void onClickSimilar(int movieId);

        void onClickRate();

        void onClickDlgRate(double rating);

        void onClickDlgClear();

        void onClickFavorite();

    }
}
