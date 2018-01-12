package com.example.goran.mymoviedb.movies.list;

import com.example.goran.mymoviedb.data.Interactor;
import com.example.goran.mymoviedb.data.model.Movie;
import com.example.goran.mymoviedb.di.scope.FragmentScope;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Goran on 11.1.2018..
 */
@FragmentScope
public class MovieListPresenter implements MovieListContract.Presenter {

    private MovieListContract.View listView;
    private Interactor.Movies listInteractor;

    private int currentPage = 1;

    @Inject
    public MovieListPresenter(MovieListContract.View listView, Interactor.Movies listInteractor) {
        this.listView = listView;
        this.listInteractor = listInteractor;
    }


    @Override
    public void loadMovies() {

        if (currentPage <= 5) {

            listInteractor.getMovieList(currentPage++, new Interactor.DataListener() {

                @Override
                public void onDataReady(List<Movie> movieList) {
                    listView.addMoviesToAdapter(movieList);
                }

                @Override
                public void onError() {

                }
            });
        }
    }

    @Override
    public void onClickMovie(int position) {
        listView.navigateToMovie(position);
    }
}
