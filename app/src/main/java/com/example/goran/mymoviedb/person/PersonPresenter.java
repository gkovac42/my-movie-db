package com.example.goran.mymoviedb.person;

import com.example.goran.mymoviedb.data.interactors.PersonInteractor;
import com.example.goran.mymoviedb.data.interactors.PersonInteractorImpl;
import com.example.goran.mymoviedb.data.model.person.PersonData;
import com.example.goran.mymoviedb.di.scope.PerActivity;

import javax.inject.Inject;

/**
 * Created by Goran on 26.1.2018..
 */

@PerActivity
public class PersonPresenter implements PersonContract.Presenter, PersonInteractorImpl.PersonListener {

    private PersonContract.View view;
    private PersonInteractor interactor;

    private int personId;

    @Inject
    public PersonPresenter(PersonContract.View view, PersonInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
        this.interactor.setListener(this);
    }

    @Override
    public void initPresenter(int personId) {
        this.personId = personId;
    }

    @Override
    public void loadPersonData() {
        view.showProgressDialog();
        interactor.getPersonData(personId);

    }

    @Override
    public void onDataReady(PersonData personData) {
        view.displayPersonDetails(personData.getPerson());
        view.displayRelatedMovies(personData.getRelatedMovies());
        view.hideProgressDialog();
    }

    @Override
    public void onError() {
        view.hideProgressDialog();
    }

    @Override
    public void onClickRelatedMovie(int movieId) {
        view.navigateToRelatedMovie(movieId);
    }

}
