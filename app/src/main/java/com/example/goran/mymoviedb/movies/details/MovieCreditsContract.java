package com.example.goran.mymoviedb.movies.details;

import android.net.Uri;

import com.example.goran.mymoviedb.data.CreditsInteractor;
import com.example.goran.mymoviedb.data.model.singlemovie.Cast;
import com.example.goran.mymoviedb.data.model.singlemovie.Crew;
import com.example.goran.mymoviedb.data.model.singlemovie.Credits;

import java.util.List;

/**
 * Created by Goran on 12.1.2018..
 */

public interface MovieCreditsContract {

    interface View {

        void displayDirector(String name, Uri profile);

        void displayWriter(String name, Uri profile);

        void displayCast(List<Cast> cast);
    }

    interface Presenter {

        void setMovieId(int id);

        void loadCredits();

        void onDestroy();
    }

    interface Model {

        void getCredits(int movieId, CreditsInteractor.CreditsListener listener);

        Crew getDirector(Credits credits);

        Crew getWriter(Credits credits);

        void dispose();

    }

}
