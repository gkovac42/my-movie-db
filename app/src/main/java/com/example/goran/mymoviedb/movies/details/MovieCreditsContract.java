package com.example.goran.mymoviedb.movies.details;

import com.example.goran.mymoviedb.data.model.details.Cast;
import com.example.goran.mymoviedb.data.model.details.Crew;

import java.util.List;

/**
 * Created by Goran on 12.1.2018..
 */

public interface MovieCreditsContract {

    interface View {

        void displayCrew(List<Crew> crew);

        void displayCast(List<Cast> cast);

        void navigateToPerson(int personId);
    }

    interface Presenter {

        void initPresenter(int movieId);

        void loadCredits();

        void onClickPerson(int personId);
    }
}
