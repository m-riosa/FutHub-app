package com.example.futhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

        Switch lightSwitch;
        boolean nightMODE;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


        Button buttonLaLiga = findViewById(R.id.buttonLaLiga);
        Button buttonPrem = findViewById(R.id.buttonPrem);
        Button buttonSerieA = findViewById(R.id.buttonSerieA);
        Button buttonUcl = findViewById(R.id.buttonUCL);
        Button buttonArg = findViewById(R.id.buttonArg);

        buttonLaLiga.setOnClickListener(this);
        buttonPrem.setOnClickListener(this);
        buttonSerieA.setOnClickListener(this);
        buttonUcl.setOnClickListener(this);
        buttonArg.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonLaLiga:
                openLaLiga();
                break;
            case R.id.buttonPrem:
                openPrem();
                break;
            case R.id.buttonSerieA:
                openSerieA();
                break;
            case R.id.buttonUCL:
                openUCL();
                break;
            case R.id.buttonArg:
                openLigaArg();
                break;

        }

    }

    public void openLaLiga() {
        Intent intent = new Intent(this, LaLiga.class);
        startActivity(intent);
    }

    public void openPrem(){
        Intent intent = new Intent(this, Prem.class);
        startActivity(intent);
    }
    public void openSerieA(){
        Intent intent = new Intent(this, SerieA.class);
        startActivity(intent);
    }
    public void openUCL(){
        Intent intent = new Intent(this, UCL.class);
        startActivity(intent);
    }
    public void openLigaArg(){
        Intent intent = new Intent(this, LigaArg.class);
        startActivity(intent);
    }
}

