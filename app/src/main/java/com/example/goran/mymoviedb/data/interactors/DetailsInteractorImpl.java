package com.example.goran.mymoviedb.data.interactors;

import android.arch.lifecycle.LifecycleOwner;

import com.example.goran.mymoviedb.data.local.UserManager;
import com.example.goran.mymoviedb.data.model.FavoriteRequest;
import com.example.goran.mymoviedb.data.model.RateRequest;
import com.example.goran.mymoviedb.data.model.auth.AccountStates;
import com.example.goran.mymoviedb.data.model.details.MovieDetails;
import com.example.goran.mymoviedb.data.model.list.ListResponse;
import com.example.goran.mymoviedb.data.model.list.MovieData;
import com.example.goran.mymoviedb.data.remote.ApiHelper;
import com.example.goran.mymoviedb.di.scope.PerFragment;

import javax.inject.Inject;

import io.reactivex.Observable;
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

        void onDataReady(MovieData movieData);

        void onError();

        void onUserActionError();
    }


    @Override
    public void getMovieData(int movieId) {

        Observable<MovieDetails> detailsObs = apiHelper.getMovieDetails(movieId);
        Observable<AccountStates> statesObs = apiHelper.getAccountStates(movieId);
        Observable<ListResponse> similarObs = apiHelper.getSimilarMovies(movieId, 1);

        Observable.zip(detailsObs, statesObs, similarObs, (movieDetails, accountStates, listResponse) -> {

            MovieData movieData = new MovieData();
            movieData.setMovieDetails(movieDetails);
            movieData.setAccountStates(accountStates);
            movieData.setSimilarMovies(listResponse.getMovies());

            return movieData;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        movieData -> ((DetailsListener) getListener()).onDataReady(movieData),
                        throwable -> ((DetailsListener) getListener()).onError(),
                        () -> {},
                        disposable -> getCompositeDisposable().add(disposable));
    }

    @Override
    public void setFavorite(boolean favorite, int movieId) {

        FavoriteRequest favoriteRequest = new FavoriteRequest(movieId, favorite);

        apiHelper.postFavoriteMovie(favoriteRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        favoriteResponse -> {},
                        throwable -> ((DetailsListener) getListener()).onUserActionError(),
                        () -> {},
                        disposable -> getCompositeDisposable().add(disposable));
    }

    @Override
    public void setRating(int movieId, double rating) {

        RateRequest rateRequest = new RateRequest(rating * 2);

        apiHelper.postMovieRating(movieId, rateRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        rateResponse -> {},
                        throwable -> ((DetailsListener) getListener()).onUserActionError(),
                        () -> {},
                        disposable -> getCompositeDisposable().add(disposable));
    }

    @Override
    public void deleteRating(int movieId) {

        apiHelper.deleteMovieRating(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        rateResponse -> {},
                        throwable -> ((DetailsListener) getListener()).onUserActionError(),
                        () -> {},
                        disposable -> getCompositeDisposable().add(disposable));
    }

    @Override
    public boolean userNotNull() {
        return UserManager.getActiveUser() != null;
    }

}
