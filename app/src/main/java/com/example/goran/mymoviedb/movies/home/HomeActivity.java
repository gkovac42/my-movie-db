package com.example.goran.mymoviedb.movies.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.goran.mymoviedb.R;
import com.example.goran.mymoviedb.base.BaseApplication;
import com.example.goran.mymoviedb.di.HomeActivityModule;
import com.example.goran.mymoviedb.login.LoginActivity;
import com.example.goran.mymoviedb.movies.list.MovieListFragment;
import com.example.goran.mymoviedb.movies.search.MovieSearchFragment;
import com.example.goran.mymoviedb.movies.util.Category;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity
        implements HomeContract.View, NavigationView.OnNavigationItemSelectedListener {

    private static final String BUNDLE_SELECTED_ITEM = "selected_item";
    private static final int FAVORITES_MENU_POSITION = 4;

    @BindView(R.id.txt_nav_user) TextView txtUser;
    @BindView(R.id.txt_nav_login) TextView txtLogInOut;

    @Inject
    HomeContract.Presenter presenter;

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;

    private int selectedMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        (((BaseApplication) getApplication()).getAppComponent())
                .homeActivitySubcomponent(new HomeActivityModule(this))
                .inject(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initNavDrawer();

        if (savedInstanceState != null) {
            selectedMenuItem = savedInstanceState.getInt(BUNDLE_SELECTED_ITEM);
        }

        presenter.initView(selectedMenuItem);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_SELECTED_ITEM, selectedMenuItem);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            presenter.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        selectedMenuItem = item.getItemId();
        presenter.onClickMenuItem(selectedMenuItem);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void navigateToMenuItem(int itemId) {

        switch (selectedMenuItem) {

            case R.id.nav_playing_now:
                getSupportActionBar().setTitle(R.string.title_playing_now);
                showFragment(MovieListFragment.newInstance(Category.NOW_PLAYING));
                break;

            case R.id.nav_upcoming:
                getSupportActionBar().setTitle(R.string.title_upcoming);
                showFragment(MovieListFragment.newInstance(Category.UPCOMING));
                break;

            case R.id.nav_popular:
                getSupportActionBar().setTitle(R.string.title_popular);
                showFragment(MovieListFragment.newInstance(Category.POPULAR));
                break;

            case R.id.nav_top_rated:
                getSupportActionBar().setTitle(R.string.title_top_rated);
                showFragment(MovieListFragment.newInstance(Category.TOP_RATED));
                break;

            case R.id.nav_search:
                getSupportActionBar().setTitle(R.string.title_search);
                showFragment(MovieSearchFragment.newInstance());
                break;

            case R.id.nav_favorite:
                getSupportActionBar().setTitle(R.string.title_favorite);
                showFragment(MovieListFragment.newInstance(Category.FAVORITE));
                break;
            default:
                getSupportActionBar().setTitle(R.string.title_playing_now);
                showFragment(MovieListFragment.newInstance(Category.NOW_PLAYING));
        }
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_main, fragment);

        ft.commit();
    }

    private void initNavDrawer() {
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ButterKnife.bind(this, navigationView.getHeaderView(0));

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @OnClick(R.id.txt_nav_login)
    public void onClickLoginOut() {
        presenter.onClickLoginOut();
    }

    @Override
    public void displayActiveUser(String username) {
        txtUser.setText(username);
        txtLogInOut.setText(R.string.nav_log_out);
    }

    @Override
    public void hideFavorites() {
        navigationView.getMenu().getItem(FAVORITES_MENU_POSITION).setVisible(false);
    }

    @Override
    public void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void exit() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
