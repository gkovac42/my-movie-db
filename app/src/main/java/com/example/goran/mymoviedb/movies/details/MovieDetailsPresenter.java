package com.example.goran.mymoviedb.movies.details;

import com.example.goran.mymoviedb.data.MoviesInteractor;
import com.example.goran.mymoviedb.data.model.Movie;
import com.example.goran.mymoviedb.data.model.singlemovie.MovieDetails;
import com.example.goran.mymoviedb.di.scope.FragmentScope;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Goran on 12.1.2018..
 */

@FragmentScope
public class MovieDetailsPresenter implements MovieDetailsContract.Presenter {

    private MovieDetailsContract.View detailsView;
    private MovieDetailsContract.Model detailsInteractor;

    private int movieId;

    @Inject
    public MovieDetailsPresenter(MovieDetailsContract.View detailsView, MovieDetailsContract.Model detailsInteractor) {
        this.detailsView = detailsView;
        this.detailsInteractor = detailsInteractor;
    }

    @Override
    public void setMovieId(int id) {
        movieId = id;
    }

    @Override

    public void getMovieDetails() {
        detailsInteractor.getMovieDetails(movieId, new MoviesInteractor.DetailsListener() {
            @Override
            public void onDataReady(MovieDetails movieDetails) {
                detailsView.displayMovieDetails(movieDetails);
            }

            @Override
            public void onError() {

            }
        });

    }

    @Override
    public void getSimilarMovies() {
        detailsInteractor.getSimilarList(movieId, new MoviesInteractor.ListListener() {
            @Override
            public void onDataReady(List<Movie> movieList) {
                detailsView.displaySimilarMovies(movieList);
            }

            @Override
            public void onError() {

            }
        });
    }

}
