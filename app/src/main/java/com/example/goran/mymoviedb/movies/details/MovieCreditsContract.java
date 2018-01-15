package com.example.goran.mymoviedb.movies.details;

import com.example.goran.mymoviedb.data.model.singlemovie.MovieCredits;

/**
 * Created by Goran on 12.1.2018..
 */

public interface MovieCreditsContract {

    interface View {

        void displayCredits(MovieCredits movieCredits);
    }

    interface Presenter {

        void loadCredits();
    }

    interface Model {
        void getCredits();

    }

}
