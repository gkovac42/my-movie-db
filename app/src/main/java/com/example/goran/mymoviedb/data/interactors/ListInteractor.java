package com.example.goran.mymoviedb.data.interactors;

/**
 * Created by Goran on 23.1.2018..
 */

public interface ListInteractor {

    void setListener(ListInteractorImpl.ListListener listener);

    void getMovieList(int category, int page);

}
