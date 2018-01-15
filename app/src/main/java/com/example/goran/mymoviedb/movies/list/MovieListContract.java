package com.example.goran.mymoviedb.movies.list;

import com.example.goran.mymoviedb.data.MoviesInteractor;
import com.example.goran.mymoviedb.data.model.ListResponse;
import com.example.goran.mymoviedb.data.model.Movie;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Goran on 11.1.2018..
 */

public interface MovieListContract {

    interface View {

        int getCategory();

        void navigateToMovie(int movieId);

        void addMoviesToAdapter(List<Movie> movies);
    }

    interface Presenter {

        void loadMovies();

        void onClickMovie(int position);

        void onBottomReached();
    }

    interface Model {

        Observable<ListResponse> getNowPlaying(int page);

        Observable<ListResponse> getUpcoming(int page);

        Observable<ListResponse> getPopular(int page);

        Observable<ListResponse> getTopRated(int page);

        void getMovieList(Observable<ListResponse> listObservable, MoviesInteractor.ListListener listener);

    }
}
