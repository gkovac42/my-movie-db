package com.example.goran.mymoviedb.login;

/**
 * Created by Goran on 10.1.2018..
 */

public interface LoginContract {

    interface View {

        void showProgressDialog();

        void hideProgressDialog();

        boolean stayLoggedIn();

        void navigateToMain();

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
