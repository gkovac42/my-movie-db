package com.example.goran.mymoviedb.di;

import android.support.v4.app.Fragment;

import com.example.goran.mymoviedb.data.interactors.ListInteractor;
import com.example.goran.mymoviedb.data.interactors.ListInteractorImpl;
import com.example.goran.mymoviedb.di.scope.PerFragment;
import com.example.goran.mymoviedb.movies.list.MovieListContract;
import com.example.goran.mymoviedb.movies.list.MovieListFragment;
import com.example.goran.mymoviedb.movies.list.MovieListPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Goran on 10.1.2018..
 */

@Module
public class MovieListFragmentModule {

    private MovieListFragment movieListFragment;

    public MovieListFragmentModule(MovieListFragment fragment) {
        this.movieListFragment = fragment;
    }

    @Provides
    @PerFragment
    Fragment provideFragment() {
        return movieListFragment;
    }

    @Provides
    @PerFragment
    MovieListContract.View provideView() {
        return movieListFragment;
    }

    @Provides
    @PerFragment
    MovieListContract.Presenter providePresenter(MovieListPresenter presenter) {
        return presenter;
    }

    @Provides
    @PerFragment
    ListInteractor provideListInteractor(ListInteractorImpl interactor) {
        return interactor;
    }
}
