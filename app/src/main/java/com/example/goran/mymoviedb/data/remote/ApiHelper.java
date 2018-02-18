package com.example.goran.mymoviedb.data.remote;

import com.example.goran.mymoviedb.data.local.UserManager;
import com.example.goran.mymoviedb.data.model.user.FavoriteRequest;
import com.example.goran.mymoviedb.data.model.user.FavoriteResponse;
import com.example.goran.mymoviedb.data.model.user.RateRequest;
import com.example.goran.mymoviedb.data.model.user.RateResponse;
import com.example.goran.mymoviedb.data.model.user.Account;
import com.example.goran.mymoviedb.data.model.user.AccountStates;
import com.example.goran.mymoviedb.data.model.user.RequestToken;
import com.example.goran.mymoviedb.data.model.user.Session;
import com.example.goran.mymoviedb.data.model.user.TokenValidation;
import com.example.goran.mymoviedb.data.model.details.Credits;
import com.example.goran.mymoviedb.data.model.details.GenreList;
import com.example.goran.mymoviedb.data.model.details.MovieDetails;
import com.example.goran.mymoviedb.data.model.keywords.KeywordResponse;
import com.example.goran.mymoviedb.data.model.list.ListResponse;
import com.example.goran.mymoviedb.data.model.person.Person;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by Goran on 8.11.2017..
 */

@Singleton
public class ApiHelper {

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

    public Observable<Account> getAccountId(String sessionId) {
        return apiService.getAccountId(API_KEY, sessionId);
    }

    // single movie details & credits

    public Observable<MovieDetails> getMovieDetails(int movieId) {
        return apiService.getMovieDetails(movieId, API_KEY);
    }

    public Observable<Credits> getMovieCredits(int movieId) {
        return apiService.getMovieCredits(movieId, API_KEY);
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

    public Observable<ListResponse> getSimilarMovies(int movieId, int page) {
        return apiService.getSimilarMovies(movieId, API_KEY, page);
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

    public Observable<GenreList> getGenreList() {
        return apiService.getGenreList(API_KEY);
    }

    public Observable<ListResponse> searchByGenre(String genreId, int page) {
        return apiService.searchByGenre(API_KEY, genreId, page);
    }

    // person

    public Observable<Person> getPerson(int personId) {
        return apiService.getPerson(personId, API_KEY);
    }

    public Observable<ListResponse> getPersonRelatedMovies(int personId) {
        return apiService.getPersonRelatedMovies(API_KEY, String.valueOf(personId), "popularity.desc");
    }

    // account

    public Observable<ListResponse> getFavoriteMovies(int page) {
        return apiService.getFavoriteMovies(UserManager.getActiveUser().getAccountId(),
                API_KEY, UserManager.getActiveUser().getSessionId(), page);
    }

    public Observable<ListResponse> getFavoriteMovies(int accountId, String sessionId, int page) {
        return apiService.getFavoriteMovies(accountId, API_KEY, sessionId, page);
    }

    public Observable<ListResponse> getRatedMovies(int page) {
        return apiService.getRatedMovies(UserManager.getActiveUser().getAccountId(), API_KEY,
                UserManager.getActiveUser().getSessionId(), page);
    }

    public Observable<FavoriteResponse> postFavoriteMovie(FavoriteRequest favoriteRequest) {
        return apiService.postFavoriteMovie(UserManager.getActiveUser().getAccountId(),
                API_KEY, UserManager.getActiveUser().getSessionId(), favoriteRequest);
    }

    public Observable<RateResponse> postMovieRating(int movieId, RateRequest rateRequest) {
        return apiService.postMovieRating(movieId, API_KEY, UserManager.getActiveUser().getSessionId(), rateRequest);
    }

    public Observable<RateResponse> deleteMovieRating(int movieId) {
        return apiService.deleteMovieRating(movieId, API_KEY, UserManager.getActiveUser().getSessionId());
    }

    public Observable<AccountStates> getAccountStates(int movieId) {
        return apiService.getAccountStates(movieId, API_KEY, UserManager.getActiveUser().getSessionId());
    }
}
