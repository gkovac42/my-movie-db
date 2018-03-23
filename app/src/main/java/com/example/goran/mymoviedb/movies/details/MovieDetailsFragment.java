package com.example.goran.mymoviedb.movies.details;

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

import com.example.goran.mymoviedb.base.BaseApplication;
import com.example.goran.mymoviedb.base.BaseFragment;
import com.example.goran.mymoviedb.R;
import com.example.goran.mymoviedb.data.model.details.MovieDetails;
import com.example.goran.mymoviedb.data.model.list.Movie;
import com.example.goran.mymoviedb.di.MovieDetailsFragmentModule;
import com.example.goran.mymoviedb.movies.adapters.MovieAdapterListener;
import com.example.goran.mymoviedb.movies.adapters.SimpleMovieAdapter;
import com.example.goran.mymoviedb.Urls;
import com.example.goran.mymoviedb.Constants;
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

    @BindDrawable(R.drawable.ic_favorite_black_24dp) Drawable drwFavorite;
    @BindDrawable(R.drawable.ic_favorite_border_black_24dp) Drawable drwNotFavorite;
    @BindDrawable(R.drawable.ic_star_accent_24dp) Drawable drwRated;
    @BindDrawable(R.drawable.ic_star_border_accent_24dp) Drawable drwNotRated;

    @Inject
    MovieDetailsContract.Presenter presenter;

    private SimpleMovieAdapter adapter;
    private RatingDialog ratingDialog;


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

        initAdapter();

        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        int movieId = getActivity().getIntent().getIntExtra(Constants.EXTRA_MOVIE_ID, 0);

        presenter.initPresenter(movieId);
        presenter.loadMovieData();

        initRatingDialog();
    }


    private void initAdapter() {
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
    }

    // user interaction

    private void initRatingDialog() {
        ratingDialog = new RatingDialog();

        ratingDialog.setOnClickListener(dialogView -> {

            if (dialogView.getId() == R.id.btn_dialog_rate) {
                presenter.onClickDlgRate(ratingDialog.getRating());

            } else {
                presenter.onClickDlgCancel();
            }
        });
    }

    @Override
    public void enableUserFeatures() {
        btnFavorite.setVisibility(View.VISIBLE);
        btnRate.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_movie_rate)
    void onClickRate() {
        presenter.onClickRate();
    }

    @OnClick(R.id.btn_movie_favorite)
    void onClickFavorite() {
        presenter.onClickFavorite();
    }

    @Override
    public void checkFavorite() {
        btnFavorite.setImageDrawable(drwFavorite);
    }

    @Override
    public void uncheckFavorite() {
        btnFavorite.setImageDrawable(drwNotFavorite);
    }

    @Override
    public void checkRated() {
        btnRate.setImageDrawable(drwRated);
    }

    @Override
    public void uncheckRated() {
        btnRate.setImageDrawable(drwNotRated);
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
        Toast.makeText(getActivity(),
                R.string.user_action_error,
                Toast.LENGTH_SHORT).show();
    }

    // movie details
    @Override
    public void displayMovieDetails(MovieDetails details) {

        ((AppCompatActivity) getActivity()).getSupportActionBar()
                .setTitle(details.getTitle());

        imgPoster.setImageURI(Uri.parse(Urls.IMG_NORMAL + details.getPosterPath()));

        txtReleaseDate.setText(details.getReleaseDate());
        txtDesc.setText(details.getOverview());
        txtGenre.setText(details.getGenres());

        txtLanguage.setText(details.getSpokenLanguage());
        txtRating.setText(String.valueOf(details.getVoteAverage()));
        txtVotes.setText(String.valueOf(details.getVoteCount()));

        txtBudget.setText(details.getBudget());
        txtRevenue.setText(details.getRevenue());
        txtRuntime.setText(details.getRuntime());

        txtOriginalTitle.setText(details.getOriginalTitle());
        txtStatus.setText(details.getStatus());
    }

    @Override
    public void displaySimilarMovies(List<Movie> movieList) {
        adapter.addMovies(movieList);
    }

    @Override
    public void navigateToSimilar(int movieId) {
        startActivity(MovieDetailsActivity.newIntent(getActivity(), movieId));
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
