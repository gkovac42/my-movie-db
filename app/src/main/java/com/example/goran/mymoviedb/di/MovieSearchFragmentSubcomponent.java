package com.example.goran.mymoviedb.di;

import com.example.goran.mymoviedb.di.scope.FragmentScope;
import com.example.goran.mymoviedb.movies.search.MovieSearchFragment;

import dagger.Subcomponent;

/**
 * Created by Goran on 10.1.2018..
 */

@FragmentScope
@Subcomponent(modules = MovieSearchFragmentModule.class)
public interface MovieSearchFragmentSubcomponent {

    void inject(MovieSearchFragment movieSearchFragment);
}
