package com.example.goran.mymoviedb.movies.home;

import com.example.goran.mymoviedb.data.Interactor;
import com.example.goran.mymoviedb.di.scope.ActivityScope;

import javax.inject.Inject;

/**
 * Created by Goran on 12.1.2018..
 */

@ActivityScope
public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View homeView;
    private Interactor.Login loginInteractor;

    @Inject
    public HomePresenter(HomeContract.View homeView, Interactor.Login loginInteractor) {
        this.homeView = homeView;
        this.loginInteractor = loginInteractor;
    }

    @Override
    public void getUserData() {
        homeView.initUser();
    }

    @Override
    public void onClickLoginOut() {

        if (!homeView.isGuest()) {
            loginInteractor.deleteCurrentUser();
        }

        homeView.navigateToLogin();
    }

    @Override
    public void onClickPlayingNow() {
        homeView.showPlayingNow();
    }

    @Override
    public void onClickSearch() {
        homeView.showSearch();
    }
}