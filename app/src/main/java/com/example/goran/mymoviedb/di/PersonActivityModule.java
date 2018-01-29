package com.example.goran.mymoviedb.di;

import android.arch.lifecycle.LifecycleOwner;

import com.example.goran.mymoviedb.data.interactors.PersonInteractor;
import com.example.goran.mymoviedb.data.interactors.PersonInteractorImpl;
import com.example.goran.mymoviedb.di.scope.PerActivity;
import com.example.goran.mymoviedb.person.PersonActivity;
import com.example.goran.mymoviedb.person.PersonContract;
import com.example.goran.mymoviedb.person.PersonPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Goran on 10.1.2018..
 */

@Module
public class PersonActivityModule {

    private PersonActivity personActivity;

    public PersonActivityModule(PersonActivity activity) {
        this.personActivity = activity;
    }

    @Provides
    @PerActivity
    LifecycleOwner provideLifecycleOwner() {
        return personActivity;
    }

    @Provides
    @PerActivity
    PersonContract.View provideView() {
        return personActivity;
    }

    @Provides
    @PerActivity
    PersonContract.Presenter providePresenter(PersonPresenter presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    PersonInteractor providePersonInteractor(PersonInteractorImpl interactor) {
        return interactor;
    }
}
