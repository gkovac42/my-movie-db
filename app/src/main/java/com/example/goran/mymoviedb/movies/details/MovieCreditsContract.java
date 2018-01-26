package com.example.goran.mymoviedb.movies.details;

import android.net.Uri;

import com.example.goran.mymoviedb.data.model.details.Cast;

import java.util.List;

/**
 * Created by Goran on 12.1.2018..
 */

public interface MovieCreditsContract {

    interface View {

        void displayDirector(String name, Uri profile, int id);

        void displayWriter(String name, Uri profile, int id);

        void displayCast(List<Cast> cast);

        void navigateToPerson(int personId);
    }

    interface Presenter {

        void initPresenter(int movieId);

        void loadCredits();

        void onClickPerson(int personId);
    }
}
