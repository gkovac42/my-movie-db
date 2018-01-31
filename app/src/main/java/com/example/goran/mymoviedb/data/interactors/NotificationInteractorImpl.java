package com.example.goran.mymoviedb.data.interactors;

import android.text.format.DateUtils;

import com.example.goran.mymoviedb.data.local.UserManager;
import com.example.goran.mymoviedb.data.model.auth.RequestToken;
import com.example.goran.mymoviedb.data.model.auth.User;
import com.example.goran.mymoviedb.data.model.list.Movie;
import com.example.goran.mymoviedb.data.remote.ApiHelper;
import com.example.goran.mymoviedb.di.scope.PerActivity;
import com.example.goran.mymoviedb.movies.util.MovieUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Goran on 30.1.2018..
 */

@PerActivity
public class NotificationInteractorImpl implements NotificationInteractor {

    private ApiHelper apiHelper;
    private UserManager userManager;
    private CompositeDisposable compositeDisposable;
    private Listener listener;

    @Inject
    public NotificationInteractorImpl(ApiHelper apiHelper, UserManager userManager) {
        this.apiHelper = apiHelper;
        this.userManager = userManager;
        compositeDisposable = new CompositeDisposable();
    }

    public interface Listener {

        void onDateMatch(Movie movie);

        void onError();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void loginAndCheckData() {

        User user = userManager.loadUser();

        // get requestToken, use flatmap to validate it with username & password and create session
        Observable<RequestToken> observable = apiHelper.createRequestToken();

        observable.flatMap(requestToken ->
                apiHelper.validateRequestToken(user.getUsername(), user.getPassword(), requestToken.getRequestToken()))

                .flatMap(tokenValidation ->
                        apiHelper.createSession(tokenValidation.getRequestToken()))
                .flatMap(session -> {
                    user.setSessionId(session.getSessionId());
                    return apiHelper.getAccountId(session.getSessionId());
                })
                .flatMap(account -> {
                    user.setAccountId(account.getId());
                    return apiHelper.getFavoriteMovies(user.getAccountId(), user.getSessionId(), 1);
                })
                .flatMap(listResponse -> Observable.fromIterable(listResponse.getMovies()))

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movie -> {

                            Long releaseDate = MovieUtils.StringToDate(movie.getReleaseDate());

                            if (DateUtils.isToday(releaseDate)) {

                                listener.onDateMatch(movie);
                            }
                        },

                        throwable -> listener.onError(),

                        () -> dispose(),

                        disposable -> compositeDisposable.add(disposable));
    }

    private void dispose() {
        setListener(null);
        compositeDisposable.dispose();
    }
}
