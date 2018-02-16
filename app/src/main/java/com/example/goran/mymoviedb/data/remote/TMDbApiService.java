package com.example.goran.mymoviedb.data.remote;

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

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Goran on 13.11.2017..
 */

public interface TMDbApiService {

    // AUTHENTICATION REQUESTS

    @GET("authentication/token/new")
    Observable<RequestToken> createRequestToken(@Query("api_key") String apiKey);

    @GET("authentication/token/validate_with_login")
    Observable<TokenValidation> validateRequestToken(@Query("api_key") String apiKey,
                                                     @Query("username") String username,
                                                     @Query("password") String password,
                                                     @Query("request_token") String requestToken);

    @GET("authentication/session/new")
    Observable<Session> createSession(@Query("api_key") String apiKey, @Query("request_token") String requestToken);


    // MOVIES REQUESTS

    @GET("movie/popular")
    Observable<ListResponse> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/top_rated")
    Observable<ListResponse> getTopRatedMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/now_playing")
    Observable<ListResponse> getNowPlayingMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/upcoming")
    Observable<ListResponse> getUpcomingMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/{movie_id}")
    Observable<MovieDetails> getMovieDetails(@Path("movie_id") int id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/credits")
    Observable<Credits> getMovieCredits(@Path("movie_id") int id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/similar")
    Observable<ListResponse> getSimilarMovies(@Path("movie_id") int id,
                                              @Query("api_key") String apiKey,
                                              @Query("page") int page);

    // SEARCH REQUESTS

    @GET("search/movie")
    Observable<ListResponse> searchByTitle(@Query("api_key") String apiKey,
                                           @Query("query") String query,
                                           @Query("page") int page);

    @GET("search/keyword")
    Observable<KeywordResponse> getKeywords(@Query("api_key") String apiKey,
                                            @Query("query") String query,
                                            @Query("page") int page);

    @GET("discover/movie")
    Observable<ListResponse> searchByKeywordId(@Query("api_key") String apiKey,
                                               @Query("with_keywords") int keywordId,
                                               @Query("page") int page);

    @GET("discover/movie")
    Observable<ListResponse> searchByGenre(@Query("api_key") String apiKey,
                                               @Query("with_genres") String genreId,
                                               @Query("page") int page);

    @GET("genre/movie/list")
    Observable<GenreList> getGenreList(@Query("api_key") String apiKey);




    // PERSON REQUESTS

    @GET("person/{person_id}")
    Observable<Person> getPerson(@Path("person_id") int personId,
                                 @Query("api_key") String apiKey);

    @GET("discover/movie")
    Observable<ListResponse> getPersonRelatedMovies(@Query("api_key") String apiKey,
                                                    @Query("with_people") String personId,
                                                    @Query("sort_by") String sortBy);

    // ACCOUNT REQUESTS

    @GET("account")
    Observable<Account> getAccountId(@Query("api_key") String apiKey, @Query("session_id") String sessionId);

    @GET("account/{account_id}/rated/movies")
    Observable<ListResponse> getRatedMovies(@Path("account_id") int accountId,
                                            @Query("api_key") String apiKey,
                                            @Query("session_id") String sessionId,
                                            @Query("page") int page);

    @GET("account/{account_id}/favorite/movies")
    Observable<ListResponse> getFavoriteMovies(@Path("account_id") int accountId,
                                               @Query("api_key") String apiKey,
                                               @Query("session_id") String sessionId,
                                               @Query("page") int page);

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("account/{account_id}/favorite")
    Observable<FavoriteResponse> postFavoriteMovie(@Path("account_id") int accountId,
                                                   @Query("api_key") String apiKey,
                                                   @Query("session_id") String sessionId,
                                                   @Body FavoriteRequest favoriteRequest);

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("movie/{movie_id}/rating")
    Observable<RateResponse> postMovieRating(@Path("movie_id") int movieId,
                                             @Query("api_key") String apiKey,
                                             @Query("session_id") String sessionId,
                                             @Body RateRequest rateRequest);

    @Headers("Content-Type: application/json;charset=utf-8")
    @DELETE("movie/{movie_id}/rating")
    Observable<RateResponse> deleteMovieRating(@Path("movie_id") int movieId,
                                               @Query("api_key") String apiKey,
                                               @Query("session_id") String sessionId);

    @GET ("movie/{movie_id}/account_states")
    Observable<AccountStates> getAccountStates(@Path("movie_id") int movieId,
                                               @Query("api_key") String apiKey,
                                               @Query("session_id") String sessionId);
}
