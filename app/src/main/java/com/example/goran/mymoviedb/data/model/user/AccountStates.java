package com.example.goran.mymoviedb.data.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Goran on 13.2.2018..
 */

public class AccountStates {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("favorite")
    @Expose
    private Boolean favorite;

    public Object getRated() {
        return rated;
    }

    public void setRated(Object rated) {
        this.rated = rated;
    }

    @SerializedName("rated")
    @Expose
    private Object rated;
    @SerializedName("watchlist")
    @Expose
    private Boolean watchlist;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public Boolean getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(Boolean watchlist) {
        this.watchlist = watchlist;
    }
}
