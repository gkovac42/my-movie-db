package com.example.goran.mymoviedb.data.interactors;

import android.arch.lifecycle.LifecycleOwner;
import android.util.Log;

import com.example.goran.mymoviedb.data.local.UserManager;
import com.example.goran.mymoviedb.data.model.auth.User;
import com.example.goran.mymoviedb.data.remote.ApiHelper;
import com.example.goran.mymoviedb.di.scope.PerActivity;

import javax.inject.Inject;

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

    public interface LoginListener extends BaseListener {

        void onLoginError();

        void onLoginSuccess(String username, String password);
    }


    @Override
    public void saveUser(User user) {
        userManager.saveUser(user);
    }

    @Override
    public User loadUser() {
        return userManager.loadUser();
    }

    @Override
    public void deleteSavedUser() {
        userManager.deleteUser();
    }

    @Override
    public void deleteActiveUser() {
        UserManager.setActiveUser(null);
    }

    @Override
    public User getActiveUser() {
        return UserManager.getActiveUser();
    }

    @Override
    public void initLogin(String username, String password) {

        UserManager.setActiveUser(new User());

        // get requestToken, use flatmap to validate it with username & password and create session
        apiHelper.createRequestToken()
                .flatMap(requestToken ->
                        apiHelper.validateRequestToken(username, password, requestToken.getRequestToken()))

                .flatMap(tokenValidation ->
                        apiHelper.createSession(tokenValidation.getRequestToken()))
                .flatMap(session -> {
                    UserManager.getActiveUser().setSessionId(session.getSessionId());
                    return apiHelper.getAccountId(session.getSessionId());
                })

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(account -> {
                            UserManager.getActiveUser().setAccountId(account.getId());
                            UserManager.getActiveUser().setUsername(username);
                            ((LoginListener) getListener()).onLoginSuccess(username, password);
                        },
                        throwable -> ((LoginListener) getListener()).onLoginError(),
                        () -> Log.i("LOG", "Complete"),
                        disposable -> getCompositeDisposable().add(disposable));
    }
}
