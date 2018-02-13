package com.example.goran.mymoviedb.movies.home;

import com.example.goran.mymoviedb.data.interactors.LoginInteractor;
import com.example.goran.mymoviedb.data.model.auth.User;
import com.example.goran.mymoviedb.di.scope.PerActivity;

import javax.inject.Inject;

/**
 * Created by Goran on 12.1.2018..
 */

@PerActivity
public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View homeView;
    private LoginInteractor loginInteractor;

    private User activeUser;

    @Inject
    public HomePresenter(HomeContract.View homeView, LoginInteractor loginInteractor) {
        this.homeView = homeView;
        this.loginInteractor = loginInteractor;
    }

    @Override
    public void initView() {
        activeUser = loginInteractor.getActiveUser();

        if (activeUser != null) {
            homeView.displayActiveUser(activeUser.getUsername());

        } else {
            homeView.hideFavorites();
        }
    }

    @Override
    public void onClickLoginOut() {
        if (activeUser != null) {
            loginInteractor.deleteActiveUser();
            loginInteractor.deleteSavedUser();
        }

        homeView.navigateToLogin();
    }

    @Override
    public void onClickMenuItem(int itemId) {
        homeView.showSelectedMenuItem(itemId);
    }

    @Override
    public void onBackPressed() {
        homeView.exit();
    }
}
