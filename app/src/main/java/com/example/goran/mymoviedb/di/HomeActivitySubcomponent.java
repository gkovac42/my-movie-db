package com.example.goran.mymoviedb.di;

import com.example.goran.mymoviedb.data.local.KeystoreModule;
import com.example.goran.mymoviedb.di.scope.PerActivity;
import com.example.goran.mymoviedb.movies.home.HomeActivity;

import dagger.Subcomponent;

/**
 * Created by Goran on 10.1.2018..
 */

@PerActivity
@Subcomponent (modules = {HomeActivityModule.class, KeystoreModule.class})
public interface HomeActivitySubcomponent {

    void inject(HomeActivity homeActivity);
}
