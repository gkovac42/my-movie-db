package com.example.goran.mymoviedb.data.interactors;

/**
 * Created by Goran on 23.1.2018..
 */

public interface SearchInteractor {

    void searchByTitle(String query, int page, SearchInteractorImpl.SearchListener listener);

    void getKeywords(String query, SearchInteractorImpl.KeywordListener listener);

    void searchByKeywordId(int query, int page, SearchInteractorImpl.SearchListener listener);

}
