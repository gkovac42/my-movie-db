package com.example.goran.mymoviedb.movies.details;

import android.net.Uri;

import com.example.goran.mymoviedb.data.CreditsInteractor;
import com.example.goran.mymoviedb.data.model.singlemovie.Crew;
import com.example.goran.mymoviedb.data.model.singlemovie.Credits;
import com.example.goran.mymoviedb.di.scope.FragmentScope;

import javax.inject.Inject;

/**
 * Created by Goran on 12.1.2018..
 */

@FragmentScope
public class MovieCreditsPresenter implements MovieCreditsContract.Presenter {

    private MovieCreditsContract.View creditsView;
    private MovieCreditsContract.Model creditsInteractor;

    private int movieId;

    private static final String IMG_BASE_URL = "https://image.tmdb.org/t/p/w300";

    @Inject
    public MovieCreditsPresenter(MovieCreditsContract.View creditsView, MovieCreditsContract.Model creditsInteractor) {
        this.creditsView = creditsView;
        this.creditsInteractor = creditsInteractor;
    }


    @Override
    public void setMovieId(int id) {
        this.movieId = id;
    }

    @Override
    public void loadCredits() {

        creditsInteractor.getCredits(movieId, new CreditsInteractor.CreditsListener() {
            @Override
            public void onDataReady(Credits credits) {

                Crew director = creditsInteractor.getDirector(credits);
                Crew writer = creditsInteractor.getWriter(credits);

                if (director != null) {
                    creditsView.displayDirector(director.getName(),
                            Uri.parse(IMG_BASE_URL + director.getProfilePath()));
                } else {
                    creditsView.displayDirector("n/a", null);
                }

                if (writer != null) {
                    creditsView.displayWriter(writer.getName(),
                            Uri.parse(IMG_BASE_URL + writer.getProfilePath()));
                } else {
                    creditsView.displayWriter("n/a", null);
                }

                creditsView.displayCast(credits.getCast());
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void onDestroy() {
        creditsInteractor.dispose();
    }
}
