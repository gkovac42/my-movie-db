package com.example.goran.mymoviedb.data.interactors;

/**
 * Created by Goran on 23.1.2018..
 */

public interface SearchInteractor extends BaseInteractor {

    void searchByTitle(String query, int page);

    void getKeywords(String query);

    void searchByKeywordId(int query, int page);

}
