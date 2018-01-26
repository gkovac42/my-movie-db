package com.example.goran.mymoviedb.movies.search;

import com.example.goran.mymoviedb.data.interactors.SearchInteractor;
import com.example.goran.mymoviedb.data.interactors.SearchInteractorImpl;
import com.example.goran.mymoviedb.data.model.keywords.Keyword;
import com.example.goran.mymoviedb.data.model.list.Movie;
import com.example.goran.mymoviedb.di.scope.PerFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Goran on 17.1.2018..
 */

@PerFragment
public class MovieSearchPresenter implements
        MovieSearchContract.Presenter, SearchInteractorImpl.SearchListener, SearchInteractorImpl.KeywordListener {

    private SearchInteractor searchInteractor;
    private MovieSearchContract.View searchView;

    private List<Keyword> keywords = new ArrayList<>();
    private List<Movie> results = new ArrayList<>();

    private String query;
    private int keywordId;
    private int currentPage;

    @Inject
    public MovieSearchPresenter(SearchInteractor searchInteractor, MovieSearchContract.View searchView) {
        this.searchInteractor = searchInteractor;
        this.searchView = searchView;
    }

    @Override
    public void onClickSearch(String query, Boolean byTitle) {

        searchView.showProgressBar();
        searchView.hideKeyboard();

        this.currentPage = 1;
        this.query = query;
        this.results.clear();

        search(byTitle);
    }

    private void search(Boolean byTitle) {

        if (byTitle) {
            searchInteractor.searchByTitle(query, currentPage++, this);

        } else {
            getKeywordId(query);
            searchInteractor.searchByKeywordId(keywordId, currentPage++, this);
        }
    }

    private void getKeywordId(String query) {

        for (int i = 0; i < keywords.size(); i++) {

            if (keywords.get(i).getName().equals(query)) {

                this.keywordId = keywords.get(i).getId();
            }
        }
    }

    @Override
    public void onSelectKeyword() {
        searchView.initTextWatcher();
    }

    @Override
    public void onSelectTitle() {
        searchView.removeTextWatcher();
    }

    @Override
    public void loadKeywords(String query) {
        searchInteractor.getKeywords(query, this);
    }

    @Override
    public void onClickResult(int movieId) {
        searchView.navigateToResult(movieId);
    }

    @Override
    public void onBottomReached(Boolean byTitle) {
        search(byTitle);
    }

    @Override
    public void onResultsReady(List<Movie> movieList) {
        results.addAll(movieList);
        searchView.displaySearchResults(results);
        searchView.hideProgressBar();
    }

    @Override
    public void onKeywordsReady(List<Keyword> keywordList) {
        keywords = keywordList;
        searchView.displayKeywords(keywords);
    }

    @Override
    public void onError() {
        searchView.hideProgressBar();
    }

}
