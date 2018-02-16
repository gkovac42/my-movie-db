package com.example.goran.mymoviedb.data.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Goran on 9.1.2018..
 */

public class Session {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("session_id")
    @Expose
    private String sessionId;

    public Boolean getSuccess() {
        return success;
    }

    public String getSessionId() {
        return sessionId;
    }

}
