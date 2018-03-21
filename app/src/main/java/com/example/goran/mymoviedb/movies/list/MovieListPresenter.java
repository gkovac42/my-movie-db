package com.example.goran.mymoviedb.movies.list;

import com.example.goran.mymoviedb.data.interactors.ListInteractor;
import com.example.goran.mymoviedb.data.interactors.ListInteractorImpl;
import com.example.goran.mymoviedb.data.model.list.Movie;
import com.example.goran.mymoviedb.di.scope.PerFragment;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Goran on 11.1.2018..
 */
@PerFragment
public class MovieListPresenter implements MovieListContract.Presenter, ListInteractorImpl.ListListener {

    private MovieListContract.View view;
    private ListInteractor interactor;

    private int category;
    private int currentPage;

    @Inject
    public MovieListPresenter(MovieListContract.View view, ListInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
        this.interactor.setListener(this);
    }

    @Override
    public void initPresenter(int category) {
        view.showProgressDialog();
        this.category = category;
        this.currentPage = 1;
    }

    @Override
    public void loadMovies() {
        interactor.getMovieList(category, currentPage++);
    }

    @Override
    public void onDataReady(List<Movie> movieList) {
        view.updateAdapter(movieList);
        view.hideProgressDialog();
    }

    @Override
    public void onError() {
        view.hideProgressDialog();
        // TODO display error message
    }

    @Override
    public void onClickMovie(int movieId) {
        view.navigateToMovie(movieId);
    }

    @Override
    public void onBottomReached() {
        if (currentPage <= 5) {
            loadMovies();
        }
    }
}
