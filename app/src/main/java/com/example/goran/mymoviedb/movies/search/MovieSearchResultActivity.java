package com.example.goran.mymoviedb.movies.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.goran.mymoviedb.R;
import com.example.goran.mymoviedb.data.model.ListResponse;
import com.example.goran.mymoviedb.data.model.Movie;
import com.example.goran.mymoviedb.data.remote.ApiHelper;
import com.example.goran.mymoviedb.movies.adapters.SimpleMovieAdapter;
import com.example.goran.mymoviedb.movies.details.MovieDetailsActivity;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MovieSearchResultActivity extends AppCompatActivity {

    @Inject
    ApiHelper apiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Fresco.initialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list);

        Intent intent = getIntent();
        CharSequence query = intent.getStringExtra("title_query");


        ArrayList<Movie> movies = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.rw_list);
        SimpleMovieAdapter adapter = new SimpleMovieAdapter(movies);
        adapter.setListener(position -> {

            Intent detailsIntent = new Intent(this, MovieDetailsActivity.class);
            detailsIntent.putExtra("movie_id", movies.get(position).getId());
            startActivity(detailsIntent);

        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        Observable<ListResponse> observable = apiHelper.searchForMovie((String) query, 1);

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listResponse -> {
                    movies.addAll(listResponse.getMovies());
                    adapter.notifyDataSetChanged();
                });
    }
}
