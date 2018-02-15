package com.example.goran.mymoviedb.movies.details;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goran.mymoviedb.BaseApplication;
import com.example.goran.mymoviedb.BaseFragment;
import com.example.goran.mymoviedb.R;
import com.example.goran.mymoviedb.data.model.details.MovieDetails;
import com.example.goran.mymoviedb.data.model.list.Movie;
import com.example.goran.mymoviedb.di.MovieDetailsFragmentModule;
import com.example.goran.mymoviedb.movies.adapters.MovieAdapterListener;
import com.example.goran.mymoviedb.movies.adapters.SimpleMovieAdapter;
import com.example.goran.mymoviedb.movies.util.MovieUtils;
import com.example.goran.mymoviedb.movies.util.RatingDialog;
import com.example.goran.mymoviedb.notifications.NotificationUtils;
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

public class MovieDetailsFragment extends BaseFragment implements MovieDetailsContract.View {

    private static final String IMG_BASE_URL = "https://image.tmdb.org/t/p/w300";

    @Inject
    MovieDetailsContract.Presenter presenter;

    private SimpleMovieAdapter adapter;
    private RatingDialog ratingDialog;

    @BindView(R.id.img_movie_poster) SimpleDraweeView imgPoster;
    @BindView(R.id.txt_movie_release) TextView txtReleaseDate;
    @BindView(R.id.txt_movie_desc) TextView txtDesc;
    @BindView(R.id.txt_movie_genre) TextView txtGenre;
    @BindView(R.id.txt_movie_language) TextView txtLanguage;
    @BindView(R.id.txt_movie_rating) TextView txtRating;
    @BindView(R.id.txt_movie_votes) TextView txtVotes;
    @BindView(R.id.txt_movie_status) TextView txtStatus;
    @BindView(R.id.txt_movie_original_title) TextView txtOriginalTitle;
    @BindView(R.id.txt_movie_budget) TextView txtBudget;
    @BindView(R.id.txt_movie_revenue) TextView txtRevenue;
    @BindView(R.id.txt_movie_runtime) TextView txtRuntime;
    @BindView(R.id.btn_movie_rate) ImageButton btnRate;
    @BindView(R.id.btn_movie_favorite) ImageButton btnFavorite;
    @BindView(R.id.recycler_movie_similar) RecyclerView recyclerView;

    private boolean rated;
    private boolean favorite;

    @BindDrawable(R.drawable.ic_favorite_black_24dp) Drawable drwFavorite;
    @BindDrawable(R.drawable.ic_favorite_border_black_24dp) Drawable drwNotFavorite;
    @BindDrawable(R.drawable.ic_star_accent_24dp) Drawable drwRated;
    @BindDrawable(R.drawable.ic_star_border_accent_24dp) Drawable drwNotRated;

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

        (((BaseApplication) getActivity().getApplication()).getAppComponent())
                .movieDetailsFragmentSubcomponent(new MovieDetailsFragmentModule(this))
                .inject(this);

        return inflater.inflate(R.layout.fragment_movie_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        ratingDialog = new RatingDialog();
        ratingDialog.setOnClickListener(dialogView -> {

            if (dialogView.getId() == R.id.btn_dialog_rate) {
                presenter.onClickDlgRate(ratingDialog.getRating());

            } else {
                presenter.onClickDlgClear();
            }
        });

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

        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        presenter.initPresenter(getActivity().getIntent().getIntExtra("movie_id", 0));
        presenter.loadMovieData();
    }


    // user interaction
    @Override
    public void enableUserFeatures() {
        btnFavorite.setVisibility(View.VISIBLE);
        btnRate.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean isFavorite() {
        return favorite;
    }

    @Override
    public boolean isRated() {
        return rated;
    }

    @Override
    public void checkFavorite() {
        btnFavorite.setImageDrawable(drwFavorite);
        favorite = true;
    }

    @Override
    public void uncheckFavorite() {
        btnFavorite.setImageDrawable(drwNotFavorite);
        favorite = false;
    }

    @Override
    public void checkRated() {
        btnRate.setImageDrawable(drwRated);
        rated = true;
    }

    @Override
    public void uncheckRated() {
        btnRate.setImageDrawable(drwNotRated);
        rated = false;
    }

    @Override
    public void showRatingDialog() {
        ratingDialog.show(getActivity().getSupportFragmentManager(), "");
    }

    @Override
    public void dismissRatingDialog() {
        ratingDialog.dismiss();
    }

    @Override
    public void displayUserActionError() {
        Toast.makeText(getActivity(), "Action failed, please try again.", Toast.LENGTH_SHORT).show();
    }

    // movie details
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

    // notifications
    @Override
    public void scheduleNotification(String title, Long releaseDate) {
        NotificationUtils.scheduleJob(title, releaseDate, getActivity());
    }

    @Override
    public void cancelNotification(String title) {
        NotificationUtils.cancelJob(getActivity(), title);
    }

    @Override
    public void showNotification(String message) {
        NotificationUtils.showNotification(getActivity(), message);
    }
}
