package com.example.goran.mymoviedb.data.interactors;

import android.arch.lifecycle.LifecycleOwner;
import android.util.Log;

import com.example.goran.mymoviedb.data.model.list.Movie;
import com.example.goran.mymoviedb.data.model.person.Person;
import com.example.goran.mymoviedb.data.remote.ApiHelper;
import com.example.goran.mymoviedb.di.scope.PerActivity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Goran on 26.1.2018..
 */

@PerActivity
public class PersonInteractorImpl extends BaseInteractorImpl implements PersonInteractor {

    private ApiHelper apiHelper;

    @Inject
    public PersonInteractorImpl(ApiHelper apiHelper, LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
        this.apiHelper = apiHelper;
    }

    public interface PersonListener extends BaseListener {

        void onDataReady(Person person);

        void onDataReady(List<Movie> movies);

        void onError();
    }


    public void getPersonDetails(int personId) {

        apiHelper.getPerson(personId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        person -> ((PersonListener)getListener()).onDataReady(person),
                        throwable -> ((PersonListener)getListener()).onError(),
                        () -> Log.i("LOG", "Complete"),
                        disposable -> getCompositeDisposable().add(disposable));
    }

    public void getRelatedMovies(int personId) {

        apiHelper.getPersonRelatedMovies(personId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        listResponse -> ((PersonListener)getListener()).onDataReady(listResponse.getMovies()),
                        throwable -> ((PersonListener)getListener()).onError(),
                        () -> Log.i("LOG", "Complete"),
                        disposable -> getCompositeDisposable().add(disposable));
    }
}
