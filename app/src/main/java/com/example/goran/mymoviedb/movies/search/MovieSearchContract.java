package com.example.goran.mymoviedb.movies.search;

import android.widget.ArrayAdapter;

import com.example.goran.mymoviedb.data.interactors.SearchInteractor;
import com.example.goran.mymoviedb.data.model.Movie;
import com.example.goran.mymoviedb.data.model.keywords.Keyword;

import java.util.List;

/**
 * Created by Goran on 17.1.2018..
 */

public interface MovieSearchContract {

    interface View {

        void showProgressBar();

        void hideProgressBar();

        void initTextWatcher();

        void removeTextWatcher();

        ArrayAdapter<Keyword> getKeywordAdapter();

        void displaySearchResults(List<Movie> movieList);

        void displayKeywords(List<Keyword> keywordList);

        void navigateToResult(int movie);

    }

    interface Presenter {

        void onClickSearch(String query, Boolean searchByTitle);

        void onSelectKeyword();

        void onSelectTitle();

        void loadKeywords(String query);

        void onClickResult(int position);

        void onDestroy();

    }

    interface Model {

        void searchByTitle(String query, SearchInteractor.SearchListener listener);

        void getKeywords(String query, SearchInteractor.KeywordListener listener);

        void searchByKeyword(String query, SearchInteractor.SearchListener listener);

        void dispose();

    }
}
