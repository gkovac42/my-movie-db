package com.example.goran.mymoviedb.person;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.goran.mymoviedb.BaseActivity;
import com.example.goran.mymoviedb.BaseApplication;
import com.example.goran.mymoviedb.R;
import com.example.goran.mymoviedb.data.model.list.Movie;
import com.example.goran.mymoviedb.data.model.person.Person;
import com.example.goran.mymoviedb.di.PersonActivityModule;
import com.example.goran.mymoviedb.movies.adapters.MovieAdapterListener;
import com.example.goran.mymoviedb.movies.adapters.SimpleMovieAdapter;
import com.example.goran.mymoviedb.movies.details.MovieDetailsActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonActivity extends BaseActivity implements PersonContract.View {

    @BindView(R.id.img_person_profile) SimpleDraweeView imgProfile;
    @BindView(R.id.txt_person_about) TextView txtAbout;
    @BindView(R.id.recycler_person_related) RecyclerView recyclerView;

    private static final String IMG_BASE_URL = "https://image.tmdb.org/t/p/w500";

    @Inject
    PersonContract.Presenter presenter;

    private SimpleMovieAdapter adapter;

    private void initAdapter() {
        adapter = new SimpleMovieAdapter();
        adapter.setListener(new MovieAdapterListener() {
            @Override
            public void onClick(int movieId) {
                presenter.onClickRelatedMovie(movieId);
            }

            @Override
            public void onBottomReached() {

            }
        });

        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);

        (((BaseApplication) getApplication()).getAppComponent())
                .personActivitySubcomponent(new PersonActivityModule(this))
                .inject(this);

        initAdapter();

        recyclerView.setNestedScrollingEnabled(false);

        int personId = getIntent().getIntExtra("person_id", 0);

        presenter.initPresenter(personId);
        presenter.loadPersonData();

    }


    @Override
    public void displayPersonDetails(Person person) {
        getSupportActionBar().setTitle(person.getName());
        imgProfile.setImageURI(Uri.parse(IMG_BASE_URL + person.getProfilePath()));
        txtAbout.setText(person.getBiography());
    }

    @Override
    public void displayRelatedMovies(List<Movie> movies) {
        adapter.setDataSource(movies);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void navigateToRelatedMovie(int movieId) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("movie_id", movieId);
        startActivity(intent);
    }
}
