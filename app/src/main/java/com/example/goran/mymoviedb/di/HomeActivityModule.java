package com.example.goran.mymoviedb.di;

import android.support.v7.app.AppCompatActivity;

import com.example.goran.mymoviedb.data.interactors.LoginInteractor;
import com.example.goran.mymoviedb.data.interactors.LoginInteractorImpl;
import com.example.goran.mymoviedb.di.scope.PerActivity;
import com.example.goran.mymoviedb.movies.home.HomeActivity;
import com.example.goran.mymoviedb.movies.home.HomeContract;
import com.example.goran.mymoviedb.movies.home.HomePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Goran on 10.1.2018..
 */

@Module
public class HomeActivityModule {

    private HomeActivity homeActivity;

    public HomeActivityModule(HomeActivity activity) {
        this.homeActivity = activity;
    }

    @Provides
    @PerActivity
    AppCompatActivity provideAppcompatActivity() {
        return homeActivity;
    }

    @Provides
    @PerActivity
    HomeContract.View provideView() {
        return homeActivity;
    }

    @Provides
    @PerActivity
    HomeContract.Presenter providePresenter(HomePresenter presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    LoginInteractor provideLoginInteractor(LoginInteractorImpl loginInteractor) {
        return loginInteractor;
    }
}
