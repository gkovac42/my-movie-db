package com.example.goran.mymoviedb.movies.details;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.goran.mymoviedb.BaseApplication;
import com.example.goran.mymoviedb.R;
import com.example.goran.mymoviedb.data.model.list.Movie;
import com.example.goran.mymoviedb.data.model.singlemovie.MovieDetails;
import com.example.goran.mymoviedb.di.MovieDetailsFragmentModule;
import com.example.goran.mymoviedb.movies.adapters.MovieAdapterListener;
import com.example.goran.mymoviedb.movies.adapters.SimpleMovieAdapter;
import com.example.goran.mymoviedb.movies.util.MovieUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Goran on 25.12.2017..
 */

public class MovieDetailsFragment extends Fragment implements MovieDetailsContract.View {

    private static final String IMG_BASE_URL = "https://image.tmdb.org/t/p/w600";

    @Inject
    MovieDetailsContract.Presenter presenter;

    private SimpleMovieAdapter adapter;

    @BindView(R.id.content_movie)
    ScrollView contentView;
    @BindView(R.id.progress_movie)
    ProgressBar progressBar;
    @BindView(R.id.img_movie_poster)
    SimpleDraweeView imgPoster;
    @BindView(R.id.txt_movie_release)
    TextView txtReleaseDate;
    @BindView(R.id.txt_movie_desc)
    TextView txtDesc;
    @BindView(R.id.txt_movie_genre)
    TextView txtGenre;
    @BindView(R.id.txt_movie_language)
    TextView txtLanguage;
    @BindView(R.id.txt_movie_rating)
    TextView txtRating;
    @BindView(R.id.txt_movie_votes)
    TextView txtVotes;
    @BindView(R.id.txt_movie_status)
    TextView txtStatus;
    @BindView(R.id.txt_movie_original_title)
    TextView txtOriginalTitle;
    @BindView(R.id.txt_movie_budget)
    TextView txtBudget;
    @BindView(R.id.txt_movie_revenue)
    TextView txtRevenue;
    @BindView(R.id.txt_movie_runtime)
    TextView txtRuntime;
    @BindView(R.id.txt_movie_homepage)
    TextView txtHomepage;
    @BindView(R.id.btn_movie_rate)
    ImageButton btnRate;
    @BindView(R.id.btn_movie_favorite)
    ImageButton btnFavorite;
    @BindView(R.id.recycler_movie_similar)
    RecyclerView recyclerView;

    private boolean isRated;
    private boolean isFavorite;

    @BindDrawable(R.drawable.ic_favorite_black_24dp)
    Drawable drwFavorite;
    @BindDrawable(R.drawable.ic_favorite_border_black_24dp)
    Drawable drwNotFavorite;
    @BindDrawable(R.drawable.ic_star_accent_24dp)
    Drawable drwRated;
    @BindDrawable(R.drawable.ic_star_border_accent_24dp)
    Drawable drwNotRated;

    @OnClick(R.id.btn_movie_rate)
    void onClickRate() {
        presenter.onClickRate();
    }

    @OnClick(R.id.btn_movie_favorite)
    void onClickFavorite() {
        presenter.onClickFavorite();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Fresco.initialize(getActivity());

        (((BaseApplication) getActivity().getApplication()).getAppComponent())
                .movieDetailsFragmentSubcomponent(new MovieDetailsFragmentModule(this))
                .inject(this);

        return inflater.inflate(R.layout.fragment_movie_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        contentView.setVisibility(View.GONE);

        adapter = new SimpleMovieAdapter();
        adapter.setListener(new MovieAdapterListener() {
            @Override
            public void onClick(int movieId) {
                presenter.onClickSimilar(movieId);
            }

            @Override
            public void onBottomReached() {

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        presenter.initPresenter(getActivity().getIntent().getIntExtra("movie_id", 0));
        presenter.getMovieDetails();
        presenter.getSimilarMovies();
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
    }

    @Override
    public void enableUserFeatures() {


    }

    @Override
    public void checkFavorite() {
        if (!isFavorite) {
            btnFavorite.setImageDrawable(drwFavorite);
            isFavorite = true;
        } else {
            btnFavorite.setImageDrawable(drwNotFavorite);
            isFavorite = false;
        }
    }

    @Override
    public void checkRated() {
        if (!isRated) {
            btnRate.setImageDrawable(drwRated);
            isRated = true;
        } else {
            btnRate.setImageDrawable(drwNotRated);
            isRated = false;
        }
    }

    @Override
    public void showRatingDialog() {
        // TODO - DialogFragment za rating
    }

    @Override
    public void displayMovieDetails(MovieDetails movieDetails) {

        ((AppCompatActivity) getActivity()).getSupportActionBar()
                .setTitle(MovieUtils.formatTitle(movieDetails.getTitle(), movieDetails.getReleaseDate()));

        imgPoster.setImageURI(Uri.parse(IMG_BASE_URL + movieDetails.getPosterPath()));

        txtReleaseDate.setText(movieDetails.getReleaseDate());

        txtDesc.setText(movieDetails.getOverview());

        txtGenre.setText(MovieUtils.getGenres(movieDetails));

        txtLanguage.setText(movieDetails.getSpokenLanguages().get(0).getName());

        txtRating.setText(String.valueOf(movieDetails.getVoteAverage()));

        txtVotes.setText(String.valueOf(movieDetails.getVoteCount()));

        txtBudget.setText(String.valueOf(movieDetails.getBudget() + "$"));

        txtRevenue.setText(String.valueOf(movieDetails.getRevenue() + "$"));

        txtRuntime.setText(String.valueOf(movieDetails.getRuntime() + " min"));

        txtHomepage.setText(movieDetails.getHomepage());
        txtHomepage.setMovementMethod(LinkMovementMethod.getInstance());

        txtOriginalTitle.setText(movieDetails.getOriginalTitle());

        txtStatus.setText(movieDetails.getStatus());
    }

    @Override
    public void displaySimilarMovies(List<Movie> movieList) {
        adapter.setDataSource(movieList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void navigateToSimilar(int movieId) {
        Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
        intent.putExtra("movie_id", movieId);
        startActivity(intent);
    }
}
