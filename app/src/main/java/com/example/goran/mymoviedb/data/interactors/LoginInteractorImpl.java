package com.example.goran.mymoviedb.data.interactors;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.goran.mymoviedb.data.local.UserManager;
import com.example.goran.mymoviedb.data.model.auth.RequestToken;
import com.example.goran.mymoviedb.data.model.auth.User;
import com.example.goran.mymoviedb.data.remote.ApiHelper;
import com.example.goran.mymoviedb.di.scope.PerActivity;

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
    public LoginInteractorImpl(ApiHelper apiHelper, UserManager userManager, AppCompatActivity appCompatActivity) {
        super(appCompatActivity);
        this.apiHelper = apiHelper;
        this.userManager = userManager;

    }

    public interface LoginListener {

        void onLoginError();

        void onLoginSuccess(User user);
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

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        // get requestToken, use flatmap to validate it with username & password and create session

        Observable<RequestToken> observable = apiHelper.createRequestToken();

        observable.flatMap(requestToken ->
                apiHelper.validateRequestToken(username, password, requestToken.getRequestToken()))

                .flatMap(tokenValidation ->
                        apiHelper.createSession(tokenValidation.getRequestToken()))

                .flatMap(session -> {
                    user.setSessionId(session.getSessionId());
                    return apiHelper.getAccountId(session.getSessionId());
                })

                // subscribe on background thread, when done,  do stuff on main thread
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(

                        account -> {
                            user.setAccountId(account.getId());
                            listener.onLoginSuccess(user);
                        },

                        throwable -> listener.onLoginError(),
                        () -> Log.i("LOG", "Complete"),
                        disposable -> getCompositeDisposable().add(disposable));
    }
}
