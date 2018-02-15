package com.example.goran.mymoviedb.data.interactors;

import android.arch.lifecycle.LifecycleOwner;

import com.example.goran.mymoviedb.data.model.person.PersonData;
import com.example.goran.mymoviedb.data.remote.ApiHelper;
import com.example.goran.mymoviedb.di.scope.PerActivity;

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

        void onDataReady(PersonData personData);

        void onError();
    }

    @Override
    public void getPersonData(int personId) {

        apiHelper.getPerson(personId)
                .zipWith(apiHelper.getPersonRelatedMovies(personId), (person, listResponse) -> {

                    PersonData personData = new PersonData();
                    personData.setPerson(person);
                    personData.setRelatedMovies(listResponse.getMovies());
                    return personData;
                })

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        personData -> ((PersonListener) getListener()).onDataReady(personData),
                        throwable -> ((PersonListener) getListener()).onError(), () -> {},
                        disposable -> getCompositeDisposable().add(disposable));
    }
}
