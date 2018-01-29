package com.example.goran.mymoviedb.data.interactors;

import android.arch.lifecycle.LifecycleOwner;
import android.util.Log;

import com.example.goran.mymoviedb.data.model.keywords.Keyword;
import com.example.goran.mymoviedb.data.model.keywords.KeywordResponse;
import com.example.goran.mymoviedb.data.model.list.ListResponse;
import com.example.goran.mymoviedb.data.model.list.Movie;
import com.example.goran.mymoviedb.data.remote.ApiHelper;
import com.example.goran.mymoviedb.di.scope.PerFragment;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Goran on 17.1.2018..
 */

@PerFragment
public class SearchInteractorImpl extends BaseInteractorImpl implements SearchInteractor {

    private ApiHelper apiHelper;

    @Inject
    public SearchInteractorImpl(ApiHelper apiHelper, LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
        this.apiHelper = apiHelper;
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
    public void searchByTitle(String query, int page, SearchListener listener) {
        Observable<ListResponse> observable = apiHelper.searchByTitle(query, page);
        observable.map(listResponse -> listResponse.getMovies())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        movies -> listener.onResultsReady(movies),
                        throwable -> listener.onError(), () -> Log.i("LOG", "Complete"),
                        disposable -> getCompositeDisposable().add(disposable));
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
                        disposable -> getCompositeDisposable().add(disposable));
    }

    @Override
    public void searchByKeywordId(int keywordId, int page, SearchListener listener) {
        Observable<ListResponse> observable = apiHelper.searchByKeywordId(keywordId, page);
        observable.map(listResponse -> listResponse.getMovies())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        movies -> listener.onResultsReady(movies),
                        throwable -> listener.onError(), () -> Log.i("LOG", "Complete"),
                        disposable -> getCompositeDisposable().add(disposable));
    }

    @Override
    public void dispose() {
        getCompositeDisposable().dispose();
    }
}
