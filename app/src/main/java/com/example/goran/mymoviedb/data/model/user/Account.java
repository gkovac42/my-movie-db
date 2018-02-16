package com.example.goran.mymoviedb.data.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Goran on 24.1.2018..
 */

public class Account {

    @SerializedName("id")
    @Expose
    private Integer id;
    @Expose
    private String name;
    @SerializedName("username")
    @Expose
    private String username;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
