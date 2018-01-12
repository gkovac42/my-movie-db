package com.example.goran.mymoviedb.data;

import com.example.goran.mymoviedb.data.model.Movie;
import com.example.goran.mymoviedb.data.model.auth.User;

import java.util.List;

/**
 * Created by Goran on 12.1.2018..
 */

public interface Interactor {

    interface Login {

        void initLogin(String username, String password, LoginListener listener);

        void encryptAndSaveUser(User user);

        User loadAndDecryptUser();

        void deleteCurrentUser();

        void disposeObservables();

    }

    interface LoginListener {

        void onLoginError();

        void onLoginSuccess(String sessionId);
    }


    interface Movies {

        void getMovieList(int page, DataListener listener);
    }

    interface DataListener {

        void onDataReady(List<Movie> movieList);

        void onError();
    }
}

