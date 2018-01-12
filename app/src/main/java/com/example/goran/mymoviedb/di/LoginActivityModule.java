package com.example.goran.mymoviedb.di;

import com.example.goran.mymoviedb.data.Interactor;
import com.example.goran.mymoviedb.data.LoginInteractor;
import com.example.goran.mymoviedb.di.scope.ActivityScope;
import com.example.goran.mymoviedb.login.LoginActivity;
import com.example.goran.mymoviedb.login.LoginContract;
import com.example.goran.mymoviedb.login.LoginPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Goran on 10.1.2018..
 */

@Module
public class LoginActivityModule {

    private LoginActivity loginActivity;

    public LoginActivityModule(LoginActivity activity) {
        this.loginActivity = activity;
    }

    @Provides
    @ActivityScope
    LoginContract.View provideView() {
        return loginActivity;
    }

    @Provides
    @ActivityScope
    LoginContract.Presenter providePresenter(LoginPresenter presenter) {
        return presenter;
    }

    @Provides
    @ActivityScope
    Interactor.Login provideInteractor(LoginInteractor interactor) {
        return interactor;
    }

}
