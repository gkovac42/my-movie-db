package com.example.goran.mymoviedb.data.local;

import android.content.SharedPreferences;

import com.example.goran.mymoviedb.data.model.auth.User;
import com.yakivmospan.scytale.Crypto;
import com.yakivmospan.scytale.Store;

import javax.crypto.SecretKey;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Goran on 11.1.2018..
 */

@Singleton
public class UserManager {

    private SecretKey key;
    private Crypto crypto;
    private SharedPreferences sharedPreferences;

    private static User activeUser;

    @Inject
    public UserManager(Store store, Crypto crypto, SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        this.crypto = crypto;
        activeUser = new User();

        if (!store.hasKey("password_key")) {
            key = store.generateSymmetricKey("password_key", null);
        } else {
            key = store.getSymmetricKey("password_key", null);
        }

    }

    public static User getActiveUser() {
        return activeUser;
    }

    public static void setActiveUser(User user) {
        activeUser = user;
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
        editor.apply();
    }

    public User loadUser() {
        String username = sharedPreferences.getString("currentUsername", null);
        String password = sharedPreferences.getString("currentPassword", null);

        return new User(username, password);
    }

    public void deleteUser() {
        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("currentUsername");
            editor.remove("currentPassword");
            editor.apply();
        } catch (Exception ignored) {
        }
    }
}
