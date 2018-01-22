package com.example.goran.mymoviedb.movies.list;

import com.example.goran.mymoviedb.data.interactors.ListInteractor;
import com.example.goran.mymoviedb.data.model.list.Movie;
import com.example.goran.mymoviedb.di.scope.FragmentScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Goran on 11.1.2018..
 */
@FragmentScope
public class MovieListPresenter implements MovieListContract.Presenter, ListInteractor.ListListener {

    private MovieListContract.View listView;
    private MovieListContract.Model listInteractor;

    private List<Movie> movies;

    private int category;
    private int currentPage;

    @Inject
    public MovieListPresenter(MovieListContract.View listView, MovieListContract.Model listInteractor) {
        this.listView = listView;
        this.listInteractor = listInteractor;
    }

    @Override
    public void initPresenter(int category) {
        this.category = category;
        this.currentPage = 1;
        this.movies = new ArrayList<>();
    }

    @Override
    public void loadMovies() {
        listInteractor.getMovieList(category, currentPage++, this);
    }

    @Override
    public void onDataReady(List<Movie> movieList) {

        movies.addAll(movieList);

        listView.updateAdapter(movies);
    }

    @Override
    public void onError() {

    }

    @Override
    public void onClickMovie(int movieId) {
        listView.navigateToMovie(movieId);
    }

    @Override
    public void onBottomReached() {
        if (currentPage <= 5) {
            loadMovies();
        }
    }

    @Override
    public void onDestroy() {
        listInteractor.dispose();
    }
}
