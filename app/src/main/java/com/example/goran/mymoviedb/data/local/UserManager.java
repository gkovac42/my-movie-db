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
    private Store store;
    private SharedPreferences sharedPreferences;

    private static User activeUser;

    @Inject
    public UserManager(Store store, Crypto crypto, SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        this.crypto = crypto;
        this.store = store;
        this.key = getKey();
    }

    public static User getActiveUser() {
        return activeUser;
    }

    public static void setActiveUser(User user) {
        activeUser = user;
    }

    private SecretKey getKey() {
        if (!store.hasKey("password_key")) {
            return store.generateSymmetricKey("password_key", null);

        } else {
            return store.getSymmetricKey("password_key", null);
        }
    }

    private String encrypt(String password) {
        return crypto.encrypt(password, key);
    }

    private String decrypt(String password) {
        try {
            return crypto.decrypt(password, key);

        } catch (Exception e) {
            return null;
        }
    }

    public void saveUser(User user) {
        String encryptedPassword = encrypt(user.getPassword());

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", user.getUsername());
        editor.putString("password", encryptedPassword);
        editor.apply();

    }

    public User loadUser() {
        String username = sharedPreferences.getString("username", null);
        String password = sharedPreferences.getString("password", null);
        String decryptedPassword = decrypt(password);

        return new User(username, decryptedPassword);
    }

    public void deleteUser() {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("username");
            editor.remove("password");
            editor.apply();
    }
}
