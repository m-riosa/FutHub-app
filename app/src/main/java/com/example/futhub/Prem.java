package com.example.futhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.example.futhub.adapter.FixtureAdapter;
import com.example.futhub.manager.RequestManager;
import com.example.futhub.models.FixtureResponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Prem extends AppCompatActivity {
    Switch lightSwitch;
    boolean nightMODE;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    RecyclerView recyclerView;
    ProgressDialog dialog;
    RequestManager manager;

    String leagueId = "39"; // English Premier League
    String next = "50";
    String application = "application/json";
    String host = "api-football-v1.p.rapidapi.com";
    String apiKey = "####";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prem);

        getSupportActionBar().hide();

        lightSwitch = findViewById(R.id.lightSwitch);
        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMODE = sharedPreferences.getBoolean("night", false);

        if (nightMODE){
            lightSwitch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        lightSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if(nightMODE){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night", false);
                } else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night", true);
                }
                editor.apply();
            }
        });

        // get id from xml file for recycler view
        recyclerView = findViewById(R.id.recycler_fixture);

        // set up progress dialog
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading...");

        // pass call to API with league id for Premier League
        manager = new RequestManager(this);
        manager.getFixture(listener, application, apiKey, host, next, leagueId);
        dialog.show();
    }

    private final ResponseListener listener = new ResponseListener() {
        @Override
        public void didFetch(FixtureResponse response, String message) {
            dialog.dismiss();

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(Prem.this,
                    LinearLayoutManager.VERTICAL, false));
            FixtureAdapter adapter = new FixtureAdapter(Prem.this, response.response);
            recyclerView.setAdapter(adapter);
            if (response.response.size() == 0){
                Toast.makeText(Prem.this, "No future games found", Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void didError(String message) {
            dialog.dismiss();
            Toast.makeText(Prem.this, message, Toast.LENGTH_SHORT).show();
        }


    };


    // old button code, need to update to fit in recycler view when api is implemented correctly
//    public void openFubo(View v){
//        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("tv.fubo.mobile");
//
//        if(launchIntent != null){
//            this.startActivity(launchIntent);
//        } else{
//            Toast.makeText(Prem.this, "App not detected, redirecting to website", Toast.LENGTH_LONG).show();
//            Uri espn = Uri.parse("https://www.fubo.tv/sports/199/soccer");
//            Intent webIntent = new Intent(Intent.ACTION_VIEW, espn);
//            startActivity(webIntent);
//        }
//    }
    public void openStream(View v){
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.peacocktv.peacockandroid");
        if (launchIntent != null){
            this.startActivity(launchIntent);
        } else {
            Toast.makeText(Prem.this, "App not detected, redirecting to website", Toast.LENGTH_LONG).show();
            Uri espn = Uri.parse("https://www.peacocktv.com");
            Intent webIntent = new Intent(Intent.ACTION_VIEW, espn);
            startActivity(webIntent);
        }
    }
}


