package com.example.goran.mymoviedb.di;

import com.example.goran.mymoviedb.data.CreditsInteractor;
import com.example.goran.mymoviedb.di.scope.FragmentScope;
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
    @FragmentScope
    MovieCreditsContract.View provideView() {
        return movieCreditsFragment;
    }

    @Provides
    @FragmentScope
    MovieCreditsContract.Presenter providePresenter(MovieCreditsPresenter presenter) {
        return presenter;
    }

    @Provides
    @FragmentScope
    MovieCreditsContract.Model provideInteractor(CreditsInteractor interactor) {
        return interactor;
    }
}
