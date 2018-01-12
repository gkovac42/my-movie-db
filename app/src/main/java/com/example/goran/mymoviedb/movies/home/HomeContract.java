package com.example.goran.mymoviedb.movies.home;

/**
 * Created by Goran on 12.1.2018..
 */

public interface HomeContract {

    interface View {

        boolean isGuest();

        void initUser();

        void showPlayingNow();

        void showSearch();

        void navigateToLogin();
    }

    interface Presenter {

        void getUserData();

        void onClickLoginOut();

        void onClickPlayingNow();

        void onClickSearch();

    }

}
