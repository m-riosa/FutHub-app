package com.example.futhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SerieA extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serie);
    }

    public void openSerie(View v){
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.cbs.app");

        if(launchIntent != null){
            this.startActivity(launchIntent);
        } else{
            Toast.makeText(SerieA.this, "App not detected, redirecting to website", Toast.LENGTH_LONG).show();
            Uri espn = Uri.parse("https://www.paramountplus.com/shows/serie-a/");
            Intent webIntent = new Intent(Intent.ACTION_VIEW, espn);
            startActivity(webIntent);
        }
    }
}