package com.example.goran.mymoviedb.movies.home;

/**
 * Created by Goran on 12.1.2018..
 */

public interface HomeContract {

    interface View {

        boolean isGuest();

        void initUser();

        void showPlayingNowFragment();

        void showUpcomingFragment();

        void showPopularFragment();

        void showTopRatedFragment();

        void showSearchFragment();

        void navigateToLogin();
    }

    interface Presenter {

        void getUserData();

        void onClickLoginOut();

        void onClickPlayingNow();

        void onClickUpcoming();

        void onClickPopular();

        void onClickTopRated();

        void onClickSearch();

    }
}
