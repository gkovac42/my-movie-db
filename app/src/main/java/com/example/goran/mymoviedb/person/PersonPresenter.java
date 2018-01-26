package com.example.goran.mymoviedb.person;

import com.example.goran.mymoviedb.data.interactors.PersonInteractor;
import com.example.goran.mymoviedb.data.interactors.PersonInteractorImpl;
import com.example.goran.mymoviedb.data.model.list.Movie;
import com.example.goran.mymoviedb.data.model.person.Person;
import com.example.goran.mymoviedb.di.scope.PerActivity;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Goran on 26.1.2018..
 */

@PerActivity
public class PersonPresenter implements PersonContract.Presenter, PersonInteractorImpl.PersonListener {

    private PersonContract.View personView;
    private PersonInteractor personInteractor;

    private int personId;

    @Inject
    public PersonPresenter(PersonContract.View personView, PersonInteractor personInteractor) {
        this.personView = personView;
        this.personInteractor = personInteractor;
    }

    @Override
    public void initPresenter(int personId) {
        this.personId = personId;
    }

    @Override
    public void loadPersonDetails() {
        personInteractor.getPersonDetails(personId, this);
        personInteractor.getRelatedMovies(personId, this);
    }

    @Override
    public void onClickRelatedMovie(int movieId) {
        personView.navigateToRelatedMovie(movieId);
    }

    @Override
    public void onDataReady(Person person) {
        personView.displayPersonDetails(person);
    }

    @Override
    public void onDataReady(List<Movie> movies) {
        personView.displayRelatedMovies(movies);
    }

    @Override
    public void onError() {

    }
}
