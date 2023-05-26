package com.example.futhub;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

import com.example.futhub.models.FixtureResponse;


public interface FootballApiService {
    @GET("fixtures")
    Call<FixtureResponse> getFixtures(
            @Header("x-rapidapi-key") String apiKey,
            @Query("league") String leagueId,
            @Query("season") String seasonId
    );
}
