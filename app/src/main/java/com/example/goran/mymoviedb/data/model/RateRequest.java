package com.example.goran.mymoviedb.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Goran on 25.1.2018..
 */

public class RateRequest {

    @SerializedName("value")
    @Expose
    private Double value;

    public RateRequest(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
