package com.example.goran.mymoviedb.di;

import com.example.goran.mymoviedb.di.scope.PerActivity;
import com.example.goran.mymoviedb.login.LoginActivity;

import dagger.Subcomponent;

/**
 * Created by Goran on 10.1.2018..
 */

@PerActivity
@Subcomponent(modules = LoginActivityModule.class)
public interface LoginActivitySubcomponent {

    void inject(LoginActivity loginActivity);

}
