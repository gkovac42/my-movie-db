package com.example.goran.mymoviedb.movies.search;

import com.example.goran.mymoviedb.data.model.keywords.Keyword;
import com.example.goran.mymoviedb.data.model.list.Movie;

import java.util.List;

/**
 * Created by Goran on 17.1.2018..
 */

public interface MovieSearchContract {

    interface View {

        void showProgressBar();

        void hideProgressBar();

        void hideKeyboard();

        void initTextWatcher();

        void removeTextWatcher();

        void displaySearchResults(List<Movie> movieList);

        void displayKeywords(List<Keyword> keywordList);

        void navigateToResult(int movieId);

    }

    interface Presenter {

        void onClickSearch(String query, Boolean searchByTitle);

        void onSelectKeyword();

        void onSelectTitle();

        void loadKeywords(String query);

        void onClickResult(int movieId);

        void onBottomReached(Boolean searchByTitle);

    }
}
