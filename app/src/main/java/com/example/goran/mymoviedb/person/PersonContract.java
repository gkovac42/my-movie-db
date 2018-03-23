package com.example.goran.mymoviedb.person;

import com.example.goran.mymoviedb.base.BaseView;
import com.example.goran.mymoviedb.data.model.list.Movie;
import com.example.goran.mymoviedb.data.model.person.Person;

import java.util.List;

/**
 * Created by Goran on 26.1.2018..
 */

public interface PersonContract {

    interface View extends BaseView {

        void displayPersonDetails(Person person);

        void displayRelatedMovies(List<Movie> movies);

        void navigateToRelatedMovie(int movieId);
    }

    interface Presenter {

        void initPresenter(int personId);

        void loadPersonData();

        void onClickRelatedMovie(int movieId);
    }
}
