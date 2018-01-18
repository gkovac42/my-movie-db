package com.example.goran.mymoviedb.movies.search;

import com.example.goran.mymoviedb.data.interactors.SearchInteractor;
import com.example.goran.mymoviedb.data.model.list.Movie;
import com.example.goran.mymoviedb.data.model.keywords.Keyword;
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
            int count = searchView.getKeywordAdapter().getCount();

            // nisam htio uzeti keywordId preko onClick listenera jer se može upisati riječ i napraviti search bez click-a
            // ovako malo ružno izgleda ali nisam se sjetio boljeg rješenja

            for (int i = 0; i < count; i++) {
                if (searchView.getKeywordAdapter().getItem(i).getName().equals(query)) {
                    keywordId = searchView.getKeywordAdapter().getItem(i).getId();
                }
            }

            searchInteractor.searchByKeyword(String.valueOf(keywordId), this);
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
    public void onClickResult(int position) {
        searchView.navigateToResult(position);
    }

    @Override
    public void onResultsReady(List<Movie> movieList) {
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
