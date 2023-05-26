package com.example.futhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LaLiga extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_la_liga);
    }

    public void openLiga(View v){
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.espn.score_center");

        if(launchIntent != null){
            this.startActivity(launchIntent);
        } else{
            Toast.makeText(LaLiga.this, "App not detected, redirecting to website", Toast.LENGTH_LONG).show();
            Uri espn = Uri.parse("https://www.espn.com/espnplus");
            Intent webIntent = new Intent(Intent.ACTION_VIEW, espn);
            startActivity(webIntent);
        }
    }
}