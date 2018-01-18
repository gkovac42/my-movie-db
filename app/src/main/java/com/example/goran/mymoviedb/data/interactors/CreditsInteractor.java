package com.example.goran.mymoviedb.data.interactors;

import android.util.Log;

import com.example.goran.mymoviedb.data.model.singlemovie.Crew;
import com.example.goran.mymoviedb.data.model.singlemovie.Credits;
import com.example.goran.mymoviedb.data.remote.ApiHelper;
import com.example.goran.mymoviedb.di.scope.FragmentScope;
import com.example.goran.mymoviedb.movies.details.MovieCreditsContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Goran on 15.1.2018..
 */

@FragmentScope
public class CreditsInteractor implements MovieCreditsContract.Model {

    private ApiHelper apiHelper;
    private CompositeDisposable compositeDisposable;

    @Inject
    public CreditsInteractor(ApiHelper apiHelper) {
        this.apiHelper = apiHelper;
        compositeDisposable = new CompositeDisposable();
    }

    public interface CreditsListener {

        void onDataReady(Credits credits);

        void onError();
    }

    @Override
    public void getCredits(int movieId, CreditsListener listener) {
        Observable<Credits> observable = apiHelper.getMovieCredits(movieId);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        movieCredits -> listener.onDataReady(movieCredits),
                        throwable -> listener.onError(), () -> Log.i("LOG", "Complete"),
                        disposable -> compositeDisposable.add(disposable));
    }

    @Override
    public Crew getDirector(Credits credits) {
        for (Crew crew : credits.getCrew()) {
            if (crew.getJob().equals("Director")) {
                return crew;
            }
        }

        return null;
    }

    @Override
    public Crew getWriter(Credits credits) {
        for (Crew crew : credits.getCrew()) {
            if (crew.getJob().equals("Screenplay")) {
                return crew;
            }
        }

        return null;
    }

    @Override
    public void dispose() {
        compositeDisposable.dispose();
    }
}
