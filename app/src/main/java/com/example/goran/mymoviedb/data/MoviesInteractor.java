package com.example.goran.mymoviedb.data;

import android.util.Log;

import com.example.goran.mymoviedb.data.model.ListResponse;
import com.example.goran.mymoviedb.data.model.singlemovie.MovieDetails;
import com.example.goran.mymoviedb.data.remote.ApiHelper;
import com.example.goran.mymoviedb.di.scope.FragmentScope;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Goran on 11.1.2018..
 */

@FragmentScope
public class MoviesInteractor implements Interactor.Movies {

    private ApiHelper apiHelper;
    private CompositeDisposable compositeDisposable;

    @Inject
    public MoviesInteractor(ApiHelper apiHelper) {
        this.apiHelper = apiHelper;
        compositeDisposable = new CompositeDisposable();
    }

    public Observable<ListResponse> getNowPlaying(int page) {
        return apiHelper.getNowPlayingMovies(page);
    }

    @Override
    public Observable<ListResponse> getUpcoming(int page) {
        return apiHelper.getUpcomingMovies(page);
    }

    @Override
    public Observable<ListResponse> getPopular(int page) {
        return apiHelper.getPopularMovies(page);
    }

    @Override
    public Observable<ListResponse> getTopRated(int page) {
        return apiHelper.getTopRatedMovies(page);
    }

    public Observable<ListResponse> getSimilar(int id, int page) {
        return apiHelper.getSimilarMovies(id, page);
    }

    @Override
    public void getMovieList(Observable<ListResponse> listObservable, Interactor.ListListener listener) {

        listObservable.map(listResponse -> listResponse.getMovies())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        movies -> listener.onDataReady(movies),
                        throwable -> listener.onError(),
                        () -> Log.i("LOG", "Complete"),
                        disposable -> compositeDisposable.add(disposable));
    }

    public void getMovieDetails(int movieId, Interactor.DetailsListener listener) {

        Observable<MovieDetails> observable = apiHelper.getMovieDetails(movieId);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        movieDetails -> listener.onDataReady(movieDetails),
                        throwable -> listener.onError(),
                        () -> Log.i("LOG", "Complete"),
                        disposable -> compositeDisposable.add(disposable));
    }


}
