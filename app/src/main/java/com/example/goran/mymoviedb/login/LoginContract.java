package com.example.goran.mymoviedb.login;

import com.example.goran.mymoviedb.data.model.auth.User;

/**
 * Created by Goran on 10.1.2018..
 */

public interface LoginContract {

    interface View {

        boolean stayLoggedIn();

        void navigateToMain(User user);

        void displayUsernameError();

        void displayPasswordError();

        void displayLoginError();

    }

    interface Presenter {

        void checkForCurrentUser();

        void onClickLogin(String username, String password);

        void onClickGuest();

    }
}
