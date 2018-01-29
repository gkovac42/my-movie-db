package com.example.goran.mymoviedb.data.interactors;

import android.arch.lifecycle.LifecycleOwner;
import android.util.Log;

import com.example.goran.mymoviedb.data.local.UserManager;
import com.example.goran.mymoviedb.data.model.FavoriteRequest;
import com.example.goran.mymoviedb.data.model.RateRequest;
import com.example.goran.mymoviedb.data.model.details.MovieDetails;
import com.example.goran.mymoviedb.data.model.list.ListResponse;
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

    public interface DetailsListener {

        void onDataReady(MovieDetails movieDetails);

        void onError();
    }

    public interface UserActionListener {

        void onSucess(String message);

        void onError();
    }

    @Override
    public void getMovieDetails(int movieId, DetailsListener listener) {

        Observable<MovieDetails> observable = apiHelper.getMovieDetails(movieId);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        movieDetails -> listener.onDataReady(movieDetails),
                        throwable -> listener.onError(),
                        () -> Log.i("LOG", "Complete"),
                        disposable -> getCompositeDisposable().add(disposable));
    }

    @Override
    public void getSimilarList(int movieId, ListInteractorImpl.ListListener listener) {

        Observable<ListResponse> listObservable = apiHelper.getSimilarMovies(movieId, 1);

        listObservable.map(listResponse -> listResponse.getMovies())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        movies -> listener.onDataReady(movies),
                        throwable -> listener.onError(),
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
        Log.i("RATING", String.valueOf(rateRequest.getValue()));

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
}
