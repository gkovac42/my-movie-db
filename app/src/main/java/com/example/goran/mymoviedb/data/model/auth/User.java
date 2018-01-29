package com.example.goran.mymoviedb.data.model.auth;

import java.util.List;

/**
 * Created by Goran on 11.1.2018..
 */

public class User {

    private String username;
    private String password;
    private String sessionId;
    private int accountId;

    private List<Integer> favoriteMovies;
    private List<Integer> ratedMovies;

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public List<Integer> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void setFavoriteMovies(List<Integer> favoriteMovies) {
        this.favoriteMovies = favoriteMovies;
    }

    public List<Integer> getRatedMovies() {
        return ratedMovies;
    }

    public void setRatedMovies(List<Integer> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    public void addToFavorite(Integer movieId) {
        favoriteMovies.add(movieId);
    }

    public void addToRated(Integer movieId) {
        ratedMovies.add(movieId);
    }

    public void removeFromFavorite(Integer movieId) {
        for (Integer i : favoriteMovies) {
            if (i == movieId) {
                favoriteMovies.remove(i);
            }
        }
    }

    public void removeFromRated(Integer movieId) {
        for (Integer i : ratedMovies) {
            if (i == movieId) {
                ratedMovies.remove(i);
            }
        }
    }
}
