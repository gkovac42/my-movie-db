package com.example.goran.mymoviedb.login;

import com.example.goran.mymoviedb.data.LoginInteractor;
import com.example.goran.mymoviedb.data.model.auth.User;

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

        void onDestroy();

    }

    interface Model {

        void initLogin(String username, String password, LoginInteractor.LoginListener listener);

        void encryptAndSaveUser(User user);

        User loadAndDecryptUser();

        void deleteCurrentUser();

        void dispose();

    }
}
