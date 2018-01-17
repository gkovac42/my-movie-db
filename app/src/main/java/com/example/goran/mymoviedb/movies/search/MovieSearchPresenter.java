package com.example.goran.mymoviedb.movies.search;

import android.util.Log;

import com.example.goran.mymoviedb.data.model.Movie;
import com.example.goran.mymoviedb.data.model.keywords.Keyword;
import com.example.goran.mymoviedb.data.SearchInteractor;
import com.example.goran.mymoviedb.di.scope.FragmentScope;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Goran on 17.1.2018..
 */

@FragmentScope
public class MovieSearchPresenter implements
        MovieSearchContract.Presenter, SearchInteractor.SearchListener, SearchInteractor.KeywordListener {

    private MovieSearchContract.Model searchInteractor;
    private MovieSearchContract.View searchView;

    @Inject
    public MovieSearchPresenter(MovieSearchContract.Model searchInteractor, MovieSearchContract.View searchView) {
        this.searchInteractor = searchInteractor;
        this.searchView = searchView;
    }

    @Override
    public void onClickSearch(String query, Boolean searchByTitle) {

        searchView.showProgressBar();

        if (searchByTitle) {
            searchInteractor.searchByTitle(query, this);
        } else {
            int keywordId = 0;
            for (int i = 0; i < searchView.getKeywordAdapter().getCount(); i++) {
                if (searchView.getKeywordAdapter().getItem(i).getName().equals(query)) {
                    keywordId = searchView.getKeywordAdapter().getItem(i).getId();
                }
            }
            searchInteractor.searchByKeyword(String.valueOf(keywordId), this);
        }
    }

    @Override
    public void onClickKeyword() {
        searchView.initTextWatcher();
    }

    @Override
    public void onClickTitle() {
        searchView.removeTextWatcher();
    }

    @Override
    public void loadKeywords(String query) {
        searchInteractor.getKeywords(query, this);
    }

    @Override
    public void onClickResult(int position) {
        searchView.navigateToResult(position);
    }

    @Override
    public void onResultsReady(List<Movie> movieList) {
        Log.i("RESULTS", movieList.get(0).getTitle());
        Log.i("RESULTS", "Hey");
        searchView.displaySearchResults(movieList);
        searchView.hideProgressBar();
    }

    @Override
    public void onKeywordsReady(List<Keyword> keywordList) {
        searchView.displayKeywords(keywordList);
    }

    @Override
    public void onError() {
        searchView.hideProgressBar();
    }

    @Override
    public void onDestroy() {
        searchInteractor.dispose();
    }
}
