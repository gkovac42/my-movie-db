package com.example.goran.mymoviedb.movies.details;

import com.example.goran.mymoviedb.data.interactors.CreditsInteractor;
import com.example.goran.mymoviedb.data.interactors.CreditsInteractorImpl;
import com.example.goran.mymoviedb.data.model.details.Credits;
import com.example.goran.mymoviedb.di.scope.PerFragment;

import javax.inject.Inject;

/**
 * Created by Goran on 12.1.2018..
 */

@PerFragment
public class MovieCreditsPresenter implements MovieCreditsContract.Presenter, CreditsInteractorImpl.CreditsListener {

    private MovieCreditsContract.View creditsView;
    private CreditsInteractor creditsInteractor;

    private int movieId;

    @Inject
    public MovieCreditsPresenter(MovieCreditsContract.View creditsView, CreditsInteractor creditsInteractor) {
        this.creditsView = creditsView;
        this.creditsInteractor = creditsInteractor;

        creditsInteractor.setListener(this);
    }


    @Override
    public void initPresenter(int movieId) {
        this.movieId = movieId;
    }

    @Override
    public void loadCredits() {
        creditsInteractor.getCredits(movieId);
    }

    @Override
    public void onCreditsReady(Credits credits) {
        creditsView.displayCast(credits.getCast());
        creditsView.displayCrew(credits.getCrew());
    }

    @Override
    public void onError() {

    }

    @Override
    public void onClickPerson(int personId) {
        if (personId != 0) {
            creditsView.navigateToPerson(personId);
        }
    }
}
