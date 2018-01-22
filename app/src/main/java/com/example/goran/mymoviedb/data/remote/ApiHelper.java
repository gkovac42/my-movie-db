package com.example.goran.mymoviedb.data.remote;

import com.example.goran.mymoviedb.data.model.list.ListResponse;
import com.example.goran.mymoviedb.data.model.auth.RequestToken;
import com.example.goran.mymoviedb.data.model.auth.Session;
import com.example.goran.mymoviedb.data.model.auth.TokenValidation;
import com.example.goran.mymoviedb.data.model.keywords.KeywordResponse;
import com.example.goran.mymoviedb.data.model.singlemovie.Credits;
import com.example.goran.mymoviedb.data.model.singlemovie.MovieDetails;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by Goran on 8.11.2017..
 */

@Singleton
public class ApiHelper {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String API_KEY = "2a7ab626f8d7895759f131e9fa35222f";

    private TMDbApiService apiService;

    @Inject
    public ApiHelper(TMDbApiService apiService) {
        this.apiService = apiService;
    }

    // auth

    public Observable<RequestToken> createRequestToken() {
        return apiService.createRequestToken(API_KEY);
    }

    public Observable<TokenValidation> validateRequestToken(String username, String password, String requestToken) {
        return apiService.validateRequestToken(API_KEY, username, password, requestToken);
    }

    public Observable<Session> createSession(String requestToken) {
        return apiService.createSession(API_KEY, requestToken);
    }

    // single movie details & credits

    public Observable<MovieDetails> getMovieDetails(int id) {
        return apiService.getMovieDetails(id, API_KEY);
    }

    public Observable<Credits> getMovieCredits(int id) {
        return apiService.getMovieCredits(id, API_KEY);
    }

    // movie listings

    public Observable<ListResponse> getPopularMovies(int page) {
        return apiService.getPopularMovies(API_KEY, page);
    }

    public Observable<ListResponse> getTopRatedMovies(int page) {
        return apiService.getTopRatedMovies(API_KEY, page);
    }

    public Observable<ListResponse> getNowPlayingMovies(int page) {
        return apiService.getNowPlayingMovies(API_KEY, page);
    }

    public Observable<ListResponse> getUpcomingMovies(int page) {
        return apiService.getUpcomingMovies(API_KEY, page);
    }

    public Observable<ListResponse> getSimilarMovies(int id, int page) {
        return apiService.getSimilarMovies(id, API_KEY, page);
    }

    // search

    public Observable<ListResponse> searchByTitle(String query, int page) {
        return apiService.searchByTitle(API_KEY, query, page);
    }

    public Observable<KeywordResponse> getKeywords(String query) {
        return apiService.getKeywords(API_KEY, query, 1);
    }

    public Observable<ListResponse> searchByKeywordId(int keywordId, int page) {
        return apiService.searchByKeywordId(API_KEY, keywordId, page);
    }

}
