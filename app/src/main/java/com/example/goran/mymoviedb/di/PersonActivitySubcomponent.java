package com.example.goran.mymoviedb.di;

import com.example.goran.mymoviedb.di.scope.PerActivity;
import com.example.goran.mymoviedb.person.PersonActivity;

import dagger.Subcomponent;

/**
 * Created by Goran on 10.1.2018..
 */

@PerActivity
@Subcomponent(modules = PersonActivityModule.class)
public interface PersonActivitySubcomponent {

    void inject(PersonActivity personActivity);

}
