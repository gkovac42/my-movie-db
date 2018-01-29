package com.example.goran.mymoviedb.data.interactors;

import android.arch.lifecycle.LifecycleOwner;
import android.util.Log;

import com.example.goran.mymoviedb.data.local.UserManager;
import com.example.goran.mymoviedb.data.model.auth.RequestToken;
import com.example.goran.mymoviedb.data.model.auth.User;
import com.example.goran.mymoviedb.data.model.list.Movie;
import com.example.goran.mymoviedb.data.remote.ApiHelper;
import com.example.goran.mymoviedb.di.scope.PerActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Goran on 10.1.2018..
 */

@PerActivity
public class LoginInteractorImpl extends BaseInteractorImpl implements LoginInteractor {

    private ApiHelper apiHelper;
    private UserManager userManager;

    @Inject
    public LoginInteractorImpl(ApiHelper apiHelper, UserManager userManager, LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
        this.apiHelper = apiHelper;
        this.userManager = userManager;
    }

    public interface LoginListener {

        void onLoginError();

        void onLoginSuccess(String username, String password);
    }

    @Override
    public void encryptAndSaveUser(User user) {
        String encryptedPassword = userManager.encrypt(user.getPassword());
        user.setPassword(encryptedPassword);
        userManager.saveUser(user);
    }

    @Override
    public User loadAndDecryptUser() {
        User user = userManager.loadUser();
        String decryptedPassword = userManager.decrypt(user.getPassword());
        user.setPassword(decryptedPassword);

        return user;
    }

    @Override
    public void deleteCurrentUser() {
        userManager.deleteUser();
    }

    @Override
    public void initLogin(String username, String password, LoginListener listener) {

        UserManager.setActiveUser(new User());

        // get requestToken, use flatmap to validate it with username & password and create session
        Observable<RequestToken> observable = apiHelper.createRequestToken();

        observable.flatMap(requestToken ->
                apiHelper.validateRequestToken(username, password, requestToken.getRequestToken()))

                .flatMap(tokenValidation ->
                        apiHelper.createSession(tokenValidation.getRequestToken()))
                .flatMap(session -> {
                    UserManager.getActiveUser().setSessionId(session.getSessionId());
                    return apiHelper.getAccountId(session.getSessionId());
                })
                .flatMap(account -> {
                    UserManager.getActiveUser().setAccountId(account.getId());
                    return apiHelper.getFavoriteMovies(1);
                })
                // get user favorite and rated movies
                .flatMap(listResponse -> {
                    UserManager.getActiveUser().setFavoriteMovies(getMovieIds(listResponse.getMovies()));
                    return apiHelper.getRatedMovies(1);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listResponse -> {
                            UserManager.getActiveUser().setRatedMovies(getMovieIds(listResponse.getMovies()));
                            UserManager.getActiveUser().setUsername(username);
                            listener.onLoginSuccess(username, password);
                        },
                        throwable -> listener.onLoginError(),
                        () -> Log.i("LOG", "Complete"),
                        disposable -> getCompositeDisposable().add(disposable));
    }

    private List<Integer> getMovieIds(List<Movie> movies) {
        List<Integer> ids = new ArrayList<>();

        for (Movie movie : movies) {
            ids.add(movie.getId());
        }
        return ids;
    }
}
