package com.example.goran.mymoviedb.login;

import com.example.goran.mymoviedb.data.interactors.LoginInteractor;
import com.example.goran.mymoviedb.data.interactors.LoginInteractorImpl;
import com.example.goran.mymoviedb.data.local.UserManager;
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
    public LoginPresenter(LoginInteractor model, LoginContract.View view) {
        this.loginInteractor = model;
        this.loginView = view;
    }

    @Override
    public void checkForCurrentUser() {
        // try to load saved user data
        User currentUser = loginInteractor.loadAndDecryptUser();

        if (currentUser.getUsername() != null) {
            // if not null use saved username and sessionId
            UserManager.setActiveUser(currentUser);
            loginView.navigateToMain(currentUser);
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

            loginInteractor.initLogin(username, password, this);
        }
    }

    @Override
    public void onLoginError() {
        loginView.displayLoginError();
    }

    @Override
    public void onLoginSuccess(User user) {

        UserManager.setActiveUser(user);

        // if checked save user data
        if (loginView.stayLoggedIn()) {
            loginInteractor.encryptAndSaveUser(user);

        } else {
            loginInteractor.deleteCurrentUser();
        }

        loginView.navigateToMain(user);
    }

    @Override
    public void onClickGuest() {
        loginView.navigateToMain(null);
    }

}
