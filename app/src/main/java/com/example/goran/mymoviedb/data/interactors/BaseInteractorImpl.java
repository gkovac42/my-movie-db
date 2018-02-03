package com.example.goran.mymoviedb.data.interactors;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Goran on 23.1.2018..
 */

public class BaseInteractorImpl implements BaseInteractor, LifecycleObserver {

    private CompositeDisposable compositeDisposable;

    public BaseInteractorImpl(LifecycleOwner lifecycleOwner) {
        lifecycleOwner.getLifecycle().addObserver(this);
        compositeDisposable = new CompositeDisposable();
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void dispose() {
        Log.i("BASE_INTERACTOR", "Dispose successful");
        compositeDisposable.dispose();
    }
}
