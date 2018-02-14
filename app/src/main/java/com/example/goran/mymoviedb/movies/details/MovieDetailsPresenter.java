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

    private MovieDetailsContract.View view;
    private DetailsInteractor interactor;

    private int movieId;
    private String title;
    private Long releaseDate;

    @Inject
    public MovieDetailsPresenter(MovieDetailsContract.View view, DetailsInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
        this.interactor.setListener(this);
    }

    @Override
    public void initPresenter(int movieId) {
        this.movieId = movieId;

        view.showProgressDialog();

        if (interactor.userNotNull()) {
            view.enableUserFeatures();
        }
    }

    @Override
    public void loadMovieData() {
        interactor.getMovieData(movieId);
    }

    @Override
    public void onDataReady(MovieData data) {

        title = data.getMovieDetails().getTitle();
        releaseDate = MovieUtils.dateStringToLong(data.getMovieDetails().getReleaseDate());

        if (data.getAccountStates().getRated().getClass() != Boolean.class) {
            view.checkRated();
        }

        if (data.getAccountStates().getFavorite()) {
            view.checkFavorite();
        }

        view.displayMovieDetails(data.getMovieDetails());
        view.displaySimilarMovies(data.getSimilarMovies());
        view.hideProgressDialog();
    }

    @Override
    public void onClickSimilar(int id) {
        view.navigateToSimilar(id);
    }

    @Override
    public void onClickRate() {

        if (!view.isRated()) {
            view.showRatingDialog();

        } else {
            interactor.deleteRating(movieId);
            view.uncheckRated();
        }
    }

    @Override
    public void onClickDlgRate(double rating) {
        interactor.setRating(movieId, rating);
        view.checkRated();
        view.dismissRatingDialog();
    }

    @Override
    public void onClickDlgClear() {
        view.dismissRatingDialog();
    }

    @Override
    public void onClickFavorite() {

        if (!view.isFavorite()) {
            interactor.setFavorite(true, movieId);
            view.checkFavorite();

            if (DateUtils.isToday(releaseDate)) {
                view.showNotification(title);

            } else if (releaseDate > Calendar.getInstance().getTimeInMillis()) {
                view.scheduleNotification(title, releaseDate);
            }

        } else {
            interactor.setFavorite(false, movieId);
            view.uncheckFavorite();
            view.cancelNotification(title);
        }
    }

    @Override
    public void onError() {
        view.hideProgressDialog();
    }

    @Override
    public void onUserActionError() {
        view.displayUserActionError();
    }
}
