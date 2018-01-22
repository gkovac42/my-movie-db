package com.example.goran.mymoviedb.movies.details;

import com.example.goran.mymoviedb.data.interactors.ListInteractor;
import com.example.goran.mymoviedb.data.model.list.Movie;
import com.example.goran.mymoviedb.data.model.singlemovie.MovieDetails;
import com.example.goran.mymoviedb.data.interactors.DetailsInteractor;

import java.util.List;

/**
 * Created by Goran on 12.1.2018..
 */

public interface MovieDetailsContract {

    interface View {

        void hideProgressBar();

        void enableUserFeatures();

        void checkFavorite();

        void checkRated();

        void showRatingDialog();

        void displayMovieDetails(MovieDetails movieDetails);

        void displaySimilarMovies(List<Movie> movieList);

        void navigateToSimilar(int id);

    }

    interface Presenter {

        void initPresenter(int movieId);

        void getMovieDetails();

        void getSimilarMovies();

        void onClickSimilar(int movieId);

        void onClickRate();

        void onClickFavorite();
    }

    interface Model {

        void getMovieDetails(int movieId, DetailsInteractor.DetailsListener listener);

        void getSimilarList(int movieId, ListInteractor.ListListener listener);

    }

}
