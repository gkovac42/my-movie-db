package com.example.goran.mymoviedb.di;

import com.example.goran.mymoviedb.data.interactors.SearchInteractor;
import com.example.goran.mymoviedb.di.scope.FragmentScope;
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
    @FragmentScope
    MovieSearchContract.View provideView() {
        return movieSearchFragment;
    }

    @Provides
    @FragmentScope
    MovieSearchContract.Presenter providePresenter(MovieSearchPresenter presenter) {
        return presenter;
    }

    @Provides
    @FragmentScope
    MovieSearchContract.Model provideInteractor(SearchInteractor interactor) {
        return interactor;
    }
}
