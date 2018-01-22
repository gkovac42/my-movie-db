package com.example.goran.mymoviedb.movies.home;

/**
 * Created by Goran on 12.1.2018..
 */

public interface HomeContract {

    interface View {

        void displayUsername(String username);

        void showPlayingNowList();

        void showUpcomingList();

        void showPopularList();

        void showTopRatedList();

        void showSearchFragment();

        void navigateToLogin();
    }

    interface Presenter {

        void initPresenter(String username, String sessionId);

        void onClickLoginOut();

        void onClickPlayingNow();

        void onClickUpcoming();

        void onClickPopular();

        void onClickTopRated();

        void onClickSearch();

    }
}
