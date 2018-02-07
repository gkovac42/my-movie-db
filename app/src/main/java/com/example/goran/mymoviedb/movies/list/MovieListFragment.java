package com.example.goran.mymoviedb.movies.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.goran.mymoviedb.BaseApplication;
import com.example.goran.mymoviedb.BaseFragment;
import com.example.goran.mymoviedb.R;
import com.example.goran.mymoviedb.data.model.list.Movie;
import com.example.goran.mymoviedb.di.MovieListFragmentModule;
import com.example.goran.mymoviedb.movies.adapters.LargeMovieAdapter;
import com.example.goran.mymoviedb.movies.adapters.MovieAdapterListener;
import com.example.goran.mymoviedb.movies.details.MovieDetailsActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Goran on 22.12.2017..
 */

public class MovieListFragment extends BaseFragment implements MovieListContract.View {

    @Inject
    MovieListContract.Presenter presenter;

    @BindView(R.id.recycler_list) RecyclerView recyclerView;

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

        (((BaseApplication) getActivity().getApplication()).getAppComponent())
                .movieListFragmentSubcomponent(new MovieListFragmentModule(this))
                .inject(this);

        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, getActivity());

        adapter = new LargeMovieAdapter();
        adapter.setListener(new MovieAdapterListener() {
            @Override
            public void onClick(int movieId) {
                presenter.onClickMovie(movieId);
            }

            @Override
            public void onBottomReached() {
                presenter.onBottomReached();
            }
        });

        recyclerView.setAdapter(adapter);

        presenter.initPresenter(getCategory());
        presenter.loadMovies();

    }

    private int getCategory() {
        return getArguments().getInt("category");
    }

    @Override
    public void updateAdapter(List<Movie> movies) {
        adapter.setDataSource(movies);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void navigateToMovie(int movieId) {
        Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
        intent.putExtra("movie_id", movieId);
        startActivity(intent);
    }
}
