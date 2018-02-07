package com.example.goran.mymoviedb.data.interactors;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Goran on 23.1.2018..
 */

public abstract class BaseInteractorImpl implements BaseInteractor, LifecycleObserver {

    private CompositeDisposable compositeDisposable;
    private BaseListener listener;

    public BaseInteractorImpl(LifecycleOwner lifecycleOwner) {
        lifecycleOwner.getLifecycle().addObserver(this);
        compositeDisposable = new CompositeDisposable();
    }

    public interface BaseListener {

    }

    public BaseListener getListener() {
        return listener;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    @Override
    public void setListener(BaseListener listener) {
        this.listener = listener;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void cleanUp() {

        if (listener != null) {
            setListener(null);
        }

        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
