package com.example.futhub.manager;

import android.content.Context;
import android.util.Log;

import com.example.futhub.ResponseListener;
import com.example.futhub.models.FixtureResponse;

import kotlin.ParameterName;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;


public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api-football-v1.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build())
            .build();



    public RequestManager(Context context) {
        this.context = context;
    }

    public void getFixture(ResponseListener listener, String application, String apiKey, String host, String next, String leagueId){
        CallFixture callFixture = retrofit.create(CallFixture.class);
        Call<FixtureResponse> call = callFixture.callFixtures(application, apiKey, host, leagueId, next);
        call.enqueue(new Callback<FixtureResponse>() {
            @Override
            public void onResponse(Call<FixtureResponse> call, Response<FixtureResponse> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }

                listener.didFetch(response.body(), response.message());
            }


            @Override
            public void onFailure(Call<FixtureResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }


    private interface CallFixture{
        @GET("v3/fixtures")
        Call<FixtureResponse> callFixtures (
                @Header("Accept") String application,
                @Header("X-RapidAPI-Key") String apiKey,
                @Header("X-RapidAPI-Host") String host,
                @Query("league") String leagueId,
                @Query("next") String next
        );
    }
}
