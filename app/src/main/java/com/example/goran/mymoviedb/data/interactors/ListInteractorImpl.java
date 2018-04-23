package com.example.goran.mymoviedb.data.interactors;

import android.arch.lifecycle.LifecycleOwner;

import com.example.goran.mymoviedb.data.model.list.ListResponse;
import com.example.goran.mymoviedb.data.model.list.Movie;
import com.example.goran.mymoviedb.data.remote.ApiHelper;
import com.example.goran.mymoviedb.di.scope.PerFragment;
import com.example.goran.mymoviedb.movies.util.Category;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Goran on 11.1.2018..
 */

@PerFragment
public class ListInteractorImpl extends BaseInteractorImpl implements ListInteractor {

    private ApiHelper apiHelper;

    @Inject
    public ListInteractorImpl(ApiHelper apiHelper, LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
        this.apiHelper = apiHelper;
    }

    public interface ListListener extends BaseListener {

        void onDataReady(List<Movie> movieList);

        void onError();
    }

    private Observable<ListResponse> getNowPlaying(int page) {
        return apiHelper.getNowPlayingMovies(page);
    }

    private Observable<ListResponse> getUpcoming(int page) {
        return apiHelper.getUpcomingMovies(page);
    }

    private Observable<ListResponse> getPopular(int page) {
        return apiHelper.getPopularMovies(page);
    }

    private Observable<ListResponse> getTopRated(int page) {
        return apiHelper.getTopRatedMovies(page);
    }

    private Observable<ListResponse> getFavorite(int page) {
        return apiHelper.getFavoriteMovies(page);
    }

    @Override
    public void getMovieList(int category, int page) {
        Observable<ListResponse> listObservable;

        switch (category) {
            case Category.NOW_PLAYING:
                listObservable = getNowPlaying(page);
                break;
            case Category.UPCOMING:
                listObservable = getUpcoming(page);
                break;
            case Category.POPULAR:
                listObservable = getPopular(page);
                break;
            case Category.TOP_RATED:
                listObservable = getTopRated(page);
                break;
            case Category.FAVORITE:
                listObservable = getFavorite(page);
                break;
            default:
                listObservable = getNowPlaying(page);
        }

        listObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        listResponse -> ((ListListener) getListener()).onDataReady(listResponse.getMovies()),
                        throwable -> ((ListListener) getListener()).onError(), () -> {},
                        disposable -> getCompositeDisposable().add(disposable));
    }
}
