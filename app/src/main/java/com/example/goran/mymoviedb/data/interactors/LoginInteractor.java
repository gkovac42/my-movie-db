package com.example.goran.mymoviedb.data.interactors;

import com.example.goran.mymoviedb.data.model.auth.User;

/**
 * Created by Goran on 23.1.2018..
 */

public interface LoginInteractor extends BaseInteractor {

    void initLogin(String username, String password, LoginInteractorImpl.LoginListener listener);

    void encryptAndSaveUser(User user);

    User loadAndDecryptUser();

    void deleteCurrentUser();
}
