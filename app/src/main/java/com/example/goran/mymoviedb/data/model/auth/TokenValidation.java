package com.example.goran.mymoviedb.data.model.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Goran on 9.1.2018..
 */

public class TokenValidation {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("request_token")
    @Expose
    private String requestToken;

    public Boolean getSuccess() {
        return success;
    }

    public String getRequestToken() {
        return requestToken;
    }

}

