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
        MovieSearchContract.Presenter, SearchInteractorImpl.SearchListener {

    private MovieSearchContract.View view;
    private SearchInteractor interactor;

    private List<Keyword> keywords;

    private String query;
    private int keywordId;
    private int currentPage;

    @Inject
    public MovieSearchPresenter(SearchInteractor interactor, MovieSearchContract.View view) {
        this.view = view;
        this.interactor = interactor;
        this.interactor.setListener(this);
        this.keywords = new ArrayList<>();
    }

    @Override
    public void onClickSearch(String query, Boolean byTitle) {

        view.showProgressDialog();
        view.hideKeyboard();
        view.clearSearchResults();

        this.currentPage = 1;
        this.query = query;

        search(byTitle);
    }

    private void search(Boolean byTitle) {

        if (byTitle) {
            interactor.searchByTitle(query, currentPage++);

        } else {
            getKeywordId(query);
            interactor.searchByKeywordId(keywordId, currentPage++);
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
    public void onSelectByKeyword() {
        view.initTextWatcher();
    }

    @Override
    public void onSelectByTitle() {
        view.removeTextWatcher();
    }

    @Override
    public void loadKeywords(String query) {
        interactor.getKeywords(query);
    }

    @Override
    public void onClickResult(int movieId) {
        view.navigateToResult(movieId);
    }

    @Override
    public void onBottomReached(Boolean byTitle) {
        search(byTitle);
    }

    @Override
    public void onResultsReady(List<Movie> movieList) {
        view.displaySearchResults(movieList);
        view.hideProgressDialog();
    }

    @Override
    public void onKeywordsReady(List<Keyword> keywordList) {
        keywords = keywordList;
        view.displayKeywords(keywords);
    }

    @Override
    public void onError() {
        view.hideProgressDialog();
    }

}
