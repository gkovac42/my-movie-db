package com.example.goran.mymoviedb.movies.home;

/**
 * Created by Goran on 12.1.2018..
 */

public interface HomeContract {

    interface View {

        void displayActiveUser(String username);

        void hideFavorites();

        void showSelectedMenuItem(int ItemId);

        void navigateToLogin();

        void exit();
    }

    interface Presenter {

        void initView();

        void onClickLoginOut();

        void onClickMenuItem(int itemId);

        void onBackPressed();

    }
}
