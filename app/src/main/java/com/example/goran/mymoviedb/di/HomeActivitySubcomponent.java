package com.example.goran.mymoviedb.di;

import com.example.goran.mymoviedb.di.scope.ActivityScope;
import com.example.goran.mymoviedb.movies.home.HomeActivity;

import dagger.Subcomponent;

/**
 * Created by Goran on 10.1.2018..
 */

@ActivityScope
@Subcomponent (modules = {HomeActivityModule.class, KeystoreModule.class})
public interface HomeActivitySubcomponent {

    void inject(HomeActivity homeActivity);
}
