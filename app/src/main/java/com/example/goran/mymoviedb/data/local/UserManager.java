package com.example.goran.mymoviedb.data.local;

import android.content.SharedPreferences;

import com.example.goran.mymoviedb.data.model.auth.User;
import com.example.goran.mymoviedb.di.scope.ActivityScope;
import com.yakivmospan.scytale.Crypto;
import com.yakivmospan.scytale.Store;

import javax.crypto.SecretKey;
import javax.inject.Inject;

/**
 * Created by Goran on 11.1.2018..
 */

@ActivityScope
public class UserManager {

    private SecretKey key;
    private Crypto crypto;
    private SharedPreferences sharedPreferences;

    @Inject
    public UserManager(Store store, Crypto crypto, SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        this.crypto = crypto;

        if (!store.hasKey("password_key")) {
            key = store.generateSymmetricKey("password_key", null);
        } else {
            key = store.getSymmetricKey("password_key", null);
        }

    }

    public String encrypt(String password) {
        return crypto.encrypt(password, key);
    }

    public String decrypt(String password) {
        try {
            return crypto.decrypt(password, key);
        } catch (Exception e) {
            return null;
        }
    }

    public void saveUser(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("currentUsername", user.getUsername());
        editor.putString("currentPassword", user.getPassword());
        editor.putString("currentSession", user.getSessionId());
        editor.apply();
    }

    public User loadUser() {
        String username = sharedPreferences.getString("currentUsername", null);
        String password = sharedPreferences.getString("currentPassword", null);
        String sessionId = sharedPreferences.getString("currentSession", null);

        return new User(username, password, sessionId);
    }

    public void deleteUser() {
        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("currentUsername");
            editor.remove("currentPassword");
            editor.remove("currentSession");
            editor.apply();
        } catch (Exception ignored) {
        }
    }
}
