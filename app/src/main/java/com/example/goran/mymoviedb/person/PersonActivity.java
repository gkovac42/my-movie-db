package com.example.goran.mymoviedb.person;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.goran.mymoviedb.BaseApplication;
import com.example.goran.mymoviedb.R;
import com.example.goran.mymoviedb.data.model.list.Movie;
import com.example.goran.mymoviedb.data.model.person.Person;
import com.example.goran.mymoviedb.di.PersonActivityModule;
import com.example.goran.mymoviedb.movies.adapters.MovieAdapterListener;
import com.example.goran.mymoviedb.movies.adapters.SimpleMovieAdapter;
import com.example.goran.mymoviedb.movies.details.MovieDetailsActivity;
import com.example.goran.mymoviedb.movies.util.ProgressDialog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonActivity extends AppCompatActivity implements PersonContract.View {

    private static final String IMG_BASE_URL = "https://image.tmdb.org/t/p/w300";

    @Inject
    PersonContract.Presenter presenter;

    private ProgressDialog progressDialog;
    private SimpleMovieAdapter adapter;

    @BindView(R.id.img_person_profile) SimpleDraweeView imgProfile;
    @BindView(R.id.txt_person_about) TextView txtAbout;
    @BindView(R.id.recycler_person_related) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Fresco.initialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);

        (((BaseApplication) getApplication()).getAppComponent())
                .personActivitySubcomponent(new PersonActivityModule(this))
                .inject(this);

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
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        presenter.initPresenter(getIntent().getIntExtra("person_id", 0));

        presenter.loadPersonDetails();

    }

    @Override
    public void showProgressDialog() {
        progressDialog = new ProgressDialog();
        progressDialog.show(getSupportFragmentManager(), "");
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.dismiss();
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
