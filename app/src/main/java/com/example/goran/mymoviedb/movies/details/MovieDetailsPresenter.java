package com.example.goran.mymoviedb.movies.details;

import com.example.goran.mymoviedb.data.interactors.DetailsInteractor;
import com.example.goran.mymoviedb.data.interactors.DetailsInteractorImpl;
import com.example.goran.mymoviedb.data.interactors.ListInteractorImpl;
import com.example.goran.mymoviedb.data.local.UserManager;
import com.example.goran.mymoviedb.data.model.details.MovieDetails;
import com.example.goran.mymoviedb.data.model.list.Movie;
import com.example.goran.mymoviedb.di.scope.PerFragment;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Goran on 12.1.2018..
 */

@PerFragment
public class MovieDetailsPresenter implements MovieDetailsContract.Presenter {

    private MovieDetailsContract.View detailsView;
    private DetailsInteractor detailsInteractor;

    private int movieId;

    @Inject
    public MovieDetailsPresenter(MovieDetailsContract.View detailsView, DetailsInteractor detailsInteractor) {
        this.detailsView = detailsView;
        this.detailsInteractor = detailsInteractor;
    }

    @Override
    public void initPresenter(int movieId) {
        this.movieId = movieId;

        if (UserManager.getActiveUser() != null) {
            detailsView.enableUserFeatures();

            if (checkIfExists(detailsInteractor.getFavorites())) {
                detailsView.checkFavorite();
            }

            if (checkIfExists(detailsInteractor.getRated())) {
                detailsView.checkRated();
            }
        }
    }

    @Override

    public void getMovieDetails() {
        detailsInteractor.getMovieDetails(movieId, new DetailsInteractorImpl.DetailsListener() {
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
        detailsInteractor.getSimilarList(movieId, new ListInteractorImpl.ListListener() {
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

        if (!detailsView.isRated()) {
            detailsView.showRatingDialog();

        } else {
            detailsInteractor.deleteRating(movieId);
            detailsView.uncheckRated();
        }
    }

    @Override
    public void onClickDlgRate(double rating) {
        detailsInteractor.setRating(movieId, rating);
        detailsView.checkRated();
        detailsView.dismissRatingDialog();
    }

    @Override
    public void onClickDlgClear() {
        detailsView.dismissRatingDialog();
    }

    @Override
    public void onClickFavorite() {

        if (!detailsView.isFavorite()) {
            detailsInteractor.setFavorite(true, movieId);
            detailsView.checkFavorite();

        } else {
            detailsInteractor.setFavorite(false, movieId);
            detailsView.uncheckFavorite();
        }


    }

    private boolean checkIfExists(List<Integer> idList) {

        for (int id : idList) {
            if (id == movieId) {
                return true;
            }
        }
        return false;
    }
}
