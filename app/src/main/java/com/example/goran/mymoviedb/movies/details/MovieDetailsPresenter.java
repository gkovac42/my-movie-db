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

    private boolean rated;
    private boolean favorite;

    @Inject
    public MovieDetailsPresenter(MovieDetailsContract.View view, DetailsInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
        this.interactor.setListener(this);
    }

    @Override
    public void initPresenter(int movieId) {
        view.showProgressDialog();

        this.movieId = movieId;

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

        title = MovieUtils.formatTitle(
                data.getMovieDetails().getTitle(),
                data.getMovieDetails().getReleaseDate());

        data.getMovieDetails().setTitle(title);

        releaseDate = MovieUtils.dateStringToLong(
                data.getMovieDetails().getReleaseDate());

        if (data.getAccountStates() != null) {

            // API returns boolean false if not rated / Rated object if rated
            if (data.getAccountStates().getRated().getClass() != Boolean.class) {
                setRated(true);
            }

            if (data.getAccountStates().isFavorite()) {
                setFavorite(true);
            }
        }

        view.displayMovieDetails(data.getMovieDetails());
        view.displaySimilarMovies(data.getSimilarMovies());
        view.hideProgressDialog();
    }

    @Override
    public void onError() {
        // TODO - display error dialog
        view.hideProgressDialog();
    }

    @Override
    public void onClickSimilar(int id) {
        view.navigateToSimilar(id);
    }

    @Override
    public void onClickRate() {

        if (!rated) {
            view.showRatingDialog();

        } else {
            interactor.deleteRating(movieId);
        }
    }

    @Override
    public void onClickDlgRate(double rating) {
        interactor.postRating(movieId, rating);

    }

    @Override
    public void onRatingSuccess() {
        setRated(true);
        view.dismissRatingDialog();
    }

    @Override
    public void onDeleteRatingSuccess() {
        setRated(false);
    }


    @Override
    public void onClickDlgCancel() {
        view.dismissRatingDialog();
    }

    @Override
    public void onClickFavorite() {
        interactor.postFavorite(!favorite, movieId);
    }

    @Override
    public void onFavoriteSuccess(boolean favorite) {
        setFavorite(favorite);

        if (this.favorite) {

            if (DateUtils.isToday(releaseDate)) {
                view.showNotification(title);

            } else if (releaseDate > Calendar.getInstance().getTimeInMillis()) {
                view.scheduleNotification(title, releaseDate);
            }

        } else {
            view.cancelNotification(title);
        }
    }

    @Override
    public void onUserActionError() {
        view.displayUserActionError();
    }


    private void setFavorite(boolean favorite) {
        if (favorite) {
            this.favorite = true;
            view.checkFavorite();
        } else {
            this.favorite = false;
            view.uncheckFavorite();
        }
    }

    private void setRated(boolean rated) {
        if (rated) {
            this.rated = true;
            view.checkRated();
        } else {
            this.rated = false;
            view.uncheckRated();
        }
    }
}
