package com.example.goran.mymoviedb.data.interactors;

import android.arch.lifecycle.LifecycleOwner;
import android.util.Log;

import com.example.goran.mymoviedb.data.model.details.Credits;
import com.example.goran.mymoviedb.data.model.details.Crew;
import com.example.goran.mymoviedb.data.remote.ApiHelper;
import com.example.goran.mymoviedb.di.scope.PerFragment;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Goran on 15.1.2018..
 */

@PerFragment
public class CreditsInteractorImpl extends BaseInteractorImpl implements CreditsInteractor {

    private ApiHelper apiHelper;

    @Inject
    public CreditsInteractorImpl(ApiHelper apiHelper, LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
        this.apiHelper = apiHelper;
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
                        disposable -> getCompositeDisposable().add(disposable));
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
}
