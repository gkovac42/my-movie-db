package com.example.goran.mymoviedb.di;

import com.example.goran.mymoviedb.data.remote.DetailsInteractor;
import com.example.goran.mymoviedb.di.scope.FragmentScope;
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
    @FragmentScope
    MovieDetailsContract.View provideView() {
        return movieDetailsFragment;
    }

    @Provides
    @FragmentScope
    MovieDetailsContract.Presenter providePresenter(MovieDetailsPresenter presenter) {
        return presenter;
    }

    @Provides
    @FragmentScope
    MovieDetailsContract.Model provideInteractor(DetailsInteractor interactor) {
        return interactor;
    }
}
