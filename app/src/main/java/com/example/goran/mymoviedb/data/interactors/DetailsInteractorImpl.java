package com.example.goran.mymoviedb.data.interactors;

import android.arch.lifecycle.LifecycleOwner;
import android.util.Log;

import com.example.goran.mymoviedb.data.local.UserManager;
import com.example.goran.mymoviedb.data.model.FavoriteRequest;
import com.example.goran.mymoviedb.data.model.RateRequest;
import com.example.goran.mymoviedb.data.model.details.MovieDetails;
import com.example.goran.mymoviedb.data.model.list.Movie;
import com.example.goran.mymoviedb.data.remote.ApiHelper;
import com.example.goran.mymoviedb.di.scope.PerFragment;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Goran on 17.1.2018..
 */

@PerFragment
public class DetailsInteractorImpl extends BaseInteractorImpl implements DetailsInteractor {

    private ApiHelper apiHelper;

    @Inject
    public DetailsInteractorImpl(ApiHelper apiHelper, LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
        this.apiHelper = apiHelper;
    }

    public interface DetailsListener extends BaseListener {

        void onDetailsReady(MovieDetails movieDetails);

        void onSimilarReady(List<Movie> movies);

        void onError();
    }


    @Override
    public void getMovieDetails(int movieId) {

        apiHelper.getMovieDetails(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        movieDetails -> ((DetailsListener) getListener()).onDetailsReady(movieDetails),
                        throwable -> ((DetailsListener) getListener()).onError(),
                        () -> Log.i("LOG", "Complete"),
                        disposable -> getCompositeDisposable().add(disposable));
    }

    @Override
    public void getSimilarList(int movieId) {

        apiHelper.getSimilarMovies(movieId, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        listResponse -> ((DetailsListener) getListener()).onSimilarReady(listResponse.getMovies()),
                        throwable -> ((DetailsListener) getListener()).onError(),
                        () -> Log.i("LOG", "Complete"),
                        disposable -> getCompositeDisposable().add(disposable));
    }

    @Override
    public void setFavorite(boolean favorite, int movieId) {

        FavoriteRequest favoriteRequest = new FavoriteRequest(movieId, favorite);

        apiHelper.postFavoriteMovie(favoriteRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(favoriteResponse -> {

                            if (favorite) {
                                UserManager.getActiveUser().addToFavorite(movieId);
                            } else {
                                UserManager.getActiveUser().removeFromFavorite(movieId);
                            }
                        },
                        throwable -> Log.i("LOG", "Error"),
                        () -> Log.i("LOG", "Complete"),
                        disposable -> getCompositeDisposable().add(disposable));
    }

    @Override
    public void setRating(int movieId, double rating) {

        RateRequest rateRequest = new RateRequest(rating * 2);

        apiHelper.postMovieRating(movieId, rateRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(rateResponse ->
                                UserManager.getActiveUser().addToRated(movieId),
                        throwable -> Log.i("LOG", "Error"),
                        () -> Log.i("LOG", "Complete"),
                        disposable -> getCompositeDisposable().add(disposable));
    }

    @Override
    public void deleteRating(int movieId) {

        apiHelper.deleteMovieRating(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(rateResponse ->
                                UserManager.getActiveUser().removeFromRated(movieId),
                        throwable -> Log.i("LOG", "Error"),
                        () -> Log.i("LOG", "Complete"),
                        disposable -> getCompositeDisposable().add(disposable));
    }

    @Override
    public boolean userNotNull() {
        return UserManager.getActiveUser() != null;
    }

    @Override
    public List<Integer> getUserFavoriteIds() {
        return UserManager.getActiveUser().getFavoriteMovies();
    }

    @Override
    public List<Integer> getUserRatedIds() {
        return UserManager.getActiveUser().getRatedMovies();
    }
}
