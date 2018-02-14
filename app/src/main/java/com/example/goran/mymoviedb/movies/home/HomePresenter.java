package com.example.goran.mymoviedb.movies.home;

import com.example.goran.mymoviedb.data.interactors.LoginInteractor;
import com.example.goran.mymoviedb.di.scope.PerActivity;

import javax.inject.Inject;

/**
 * Created by Goran on 12.1.2018..
 */

@PerActivity
public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View view;
    private LoginInteractor interactor;

    @Inject
    public HomePresenter(HomeContract.View view, LoginInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void initView() {

        if (interactor.getActiveUser() != null) {
            view.displayActiveUser(interactor.getActiveUser().getUsername());

        } else {
            view.hideFavorites();
        }
    }

    @Override
    public void onClickLoginOut() {

        if (interactor.getActiveUser() != null) {
            interactor.deleteActiveUser();
            interactor.deleteSavedUser();
        }

        view.navigateToLogin();
    }

    @Override
    public void onClickMenuItem(int itemId) {
        view.navigateToMenuItem(itemId);
    }

    @Override
    public void onBackPressed() {
        view.exit();
    }
}
