package com.example.goran.mymoviedb.di;

import android.support.v4.app.Fragment;

import com.example.goran.mymoviedb.data.interactors.SearchInteractor;
import com.example.goran.mymoviedb.data.interactors.SearchInteractorImpl;
import com.example.goran.mymoviedb.di.scope.PerFragment;
import com.example.goran.mymoviedb.movies.search.MovieSearchContract;
import com.example.goran.mymoviedb.movies.search.MovieSearchFragment;
import com.example.goran.mymoviedb.movies.search.MovieSearchPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Goran on 10.1.2018..
 */

@Module
public class MovieSearchFragmentModule {

    private MovieSearchFragment movieSearchFragment;

    public MovieSearchFragmentModule(MovieSearchFragment fragment) {
        this.movieSearchFragment = fragment;
    }

    @Provides
    @PerFragment
    Fragment provideFragment() {
        return movieSearchFragment;
    }

    @Provides
    @PerFragment
    MovieSearchContract.View provideView() {
        return movieSearchFragment;
    }

    @Provides
    @PerFragment
    MovieSearchContract.Presenter providePresenter(MovieSearchPresenter presenter) {
        return presenter;
    }

    @Provides
    @PerFragment
    SearchInteractor provideSearchInteractor(SearchInteractorImpl interactor) {
        return interactor;
    }
}
