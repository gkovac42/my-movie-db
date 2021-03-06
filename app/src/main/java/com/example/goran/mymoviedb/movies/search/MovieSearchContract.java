package com.example.goran.mymoviedb.movies.search;

import com.example.goran.mymoviedb.base.BaseView;
import com.example.goran.mymoviedb.data.model.keywords.Keyword;
import com.example.goran.mymoviedb.data.model.list.Movie;

import java.util.List;

/**
 * Created by Goran on 17.1.2018..
 */

public interface MovieSearchContract {

    interface View extends BaseView {

        void hideKeyboard();

        void initTextWatcher();

        void removeTextWatcher();

        void clearSearchResults();

        void displaySearchResults(List<Movie> movieList);

        void displayKeywords(List<Keyword> keywordList);

        void navigateToResult(int movieId);

    }

    interface Presenter {

        void onClickSearch(String query, Boolean searchByTitle);

        void onSelectByKeyword();

        void onSelectByTitle();

        void loadKeywords(String query);

        void onClickResult(int movieId);

        void onBottomReached(Boolean searchByTitle);

    }
}
