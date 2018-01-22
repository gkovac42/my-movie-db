package com.example.goran.mymoviedb.data.interactors;

import android.util.Log;

import com.example.goran.mymoviedb.data.model.list.ListResponse;
import com.example.goran.mymoviedb.data.model.list.Movie;
import com.example.goran.mymoviedb.data.remote.ApiHelper;
import com.example.goran.mymoviedb.di.scope.FragmentScope;
import com.example.goran.mymoviedb.movies.list.MovieListContract;
import com.example.goran.mymoviedb.movies.util.Category;

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
public class ListInteractor implements MovieListContract.Model {

    private ApiHelper apiHelper;
    private CompositeDisposable compositeDisposable;

    @Inject
    public ListInteractor(ApiHelper apiHelper) {
        this.apiHelper = apiHelper;
        compositeDisposable = new CompositeDisposable();
    }

    public interface ListListener {

        void onDataReady(List<Movie> movieList);

        void onError();
    }

    private Observable<ListResponse> getNowPlaying(int page) {
        return apiHelper.getNowPlayingMovies(page);
    }

    private Observable<ListResponse> getUpcoming(int page) {
        return apiHelper.getUpcomingMovies(page);
    }

    private Observable<ListResponse> getPopular(int page) {
        return apiHelper.getPopularMovies(page);
    }

    private Observable<ListResponse> getTopRated(int page) {
        return apiHelper.getTopRatedMovies(page);
    }

    @Override
    public void getMovieList(int category, int page, ListListener listener) {

        Observable<ListResponse> listObservable;

        switch (category) {
            case Category.NOW_PLAYING:
                listObservable = getNowPlaying(page);
                break;
            case Category.UPCOMING:
                listObservable = getUpcoming(page);
                break;
            case Category.POPULAR:
                listObservable = getPopular(page);
                break;
            case Category.TOP_RATED:
                listObservable = getTopRated(page);
                break;
            default:
                listObservable = getNowPlaying(page);
        }

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
    public void dispose() {
        compositeDisposable.dispose();
    }

}
