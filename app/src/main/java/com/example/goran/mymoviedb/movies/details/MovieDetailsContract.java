package com.example.goran.mymoviedb.movies.details;

import com.example.goran.mymoviedb.data.model.Movie;
import com.example.goran.mymoviedb.data.model.singlemovie.MovieDetails;

import java.util.List;

/**
 * Created by Goran on 12.1.2018..
 */

public interface MovieDetailsContract {

    interface View {

        void displayMovieDetails(MovieDetails movieDetails);

        void displaySimilarMovies(List<Movie> movieList);

    }

    interface Presenter {

        void getMovieId();

        void getMovieDetails();

        void getSimilarMovies();
    }

}
