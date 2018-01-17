package com.example.goran.mymoviedb.data.model.keywords;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Goran on 17.1.2018..
 */

public class KeywordResponse {
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("results")
    @Expose
    private List<Keyword> results = null;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("total_results")
    @Expose
    private Integer totalKeywords;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Keyword> getKeywords() {
        return results;
    }

    public void setKeywords(List<Keyword> results) {
        this.results = results;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalKeywords() {
        return totalKeywords;
    }

    public void setTotalKeywords(Integer totalKeywords) {
        this.totalKeywords = totalKeywords;
    }
}
