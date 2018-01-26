package com.example.goran.mymoviedb.movies.details;

import android.net.Uri;

import com.example.goran.mymoviedb.data.interactors.CreditsInteractor;
import com.example.goran.mymoviedb.data.interactors.CreditsInteractorImpl;
import com.example.goran.mymoviedb.data.model.details.Credits;
import com.example.goran.mymoviedb.data.model.details.Crew;
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

    private static final String IMG_BASE_URL = "https://image.tmdb.org/t/p/w300";

    @Inject
    public MovieCreditsPresenter(MovieCreditsContract.View creditsView, CreditsInteractor creditsInteractor) {
        this.creditsView = creditsView;
        this.creditsInteractor = creditsInteractor;
    }


    @Override
    public void initPresenter(int movieId) {
        this.movieId = movieId;
    }

    @Override
    public void loadCredits() {
        creditsInteractor.getCredits(movieId, this);
    }

    @Override
    public void onClickPerson(int personId) {
        if (personId != 0) {
            creditsView.navigateToPerson(personId);
        }
    }

    @Override
    public void onDataReady(Credits credits) {
        Crew director = creditsInteractor.getDirector(credits);
        Crew writer = creditsInteractor.getWriter(credits);

        if (director != null) {
            creditsView.displayDirector(director.getName(),
                    Uri.parse(IMG_BASE_URL + director.getProfilePath()),
                    director.getId());
        } else {
            creditsView.displayDirector("n/a", null, 0);
        }

        if (writer != null) {
            creditsView.displayWriter(writer.getName(),
                    Uri.parse(IMG_BASE_URL + writer.getProfilePath()),
                    writer.getId());
        } else {
            creditsView.displayWriter("n/a", null, 0);
        }

        creditsView.displayCast(credits.getCast());
    }

    @Override
    public void onError() {

    }
}
