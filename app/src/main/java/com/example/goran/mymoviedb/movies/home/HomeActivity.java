package com.example.goran.mymoviedb.movies.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.goran.mymoviedb.BaseApplication;
import com.example.goran.mymoviedb.R;
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

    @Inject
    HomeContract.Presenter presenter;

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private FragmentManager fragmentManager;

    @BindView(R.id.txt_nav_user)
    TextView txtUser;
    @BindView(R.id.txt_nav_login)
    TextView txtLogInOut;

    @OnClick(R.id.txt_nav_login)
    public void onClickLoginOut() {
        presenter.onClickLoginOut();
    }

    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    private void showFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.content_main, fragment)
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        (((BaseApplication) getApplication()).getAppComponent())
                .homeActivitySubcomponent(new HomeActivityModule(this))
                .inject(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ButterKnife.bind(this, navigationView.getHeaderView(0));

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        fragmentManager = getSupportFragmentManager();

        presenter.getUserData();

        presenter.onClickPlayingNow();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.nav_playing_now:
                presenter.onClickPlayingNow();
                break;
            case R.id.nav_upcoming:
                presenter.onClickUpcoming();
                break;
            case R.id.nav_popular:
                presenter.onClickPopular();
                break;
            case R.id.nav_top_rated:
                presenter.onClickTopRated();
                break;
            case R.id.nav_search:
                presenter.onClickSearch();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean isGuest() {
        String username = getIntent().getStringExtra("username");
        return username.equals("Guest");
    }

    public void initUser() {
        sessionId = getIntent().getStringExtra("session_id");
        txtUser.setText(getIntent().getStringExtra("username"));
        txtLogInOut.setText(R.string.nav_log_out);
    }

    public void showPlayingNowList() {
        getSupportActionBar().setTitle(R.string.nav_playing_now);
        showFragment(MovieListFragment.newInstance(Category.NOW_PLAYING));
    }

    @Override
    public void showUpcomingList() {
        getSupportActionBar().setTitle("MyMovieDb - Upcoming");
        showFragment(MovieListFragment.newInstance(Category.UPCOMING));
    }

    @Override
    public void showPopularList() {
        getSupportActionBar().setTitle("MyMovieDb - Popular");
        showFragment(MovieListFragment.newInstance(Category.POPULAR));
    }

    @Override
    public void showTopRatedList() {
        getSupportActionBar().setTitle("MyMovieDb - Top Rated");
        showFragment(MovieListFragment.newInstance(Category.TOP_RATED));
    }

    public void showSearchFragment() {
        getSupportActionBar().setTitle(R.string.nav_search);
        showFragment(new MovieSearchFragment());
    }

    @Override
    public void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
