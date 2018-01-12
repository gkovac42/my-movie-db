package com.example.goran.mymoviedb.data;

import android.util.Log;

import com.example.goran.mymoviedb.data.model.ListResponse;
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


    @Override
    public void getMovieList(int page, Interactor.DataListener listener) {

        Observable<ListResponse> observable = apiHelper.getNowPlayingMovies(page);
        observable.map(listResponse -> listResponse.getMovies())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(

                        movies -> listener.onDataReady(movies),

                        throwable -> listener.onError(),

                        () -> Log.i("LOG", "List downloaded"),

                        disposable -> compositeDisposable.add(disposable));
    }
}
