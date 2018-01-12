package com.example.goran.mymoviedb.di;

import com.example.goran.mymoviedb.data.Interactor;
import com.example.goran.mymoviedb.data.MoviesInteractor;
import com.example.goran.mymoviedb.di.scope.FragmentScope;
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
    @FragmentScope
    MovieListContract.View provideView() {
        return movieListFragment;
    }

    @Provides
    @FragmentScope
    MovieListContract.Presenter providePresenter(MovieListPresenter presenter) {
        return presenter;
    }

    @Provides
    @FragmentScope
    Interactor.Movies provideInteractor(MoviesInteractor interactor) {
        return interactor;
    }
}
