package com.example.goran.mymoviedb.data.interactors;

import android.util.Log;

import com.example.goran.mymoviedb.data.model.ListResponse;
import com.example.goran.mymoviedb.data.model.Movie;
import com.example.goran.mymoviedb.data.model.keywords.Keyword;
import com.example.goran.mymoviedb.data.model.keywords.KeywordResponse;
import com.example.goran.mymoviedb.data.remote.ApiHelper;
import com.example.goran.mymoviedb.di.scope.FragmentScope;
import com.example.goran.mymoviedb.movies.search.MovieSearchContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Goran on 17.1.2018..
 */

@FragmentScope
public class SearchInteractor implements MovieSearchContract.Model {

    private ApiHelper apiHelper;
    private CompositeDisposable compositeDisposable;

    @Inject
    public SearchInteractor(ApiHelper apiHelper) {
        this.apiHelper = apiHelper;
        compositeDisposable = new CompositeDisposable();
    }

    public interface SearchListener {

        void onResultsReady(List<Movie> movieList);

        void onError();
    }

    public interface KeywordListener {

        void onKeywordsReady(List<Keyword> keywordList);

        void onError();

    }

    @Override
    public void searchByTitle(String query, SearchListener listener) {
        Observable<ListResponse> observable = apiHelper.searchByTitle(query, 1);
        observable.map(listResponse -> listResponse.getMovies())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        movies -> listener.onResultsReady(movies),
                        throwable -> listener.onError(), () -> Log.i("LOG", "Complete"),
                        disposable -> compositeDisposable.add(disposable));
    }

    @Override
    public void getKeywords(String query, KeywordListener listener) {
        Observable<KeywordResponse> observable = apiHelper.getKeywords(query);
        observable.map(keywordResponse -> keywordResponse.getKeywords())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        keywords -> listener.onKeywordsReady(keywords),
                        throwable -> listener.onError(), () -> Log.i("LOG", "Complete"),
                        disposable -> compositeDisposable.add(disposable));
    }

    @Override
    public void searchByKeyword(String query, SearchListener listener) {
        Observable<ListResponse> observable = apiHelper.searchByKeyword(query, 1);
        observable.map(listResponse -> listResponse.getMovies())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        movies -> listener.onResultsReady(movies),
                        throwable -> listener.onError(), () -> Log.i("LOG", "Complete"),
                        disposable -> compositeDisposable.add(disposable));
    }

    @Override
    public void dispose() {
        compositeDisposable.dispose();
    }
}
