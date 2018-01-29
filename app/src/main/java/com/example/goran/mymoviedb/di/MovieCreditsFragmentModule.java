package com.example.goran.mymoviedb.di;

import android.arch.lifecycle.LifecycleOwner;

import com.example.goran.mymoviedb.data.interactors.CreditsInteractor;
import com.example.goran.mymoviedb.data.interactors.CreditsInteractorImpl;
import com.example.goran.mymoviedb.di.scope.PerFragment;
import com.example.goran.mymoviedb.movies.details.MovieCreditsContract;
import com.example.goran.mymoviedb.movies.details.MovieCreditsFragment;
import com.example.goran.mymoviedb.movies.details.MovieCreditsPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Goran on 10.1.2018..
 */

@Module
public class MovieCreditsFragmentModule {

    private MovieCreditsFragment movieCreditsFragment;

    public MovieCreditsFragmentModule(MovieCreditsFragment fragment) {
        this.movieCreditsFragment = fragment;
    }

    @Provides
    @PerFragment
    LifecycleOwner provideLifecycleOwner() {
        return movieCreditsFragment;
    }

    @Provides
    @PerFragment
    MovieCreditsContract.View provideView() {
        return movieCreditsFragment;
    }

    @Provides
    @PerFragment
    MovieCreditsContract.Presenter providePresenter(MovieCreditsPresenter presenter) {
        return presenter;
    }

    @Provides
    @PerFragment
    CreditsInteractor provideCreditsInteractor(CreditsInteractorImpl interactor) {
        return interactor;
    }
}
