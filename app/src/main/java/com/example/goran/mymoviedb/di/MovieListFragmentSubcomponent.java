package com.example.goran.mymoviedb.di;

import com.example.goran.mymoviedb.di.scope.FragmentScope;
import com.example.goran.mymoviedb.movies.list.MovieListFragment;

import dagger.Subcomponent;

/**
 * Created by Goran on 10.1.2018..
 */

@FragmentScope
@Subcomponent(modules = MovieListFragmentModule.class)
public interface MovieListFragmentSubcomponent {

    void inject(MovieListFragment movieListFragment);
}
