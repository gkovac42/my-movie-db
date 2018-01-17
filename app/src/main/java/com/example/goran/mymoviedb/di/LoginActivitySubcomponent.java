package com.example.goran.mymoviedb.di;

import com.example.goran.mymoviedb.data.local.KeystoreModule;
import com.example.goran.mymoviedb.di.scope.ActivityScope;
import com.example.goran.mymoviedb.login.LoginActivity;

import dagger.Subcomponent;

/**
 * Created by Goran on 10.1.2018..
 */

@ActivityScope
@Subcomponent(modules = {LoginActivityModule.class, KeystoreModule.class})
public interface LoginActivitySubcomponent {

    void inject(LoginActivity loginActivity);

}
