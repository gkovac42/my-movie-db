package com.example.goran.mymoviedb.data.interactors;

import android.support.v7.app.AppCompatActivity;
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
    public PersonInteractorImpl(ApiHelper apiHelper, AppCompatActivity appCompatActivity) {
        super(appCompatActivity);
        this.apiHelper = apiHelper;
    }

    public interface PersonListener {

        void onDataReady(Person person);

        void onDataReady(List<Movie> movies);

        void onError();
    }

    @Override
    public void getPersonDetails(int personId, PersonListener listener) {
        apiHelper.getPerson(personId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(

                        person -> listener.onDataReady(person),
                        throwable -> listener.onError(),
                        () -> Log.i("LOG", "Complete"),
                        disposable -> getCompositeDisposable().add(disposable));
    }

    @Override
    public void getRelatedMovies(int personId, PersonListener listener) {
        apiHelper.getPersonRelatedMovies(personId)
                .map(listResponse -> listResponse.getMovies())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(

                        movies -> listener.onDataReady(movies),
                        throwable -> listener.onError(),
                        () -> Log.i("LOG", "Complete"),
                        disposable -> getCompositeDisposable().add(disposable));
    }
}
