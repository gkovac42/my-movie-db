package com.example.goran.mymoviedb.movies.details;

import android.text.format.DateUtils;

import com.example.goran.mymoviedb.data.interactors.DetailsInteractor;
import com.example.goran.mymoviedb.data.interactors.DetailsInteractorImpl;
import com.example.goran.mymoviedb.data.model.details.MovieDetails;
import com.example.goran.mymoviedb.data.model.list.Movie;
import com.example.goran.mymoviedb.di.scope.PerFragment;
import com.example.goran.mymoviedb.movies.util.MovieUtils;

import java.util.Calendar;
import java.util.List;

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

            List<Integer> favoriteMovies = detailsInteractor.getUserFavoriteIds();

            if (checkIfExists(favoriteMovies)) {
                detailsView.checkFavorite();
            }

            List<Integer> ratedMovies = detailsInteractor.getUserRatedIds();

            if (checkIfExists(ratedMovies)) {
                detailsView.checkRated();
            }
        }
    }

    @Override
    public void getMovieDetails() {
        detailsInteractor.getMovieDetails(movieId);
    }

    @Override
    public void onDetailsReady(MovieDetails movieDetails) {
        movieTitle = movieDetails.getTitle();
        movieReleaseDate = MovieUtils.dateStringToLong(movieDetails.getReleaseDate());

        detailsView.displayMovieDetails(movieDetails);

        detailsView.hideProgressDialog();
    }

    @Override
    public void getSimilarMovies() {
        detailsInteractor.getSimilarList(movieId);
    }

    @Override
    public void onSimilarReady(List<Movie> movies) {
        detailsView.displaySimilarMovies(movies);
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

    private boolean checkIfExists(List<Integer> movieIdList) {
        for (Integer i : movieIdList) {
            if (i == movieId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onError() {
        detailsView.hideProgressDialog();
    }
}
