package com.example.goran.mymoviedb.movies.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.goran.mymoviedb.R;
import com.example.goran.mymoviedb.data.model.Genre;
import com.example.goran.mymoviedb.data.model.Movie;
import com.example.goran.mymoviedb.data.model.singlemovie.MovieDetails;
import com.example.goran.mymoviedb.movies.adapters.SimpleMovieAdapter;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Goran on 25.12.2017..
 */

public class MovieDetailsFragment extends Fragment implements MovieDetailsContract.View {

    private static final String IMG_BASE_URL = "https://image.tmdb.org/t/p/w600";

    MovieDetailsContract.Presenter presenter;

    private List<Movie> similarMovies;
    private SimpleMovieAdapter adapter;

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
        return inflater.inflate(R.layout.fragment_movie_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, getView());

        Intent intent = getActivity().getIntent();

        int movieId = intent.getIntExtra("movie_id", 0);

    }

    @Override
    public void displayMovieDetails(MovieDetails movieDetails) {
        imgPoster.setImageURI(Uri.parse(IMG_BASE_URL + movieDetails.getPosterPath()));

        txtReleaseDate.setText("Release Date: " + movieDetails.getReleaseDate());


        txtDesc.setText(movieDetails.getOverview());


        ArrayList<String> genres = new ArrayList<>();

        for (Genre genre : movieDetails.getGenres()) {
            genres.add(genre.getName());
        }

        String g = TextUtils.join(",\n", genres);

        txtGenre.setText(g);

        txtLanguage.setText("Language: " + movieDetails.getSpokenLanguages().get(0).getName());

        txtRating.setText(String.valueOf(movieDetails.getVoteAverage()));

        txtVotes.setText(String.valueOf(movieDetails.getVoteCount()));

        txtPopularity.setText(String.valueOf(movieDetails.getPopularity()));

        txtBudget.setText("Budget: " + String.valueOf(movieDetails.getBudget()) + "$");
        txtRevenue.setText("Revenue: " + String.valueOf(movieDetails.getRevenue() + "$"));
        txtRuntime.setText(String.valueOf(movieDetails.getRuntime()) + " min");
        txtHomepage.setText("Homepage: " + movieDetails.getHomepage());
        txtOriginalTitle.setText("Original Title: " + movieDetails.getOriginalTitle());
        txtStatus.setText("Status: " + movieDetails.getStatus());
    }

    @Override
    public void displaySimilarMovies(List<Movie> movieList) {

        similarMovies = new ArrayList<>();
        similarMovies.addAll(movieList);

        adapter = new SimpleMovieAdapter(similarMovies);
        adapter.notifyDataSetChanged();
        adapter.setListener(position -> {
            Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
            intent.putExtra("movie_id", similarMovies.get(position).getId());
            startActivity(intent);
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
