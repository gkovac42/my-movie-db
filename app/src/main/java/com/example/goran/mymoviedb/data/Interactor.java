package com.example.goran.mymoviedb.data;

import com.example.goran.mymoviedb.data.model.ListResponse;
import com.example.goran.mymoviedb.data.model.Movie;
import com.example.goran.mymoviedb.data.model.auth.User;
import com.example.goran.mymoviedb.data.model.singlemovie.MovieDetails;

import java.util.List;

import io.reactivex.Observable;

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

        Observable<ListResponse> getNowPlaying(int page);

        Observable<ListResponse> getUpcoming(int page);

        Observable<ListResponse> getPopular(int page);

        Observable<ListResponse> getTopRated(int page);

        Observable<ListResponse> getSimilar(int id, int page);

        void getMovieList(Observable<ListResponse> listObservable, ListListener listener);
    }

    interface ListListener {

        void onDataReady(List<Movie> movieList);

        void onError();
    }

    interface DetailsListener {

        void onDataReady(MovieDetails movieDetails);

        void onError();
    }
}

