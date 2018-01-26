package com.example.goran.mymoviedb.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Goran on 24.1.2018..
 */

public class FavoriteRequest {

    @SerializedName("media_type")
    @Expose
    private String mediaType;
    @SerializedName("media_id")
    @Expose
    private Integer mediaId;
    @SerializedName("favorite")
    @Expose
    private Boolean favorite;


    public FavoriteRequest(Integer mediaId, Boolean favorite) {
        this.mediaType = "movie";
        this.mediaId = mediaId;
        this.favorite = favorite;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Integer getMediaId() {
        return mediaId;
    }

    public void setMediaId(Integer mediaId) {
        this.mediaId = mediaId;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }
}
