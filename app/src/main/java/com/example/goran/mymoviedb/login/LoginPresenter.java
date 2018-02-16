package com.example.goran.mymoviedb.login;

import com.example.goran.mymoviedb.data.interactors.LoginInteractor;
import com.example.goran.mymoviedb.data.interactors.LoginInteractorImpl;
import com.example.goran.mymoviedb.data.model.user.User;
import com.example.goran.mymoviedb.di.scope.PerActivity;

import javax.inject.Inject;

/**
 * Created by Goran on 10.1.2018..
 */

@PerActivity
public class LoginPresenter implements LoginContract.Presenter, LoginInteractorImpl.LoginListener {

    private LoginContract.View view;
    private LoginInteractor interactor;

    @Inject
    public LoginPresenter(LoginInteractor interactor, LoginContract.View view) {
        this.view = view;
        this.interactor = interactor;
        this.interactor.setListener(this);
    }

    @Override
    public void checkForSavedUser() {
        User tempUser = interactor.loadUser();

        if (tempUser != null) {
            interactor.initLogin(tempUser.getUsername(), tempUser.getPassword());
            view.showProgressDialog();
        }
    }

    @Override
    public void onClickLogin(String username, String password) {

        if (!usernameValid(username)) {
            view.displayUsernameError();

        } else if (!passwordValid(password)) {
            view.displayPasswordError();

        } else {
            interactor.initLogin(username, password);
            view.showProgressDialog();
        }
    }

    @Override
    public void onLoginError() {
        interactor.deleteActiveUser();
        view.hideProgressDialog();
        view.displayLoginError();
    }

    @Override
    public void onLoginSuccess(String username, String password) {
        if (view.stayLoggedIn()) {
            User tempUser = new User(username, password);
            interactor.saveUser(tempUser);
        }

        view.navigateToMain();
    }

    @Override
    public void onClickGuest() {
        if (interactor.getActiveUser() != null) {
            interactor.deleteActiveUser();
        }

        view.navigateToMain();
    }

    private boolean usernameValid(String username) {
        return username.length() > 2 && username.length() < 21;
    }

    private boolean passwordValid(String password) {
        return password.length() > 7 && password.length() < 21;
    }
}
