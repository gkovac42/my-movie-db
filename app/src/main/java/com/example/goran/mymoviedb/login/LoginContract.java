package com.example.goran.mymoviedb.login;

import com.example.goran.mymoviedb.BaseView;

/**
 * Created by Goran on 10.1.2018..
 */

public interface LoginContract {

    interface View extends BaseView {

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
