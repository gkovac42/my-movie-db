package com.example.goran.mymoviedb.data.interactors;

import android.util.Log;

import com.example.goran.mymoviedb.data.local.UserManager;
import com.example.goran.mymoviedb.data.model.auth.RequestToken;
import com.example.goran.mymoviedb.data.model.auth.User;
import com.example.goran.mymoviedb.data.remote.ApiHelper;
import com.example.goran.mymoviedb.di.scope.ActivityScope;
import com.example.goran.mymoviedb.login.LoginContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Goran on 10.1.2018..
 */

@ActivityScope
public class LoginInteractor implements LoginContract.Model {

    private ApiHelper apiHelper;
    private UserManager userManager;
    private CompositeDisposable compositeDisposable;

    @Inject
    public LoginInteractor(ApiHelper apiHelper, UserManager userManager) {
        this.apiHelper = apiHelper;
        this.userManager = userManager;
        this.compositeDisposable = new CompositeDisposable();
    }

    public interface LoginListener {

        void onLoginError();

        void onLoginSuccess(String sessionId);
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

        // get requestToken, use flatmap to validate it with username & password and create session

        Observable<RequestToken> observable = apiHelper.createRequestToken();

        observable.flatMap(requestToken ->
                apiHelper.validateRequestToken(username, password, requestToken.getRequestToken()))

                .flatMap(tokenValidation ->
                        apiHelper.createSession(tokenValidation.getRequestToken()))

                // subscribe on background thread, when done,  do stuff on main thread

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(

                        // deal with success or error, ready disposable for cleanup

                        session -> listener.onLoginSuccess(session.getSessionId()),

                        throwable -> listener.onLoginError(),

                        () -> Log.i("LOG", "Login successful"),

                        disposable -> compositeDisposable.add(disposable));
    }

    @Override
    public void dispose() {
        compositeDisposable.dispose();
    }


}
