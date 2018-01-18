package com.example.goran.mymoviedb.data.remote;

import com.example.goran.mymoviedb.data.model.keywords.KeywordResponse;
import com.example.goran.mymoviedb.data.model.singlemovie.Credits;
import com.example.goran.mymoviedb.data.model.list.ListResponse;
import com.example.goran.mymoviedb.data.model.singlemovie.MovieDetails;
import com.example.goran.mymoviedb.data.model.auth.RequestToken;
import com.example.goran.mymoviedb.data.model.auth.Session;
import com.example.goran.mymoviedb.data.model.auth.TokenValidation;

import io.reactivex.Observable;
import retrofit2.http.GET;
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
    Observable<ListResponse> searchByKeyword(@Query("api_key") String apiKey,
                                             @Query("with_keywords") String keyword,
                                             @Query("page") int page);

}
