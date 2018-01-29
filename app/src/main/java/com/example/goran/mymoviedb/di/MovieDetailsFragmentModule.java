package com.example.goran.mymoviedb.di;


import android.arch.lifecycle.LifecycleOwner;

import com.example.goran.mymoviedb.data.interactors.DetailsInteractor;
import com.example.goran.mymoviedb.data.interactors.DetailsInteractorImpl;
import com.example.goran.mymoviedb.di.scope.PerFragment;
import com.example.goran.mymoviedb.movies.details.MovieDetailsContract;
import com.example.goran.mymoviedb.movies.details.MovieDetailsFragment;
import com.example.goran.mymoviedb.movies.details.MovieDetailsPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Goran on 10.1.2018..
 */

@Module
public class MovieDetailsFragmentModule {

    private MovieDetailsFragment movieDetailsFragment;

    public MovieDetailsFragmentModule(MovieDetailsFragment fragment) {
        this.movieDetailsFragment = fragment;
    }

    @Provides
    @PerFragment
    LifecycleOwner provideLifecycleOwner() {
        return movieDetailsFragment;
    }

    @Provides
    @PerFragment
    MovieDetailsContract.View provideView() {
        return movieDetailsFragment;
    }

    @Provides
    @PerFragment
    MovieDetailsContract.Presenter providePresenter(MovieDetailsPresenter presenter) {
        return presenter;
    }

    @Provides
    @PerFragment
    DetailsInteractor provideDetailsInteractor(DetailsInteractorImpl interactor) {
        return interactor;
    }
}
