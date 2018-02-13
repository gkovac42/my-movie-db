package com.example.goran.mymoviedb.data.model.list;

import com.example.goran.mymoviedb.data.model.auth.AccountStates;
import com.example.goran.mymoviedb.data.model.details.MovieDetails;

import java.util.List;

/**
 * Created by Goran on 10.2.2018..
 */

public class MovieData {

    private MovieDetails movieDetails;
    private List<Movie> similarMovies;
    private AccountStates accountStates;

    public MovieDetails getMovieDetails() {
        return movieDetails;
    }

    public AccountStates getAccountStates() {
        return accountStates;
    }

    public void setAccountStates(AccountStates accountStates) {
        this.accountStates = accountStates;
    }

    public void setMovieDetails(MovieDetails movieDetails) {
        this.movieDetails = movieDetails;
    }

    public List<Movie> getSimilarMovies() {
        return similarMovies;
    }

    public void setSimilarMovies(List<Movie> similarMovies) {
        this.similarMovies = similarMovies;
    }
}
