package com.example.goran.mymoviedb.di;

import android.support.v7.app.AppCompatActivity;

import com.example.goran.mymoviedb.data.interactors.LoginInteractor;
import com.example.goran.mymoviedb.data.interactors.LoginInteractorImpl;
import com.example.goran.mymoviedb.di.scope.PerActivity;
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
    @PerActivity
    AppCompatActivity provideAppcompatActivity() {
        return loginActivity;
    }

    @Provides
    @PerActivity
    LoginContract.View provideView() {
        return loginActivity;
    }

    @Provides
    @PerActivity
    LoginContract.Presenter providePresenter(LoginPresenter presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    LoginInteractor provideLoginInteractor(LoginInteractorImpl interactor) {
        return interactor;
    }
}
