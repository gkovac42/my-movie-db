package com.example.goran.mymoviedb.data.interactors;

import java.util.List;

/**
 * Created by Goran on 23.1.2018..
 */

public interface DetailsInteractor extends BaseInteractor{

    void getMovieDetails(int movieId);

    void getSimilarList(int movieId);

    void setFavorite(boolean favorite, int movieId);

    void setRating(int movieId, double rating);

    void deleteRating(int movieId);

    boolean userNotNull();

    List<Integer> getUserFavoriteIds();

    List<Integer> getUserRatedIds();

}
