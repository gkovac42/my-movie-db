package com.example.goran.mymoviedb.data;

import android.util.Log;

import com.example.goran.mymoviedb.data.model.ListResponse;
import com.example.goran.mymoviedb.data.model.Movie;
import com.example.goran.mymoviedb.data.model.singlemovie.MovieDetails;
import com.example.goran.mymoviedb.data.remote.ApiHelper;
import com.example.goran.mymoviedb.di.scope.FragmentScope;
import com.example.goran.mymoviedb.movies.details.MovieDetailsContract;
import com.example.goran.mymoviedb.movies.list.MovieListContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Goran on 11.1.2018..
 */

@FragmentScope
public class MoviesInteractor implements MovieListContract.Model, MovieDetailsContract.Model {

    private ApiHelper apiHelper;
    private CompositeDisposable compositeDisposable;

    @Inject
    public MoviesInteractor(ApiHelper apiHelper) {
        this.apiHelper = apiHelper;
        compositeDisposable = new CompositeDisposable();
    }

    public interface ListListener {

        void onDataReady(List<Movie> movieList);

        void onError();
    }

    public interface DetailsListener {

        void onDataReady(MovieDetails movieDetails);

        void onError();
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

    @Override
    public void getMovieList(Observable<ListResponse> listObservable, ListListener listener) {

        listObservable.map(listResponse -> listResponse.getMovies())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        movies -> listener.onDataReady(movies),
                        throwable -> listener.onError(),
                        () -> Log.i("LOG", "Complete"),
                        disposable -> compositeDisposable.add(disposable));
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
                        disposable -> compositeDisposable.add(disposable));
    }

    @Override
    public void getSimilarList(int movieId, ListListener listener) {

        Observable<ListResponse> listObservable = apiHelper.getSimilarMovies(movieId, 1);

        listObservable.map(listResponse -> listResponse.getMovies())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        movies -> listener.onDataReady(movies),
                        throwable -> listener.onError(),
                        () -> Log.i("LOG", "Complete"),
                        disposable -> compositeDisposable.add(disposable));

    }
}
