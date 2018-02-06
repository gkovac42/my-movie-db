package com.example.goran.mymoviedb.login;

import com.example.goran.mymoviedb.data.interactors.LoginInteractor;
import com.example.goran.mymoviedb.data.interactors.LoginInteractorImpl;
import com.example.goran.mymoviedb.data.model.auth.User;
import com.example.goran.mymoviedb.di.scope.PerActivity;
import com.example.goran.mymoviedb.login.util.UserInput;

import javax.inject.Inject;

/**
 * Created by Goran on 10.1.2018..
 */

@PerActivity
public class LoginPresenter implements LoginContract.Presenter, LoginInteractorImpl.LoginListener {

    private LoginInteractor loginInteractor;
    private LoginContract.View loginView;

    @Inject
    public LoginPresenter(LoginInteractor interactor, LoginContract.View view) {
        this.loginInteractor = interactor;
        this.loginView = view;

        loginInteractor.setListener(this);
    }

    @Override
    public void checkForCurrentUser() {
        // try to load saved user data
        User savedUser = loginInteractor.loadUser();

        if (savedUser.getUsername() != null) {
            // if not null try to log in
            loginView.showProgressDialog();
            loginInteractor.initLogin(savedUser.getUsername(), savedUser.getPassword());
        }
    }

    @Override
    public void onClickLogin(String username, String password) {
        // check user input
        if (!UserInput.usernameValid(username)) {
            loginView.displayUsernameError();

        } else if (!UserInput.passwordValid(password)) {
            loginView.displayPasswordError();

            // if input is valid start login
        } else {
            loginInteractor.initLogin(username, password);
            loginView.showProgressDialog();
        }
    }

    @Override
    public void onLoginError() {
        loginView.hideProgressDialog();
        loginView.displayLoginError();
    }

    @Override
    public void onLoginSuccess(String username, String password) {

        if (loginView.stayLoggedIn()) {
            User user = new User(username, password);
            loginInteractor.saveUser(user);
        }

        loginView.hideProgressDialog();
        loginView.navigateToMain();
    }

    @Override
    public void onClickGuest() {
        loginInteractor.deleteActiveUser();
        loginView.navigateToMain();
    }
}
