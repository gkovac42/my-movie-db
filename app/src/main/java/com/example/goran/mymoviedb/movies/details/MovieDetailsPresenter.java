package com.example.goran.mymoviedb.movies.details;

import com.example.goran.mymoviedb.data.interactors.ListInteractor;
import com.example.goran.mymoviedb.data.model.list.Movie;
import com.example.goran.mymoviedb.data.model.singlemovie.MovieDetails;
import com.example.goran.mymoviedb.data.interactors.DetailsInteractor;
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
        detailsInteractor.getMovieDetails(movieId, new DetailsInteractor.DetailsListener() {
            @Override
            public void onDataReady(MovieDetails movieDetails) {
                detailsView.displayMovieDetails(movieDetails);
                detailsView.hideProgressBar();
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void getSimilarMovies() {
        detailsInteractor.getSimilarList(movieId, new ListInteractor.ListListener() {
            @Override
            public void onDataReady(List<Movie> movieList) {
                detailsView.displaySimilarMovies(movieList);
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void onClickSimilar(int id) {
        detailsView.navigateToSimilar(id);
    }

    @Override
    public void onClickRate() {
        // TODO - get sessionId, POST rating
    }

    @Override
    public void onClickFavorite() {
        // TODO - POST favorite

    }
}
