package com.example.goran.mymoviedb.movies.details;

import android.text.format.DateUtils;

import com.example.goran.mymoviedb.data.interactors.DetailsInteractor;
import com.example.goran.mymoviedb.data.interactors.DetailsInteractorImpl;
import com.example.goran.mymoviedb.data.model.list.MovieData;
import com.example.goran.mymoviedb.di.scope.PerFragment;
import com.example.goran.mymoviedb.movies.util.MovieUtils;

import java.util.Calendar;

import javax.inject.Inject;

/**
 * Created by Goran on 12.1.2018..
 */

@PerFragment
public class MovieDetailsPresenter implements MovieDetailsContract.Presenter, DetailsInteractorImpl.DetailsListener {

    private MovieDetailsContract.View detailsView;
    private DetailsInteractor detailsInteractor;

    private int movieId;
    private String movieTitle;
    private Long movieReleaseDate;

    @Inject
    public MovieDetailsPresenter(MovieDetailsContract.View detailsView, DetailsInteractor detailsInteractor) {
        this.detailsView = detailsView;
        this.detailsInteractor = detailsInteractor;

        detailsInteractor.setListener(this);
    }

    @Override
    public void initPresenter(int movieId) {
        this.movieId = movieId;

        detailsView.showProgressDialog();

        if (detailsInteractor.userNotNull()) {
            detailsView.enableUserFeatures();
        }
    }

    @Override
    public void loadMovieData() {
        detailsInteractor.getMovieData(movieId);
    }

    @Override
    public void onDataReady(MovieData movieData) {
        movieTitle = movieData.getMovieDetails().getTitle();
        movieReleaseDate = MovieUtils.dateStringToLong(movieData.getMovieDetails().getReleaseDate());

        if (movieData.getAccountStates().getRated().getClass() != Boolean.class) {
            detailsView.checkRated();
        }

        if (movieData.getAccountStates().getFavorite()) {
            detailsView.checkFavorite();
        }

        detailsView.displayMovieDetails(movieData.getMovieDetails());
        detailsView.displaySimilarMovies(movieData.getSimilarMovies());
        detailsView.hideProgressDialog();
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

            if (DateUtils.isToday(movieReleaseDate)) {
                detailsView.showNotification(movieTitle);

            } else if (movieReleaseDate > Calendar.getInstance().getTimeInMillis()) {
                detailsView.scheduleNotification(movieTitle, movieReleaseDate);
            }

        } else {
            detailsInteractor.setFavorite(false, movieId);
            detailsView.uncheckFavorite();
            detailsView.cancelNotification(movieTitle);
        }
    }

    @Override
    public void onError() {
        detailsView.hideProgressDialog();
    }

    @Override
    public void onUserActionError() {
        detailsView.displayUserActionError();
    }
}
