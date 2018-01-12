package com.example.goran.mymoviedb.di;

import com.example.goran.mymoviedb.data.Interactor;
import com.example.goran.mymoviedb.data.LoginInteractor;
import com.example.goran.mymoviedb.di.scope.ActivityScope;
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
    @ActivityScope
    HomeContract.View provideView() {
        return homeActivity;
    }

    @Provides
    @ActivityScope
    HomeContract.Presenter providePresenter(HomePresenter presenter) {
        return presenter;
    }

    @Provides
    @ActivityScope
    Interactor.Login provideInteractor(LoginInteractor interactor) {
        return interactor;
    }
}
