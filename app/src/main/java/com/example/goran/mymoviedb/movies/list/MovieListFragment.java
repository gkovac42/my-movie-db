package com.example.goran.mymoviedb.movies.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.goran.mymoviedb.BaseApplication;
import com.example.goran.mymoviedb.R;
import com.example.goran.mymoviedb.data.model.Movie;
import com.example.goran.mymoviedb.di.MovieListFragmentModule;
import com.example.goran.mymoviedb.movies.adapters.AdapterListener;
import com.example.goran.mymoviedb.movies.adapters.LargeMovieAdapter;
import com.example.goran.mymoviedb.movies.details.MovieDetailsActivity;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * Created by Goran on 22.12.2017..
 */

public class MovieListFragment extends Fragment implements MovieListContract.View {

    @Inject
    MovieListContract.Presenter presenter;

    private List<Movie> movieList;
    private LargeMovieAdapter adapter;

    public static MovieListFragment newInstance(int category) {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        args.putInt("category", category);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Fresco.initialize(getActivity());

        (((BaseApplication) getActivity().getApplication()).getAppComponent())
                .movieListFragmentSubcomponent(new MovieListFragmentModule(this))
                .inject(this);

        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i("CATEGORY", String.valueOf(getArguments().getInt("category")));

        movieList = new ArrayList<>();
        adapter = new LargeMovieAdapter(movieList);
        adapter.setListener(new AdapterListener() {
            @Override
            public void onClick(int position) {
                presenter.onClickMovie(position);
            }

            @Override
            public void onBottomReached() {
                presenter.onBottomReached();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        RecyclerView recyclerView = getView().findViewById(R.id.rw_list);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        presenter.loadMovies();

    }

    @Override
    public int getCategory() {
        return getArguments().getInt("category");
    }

    @Override
    public void navigateToMovie(int position) {
        Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
        intent.putExtra("movie_id", movieList.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void addMoviesToAdapter(List<Movie> movies) {
        movieList.addAll(movies);
        adapter.notifyDataSetChanged();
    }

}
