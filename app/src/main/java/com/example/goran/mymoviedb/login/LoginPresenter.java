package com.example.goran.mymoviedb.login;

import com.example.goran.mymoviedb.data.interactors.LoginInteractor;
import com.example.goran.mymoviedb.data.model.auth.User;
import com.example.goran.mymoviedb.di.scope.ActivityScope;
import com.example.goran.mymoviedb.login.util.UserInput;

import javax.inject.Inject;

/**
 * Created by Goran on 10.1.2018..
 */

@ActivityScope
public class LoginPresenter implements LoginContract.Presenter, LoginInteractor.LoginListener {

    private LoginContract.Model loginInteractor;
    private LoginContract.View loginView;

    private String username;
    private String password;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View view) {
        this.loginInteractor = model;
        this.loginView = view;
    }

    @Override
    public void checkForCurrentUser() {
        // try to load saved user data
        User currentUser = loginInteractor.loadAndDecryptUser();

        if (currentUser.getUsername() != null) {
            // if not null use saved username and sessionId
            loginView.navigateToMain(currentUser.getUsername(), currentUser.getSessionId());
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
            this.username = username;
            this.password = password;
            loginInteractor.initLogin(username, password, this);
        }
    }

    @Override
    public void onLoginError() {
        loginView.displayLoginError();
    }

    @Override
    public void onLoginSuccess(String sessionId) {
        // if checked save user data
        if (loginView.stayLoggedIn()) {
            User user = new User(username, password, sessionId);
            loginInteractor.encryptAndSaveUser(user);

        } else {
            loginInteractor.deleteCurrentUser();
        }

        loginView.navigateToMain(username, sessionId);
    }

    @Override
    public void onClickGuest() {
        loginView.navigateToMain(null, null);
    }

    @Override
    public void onDestroy() {
        loginInteractor.dispose();
    }
}
