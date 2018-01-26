package com.example.goran.mymoviedb.di;

import com.example.goran.mymoviedb.di.scope.PerFragment;
import com.example.goran.mymoviedb.movies.details.MovieCreditsFragment;

import dagger.Subcomponent;

/**
 * Created by Goran on 10.1.2018..
 */

@PerFragment
@Subcomponent(modules = MovieCreditsFragmentModule.class)
public interface MovieCreditsFragmentSubcomponent {

    void inject(MovieCreditsFragment movieCreditsFragment);
}
