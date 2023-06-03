package com.example.futhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.example.futhub.adapter.FixtureAdapter;
import com.example.futhub.manager.RequestManager;
import com.example.futhub.models.FixtureResponse;

public class UCL extends AppCompatActivity {

    Switch lightSwitch;
    boolean nightMODE;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    RecyclerView recyclerView;
    ProgressDialog dialog;
    RequestManager manager;

    String leagueId = "2"; // Champions League
    String next = "50";
    String application = "application/json";
    String host = "api-football-v1.p.rapidapi.com";
    String apiKey = "####";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ucl);

        // add support for night mode
        getSupportActionBar().hide();

        lightSwitch = findViewById(R.id.lightSwitch);
        sharedPreferences = getSharedPreferences("MODE",Context.MODE_PRIVATE);
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

        // pass call to API with league id for all leagues included
        manager = new RequestManager(this);
        manager.getFixture(listener, application, apiKey, host, next, leagueId);
        dialog.show();
    }

    private final ResponseListener listener = new ResponseListener() {
        @Override
        public void didFetch(FixtureResponse response, String message) {
            dialog.dismiss();

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(UCL.this,
                    LinearLayoutManager.VERTICAL, false));
            FixtureAdapter adapter = new FixtureAdapter(UCL.this, response.response);
            recyclerView.setAdapter(adapter);

            if (response.response.size() == 0){
                Toast.makeText(UCL.this, "No future games found", Toast.LENGTH_LONG).show();
            }


        }

        @Override
        public void didError(String message) {
            dialog.dismiss();
            Toast.makeText(UCL.this, message, Toast.LENGTH_SHORT).show();
        }


    };

    public void openStream(View v){
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.cbs.app");

        if(launchIntent != null){
            this.startActivity(launchIntent);
        } else{
            Toast.makeText(UCL.this, "App not detected, redirecting to website", Toast.LENGTH_LONG).show();
            Uri espn = Uri.parse("https://www.paramountplus.com/collections/sports-hub/");
            Intent webIntent = new Intent(Intent.ACTION_VIEW, espn);
            startActivity(webIntent);
        }
    }
}