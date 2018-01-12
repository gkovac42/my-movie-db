package com.example.goran.mymoviedb.login;

/**
 * Created by Goran on 10.1.2018..
 */

public interface LoginContract {

    interface View {

        boolean stayLoggedIn();

        void navigateToMain(String username, String sessionId);

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
