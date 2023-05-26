package com.example.futhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class UCL extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ucl);
    }

    public void openUcl(View v){
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