package com.example.goran.mymoviedb.data.interactors;

/**
 * Created by Goran on 26.1.2018..
 */

public interface PersonInteractor extends BaseInteractor {

    void getPersonDetails(int personId);

    void getRelatedMovies(int personId);

}
