package com.example.goran.mymoviedb.movies.home;

/**
 * Created by Goran on 12.1.2018..
 */

public interface HomeContract {

    interface View {

        void displayUsername(String username);

        void hideFavorites();

        void showPlayingNowList();

        void showUpcomingList();

        void showPopularList();

        void showTopRatedList();

        void showSearchFragment();

        void showFavoriteList();

        void navigateToLogin();
    }

    interface Presenter {

        void initView();

        void onClickLoginOut();

        void onClickPlayingNow();

        void onClickUpcoming();

        void onClickPopular();

        void onClickTopRated();

        void onClickSearch();

        void onClickFavorite();

    }
}
