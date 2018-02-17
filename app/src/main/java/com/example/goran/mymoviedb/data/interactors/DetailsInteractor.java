package com.example.goran.mymoviedb.data.interactors;

/**
 * Created by Goran on 23.1.2018..
 */

public interface DetailsInteractor extends BaseInteractor {

    void getMovieData(int movieId);

    void postFavorite(boolean favorite, int movieId);

    void postRating(int movieId, double rating);

    void deleteRating(int movieId);

    boolean userNotNull();
}