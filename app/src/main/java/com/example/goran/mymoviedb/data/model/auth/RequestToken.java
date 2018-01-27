package com.example.goran.mymoviedb.data.model.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Goran on 9.1.2018..
 */

public class RequestToken {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("expires_at")
    @Expose
    private String expiresAt;
    @SerializedName("request_token")
    @Expose
    private String requestToken;

    public Boolean getSuccess() {
        return success;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public String getRequestToken() {
        return requestToken;
    }

}
