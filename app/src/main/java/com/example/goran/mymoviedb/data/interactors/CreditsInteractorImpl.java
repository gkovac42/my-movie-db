package com.example.goran.mymoviedb.data.interactors;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

import com.example.goran.mymoviedb.data.model.details.Credits;
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
    private CreditsListener listener;

    @Inject
    public CreditsInteractorImpl(ApiHelper apiHelper, LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
        this.apiHelper = apiHelper;
    }

    public interface CreditsListener {

        void onCreditsReady(Credits credits);

        void onError();
    }

    @Override
    public void setListener(CreditsListener listener) {
        this.listener = listener;
    }

    @Override
    public void getCredits(int movieId) {
        Observable<Credits> observable = apiHelper.getMovieCredits(movieId);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        movieCredits -> listener.onCreditsReady(movieCredits),
                        throwable -> listener.onError(), () -> Log.i("LOG", "Complete"),
                        disposable -> getCompositeDisposable().add(disposable));
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void removeListener() {
        this.listener = null;
    }
}
