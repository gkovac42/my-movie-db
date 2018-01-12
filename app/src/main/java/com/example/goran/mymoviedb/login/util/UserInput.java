package com.example.goran.mymoviedb.login.util;

/**
 * Created by Goran on 10.1.2018..
 */

public class UserInput {

    public static boolean usernameValid(String username) {
        return username.length() > 2 && username.length() < 21;
    }

    public static boolean passwordValid(String password) {
        return password.length() > 7 && password.length() < 21;
    }
}
