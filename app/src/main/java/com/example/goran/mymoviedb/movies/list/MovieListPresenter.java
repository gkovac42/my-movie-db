package com.example.goran.mymoviedb.movies.list;

import com.example.goran.mymoviedb.data.interactors.ListInteractor;
import com.example.goran.mymoviedb.data.model.list.ListResponse;
import com.example.goran.mymoviedb.data.model.list.Movie;
import com.example.goran.mymoviedb.di.scope.FragmentScope;
import com.example.goran.mymoviedb.movies.util.Category;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by Goran on 11.1.2018..
 */
@FragmentScope
public class MovieListPresenter implements MovieListContract.Presenter, ListInteractor.ListListener {

    private MovieListContract.View listView;
    private MovieListContract.Model listInteractor;

    private int currentPage = 1;

    @Inject
    public MovieListPresenter(MovieListContract.View listView, MovieListContract.Model listInteractor) {
        this.listView = listView;
        this.listInteractor = listInteractor;
    }


    @Override
    public void loadMovies() {

        Observable<ListResponse> listObservable;

        switch (listView.getCategory()) {
            case Category.NOW_PLAYING:
                listObservable = listInteractor.getNowPlaying(currentPage++);
                break;
            case Category.UPCOMING:
                listObservable = listInteractor.getUpcoming(currentPage++);
                break;
            case Category.POPULAR:
                listObservable = listInteractor.getPopular(currentPage++);
                break;
            case Category.TOP_RATED:
                listObservable = listInteractor.getTopRated(currentPage++);
                break;
            default:
                listObservable = listInteractor.getNowPlaying(currentPage++);
        }

        listInteractor.getMovieList(listObservable, this);
    }

    @Override
    public void onDataReady(List<Movie> movieList) {
        listView.addMoviesToAdapter(movieList);
    }

    @Override
    public void onError() {

    }

    @Override
    public void onClickMovie(int position) {
        listView.navigateToMovie(position);
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
