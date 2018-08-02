package com.example.ropot.movie_1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @GET("/3/movie/{category}")
    Call<MoviesModule> listMovies(@Path("category") String category,
                                  @Query("api_key") String apiKey,
                                  @Query("language") String language,
                                  @Query("page") int page);

}
