package com.example.goran.mymoviedb.movies.details;

import com.example.goran.mymoviedb.data.ListInteractor;
import com.example.goran.mymoviedb.data.model.Movie;
import com.example.goran.mymoviedb.data.model.singlemovie.MovieDetails;
import com.example.goran.mymoviedb.data.remote.DetailsInteractor;

import java.util.List;

/**
 * Created by Goran on 12.1.2018..
 */

public interface MovieDetailsContract {

    interface View {

        void displayMovieDetails(MovieDetails movieDetails);

        void displaySimilarMovies(List<Movie> movieList);

        void navigateToSimilar(int id);

    }

    interface Presenter {

        void setMovieId(int id);

        void getMovieDetails();

        void getSimilarMovies();

        void onClickSimilar(int id);
    }

    interface Model {

        void getMovieDetails(int movieId, DetailsInteractor.DetailsListener listener);

        void getSimilarList(int movieId, ListInteractor.ListListener listener);

    }

}
