package com.example.goran.mymoviedb.movies.home;

import com.example.goran.mymoviedb.data.interactors.LoginInteractor;
import com.example.goran.mymoviedb.data.local.UserManager;
import com.example.goran.mymoviedb.di.scope.PerActivity;

import javax.inject.Inject;

/**
 * Created by Goran on 12.1.2018..
 */

@PerActivity
public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View homeView;
    private LoginInteractor loginInteractor;

    @Inject
    public HomePresenter(HomeContract.View homeView, LoginInteractor loginInteractor) {
        this.homeView = homeView;
        this.loginInteractor = loginInteractor;
    }

    @Override
    public void initView() {
        if (UserManager.getActiveUser() != null) {
            homeView.displayUsername(UserManager.getActiveUser().getUsername());
        }
    }

    @Override
    public void onClickLoginOut() {
        if (UserManager.getActiveUser() != null) {
            loginInteractor.deleteCurrentUser();
        }

        homeView.navigateToLogin();
    }

    @Override
    public void onClickPlayingNow() {
        homeView.showPlayingNowList();
    }

    @Override
    public void onClickUpcoming() {
        homeView.showUpcomingList();
    }

    @Override
    public void onClickPopular() {
        homeView.showPopularList();
    }

    @Override
    public void onClickTopRated() {
        homeView.showTopRatedList();
    }

    @Override
    public void onClickSearch() {
        homeView.showSearchFragment();
    }

    @Override
    public void onClickFavorite() {
        homeView.showFavoriteList();
    }
}
