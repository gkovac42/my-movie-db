package com.example.goran.mymoviedb.data.model.auth;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Goran on 11.1.2018..
 */

public class User implements Parcelable {

    private String username;
    private String password;
    private String sessionId;
    private int accountId;

    public User() {

    }

    public User(String username, String password, String sessionId, int accountId) {
        this.username = username;
        this.password = password;
        this.sessionId = sessionId;
        this.accountId = accountId;
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

    protected User(Parcel in) {
        username = in.readString();
        password = in.readString();
        sessionId = in.readString();
        accountId = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(sessionId);
        dest.writeInt(accountId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
