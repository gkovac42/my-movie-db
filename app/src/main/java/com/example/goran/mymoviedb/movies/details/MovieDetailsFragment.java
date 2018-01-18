package com.example.goran.mymoviedb.movies.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.goran.mymoviedb.BaseApplication;
import com.example.goran.mymoviedb.R;
import com.example.goran.mymoviedb.data.model.list.Movie;
import com.example.goran.mymoviedb.data.model.singlemovie.MovieDetails;
import com.example.goran.mymoviedb.di.MovieDetailsFragmentModule;
import com.example.goran.mymoviedb.movies.adapters.SimpleMovieAdapter;
import com.example.goran.mymoviedb.movies.util.MovieUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Goran on 25.12.2017..
 */

public class MovieDetailsFragment extends Fragment implements MovieDetailsContract.View {

    private static final String IMG_BASE_URL = "https://image.tmdb.org/t/p/w600";

    @Inject
    MovieDetailsContract.Presenter presenter;

    @BindView(R.id.content_movie)
    ScrollView content;
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
    @BindView(R.id.txt_movie_popularity)
    TextView txtPopularity;
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
    @BindView(R.id.recycler_movie_similar)
    RecyclerView recyclerView;

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

        content.setVisibility(View.GONE);

        Intent intent = getActivity().getIntent();

        presenter.setMovieId(intent.getIntExtra("movie_id", 0));

        presenter.getMovieDetails();

        presenter.getSimilarMovies();

    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);
    }

    @Override
    public void enableUserFeatures() {
        // TODO - Rate & Favorite buttons VISIBLE/GONE
    }

    @Override
    public void showRatingDialog() {
        // TODO - DialogFragment za rating
    }

    @Override
    public void displayMovieDetails(MovieDetails movieDetails) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(movieDetails.getTitle());

        imgPoster.setImageURI(Uri.parse(IMG_BASE_URL + movieDetails.getPosterPath()));

        txtReleaseDate.setText(MovieUtils.formatDate(movieDetails.getReleaseDate()));

        txtDesc.setText(movieDetails.getOverview());

        txtGenre.setText(MovieUtils.getGenres(movieDetails));

        txtLanguage.setText(movieDetails.getSpokenLanguages().get(0).getName());

        txtRating.setText(String.valueOf(movieDetails.getVoteAverage()));

        txtVotes.setText(String.valueOf(movieDetails.getVoteCount()));

        txtPopularity.setText(String.valueOf(movieDetails.getPopularity()));

        txtBudget.setText(String.valueOf(movieDetails.getBudget()) + "$");

        txtRevenue.setText(String.valueOf(movieDetails.getRevenue() + "$"));

        txtRuntime.setText(String.valueOf(movieDetails.getRuntime()) + " min");

        txtHomepage.setText(movieDetails.getHomepage());

        txtOriginalTitle.setText(movieDetails.getOriginalTitle());

        txtStatus.setText(movieDetails.getStatus());
    }

    @Override
    public void displaySimilarMovies(List<Movie> movieList) {

        SimpleMovieAdapter adapter = new SimpleMovieAdapter(movieList);
        adapter.setListener(position -> {
            presenter.onClickSimilar(movieList.get(position).getId());

        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void navigateToSimilar(int id) {
        Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
        intent.putExtra("movie_id", id);
        startActivity(intent);
    }
}
