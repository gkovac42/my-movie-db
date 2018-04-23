package com.example.goran.mymoviedb.data.interactors;

import android.arch.lifecycle.LifecycleOwner;

import com.example.goran.mymoviedb.data.model.details.Credits;
import com.example.goran.mymoviedb.data.remote.ApiHelper;
import com.example.goran.mymoviedb.di.scope.PerFragment;

import javax.inject.Inject;

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

    public interface CreditsListener extends BaseListener {

        void onCreditsReady(Credits credits);

        void onError();
    }


    @Override
    public void getCredits(int movieId) {
        apiHelper.getMovieCredits(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        movieCredits -> ((CreditsListener) getListener()).onCreditsReady(movieCredits),
                        throwable -> ((CreditsListener) getListener()).onError(), () -> {},
                        disposable -> getCompositeDisposable().add(disposable));
    }
}
