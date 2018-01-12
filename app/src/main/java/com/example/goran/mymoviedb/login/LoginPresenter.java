package com.example.goran.mymoviedb.login;

import com.example.goran.mymoviedb.data.Interactor;
import com.example.goran.mymoviedb.data.model.auth.User;
import com.example.goran.mymoviedb.di.scope.ActivityScope;
import com.example.goran.mymoviedb.login.util.UserInput;

import javax.inject.Inject;

/**
 * Created by Goran on 10.1.2018..
 */

@ActivityScope
public class LoginPresenter implements LoginContract.Presenter {

    private Interactor.Login loginInteractor;
    private LoginContract.View loginView;


    @Inject
    public LoginPresenter(Interactor.Login interactor, LoginContract.View view) {
        this.loginInteractor = interactor;
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
            loginInteractor.initLogin(username, password, new Interactor.LoginListener() {
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

                        // if not checked delete user data if exists
                    } else {
                        loginInteractor.deleteCurrentUser();
                    }
                    // go to main app
                    loginView.navigateToMain(username, sessionId);
                }
            });
        }
    }

    // for guests skip login and go straight to main app
    @Override
    public void onClickGuest() {
        loginView.navigateToMain("guest", "guest");
    }
}
