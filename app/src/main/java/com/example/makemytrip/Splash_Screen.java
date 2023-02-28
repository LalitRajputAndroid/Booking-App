package com.example.makemytrip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceActivity;

public class Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
                boolean check = preferences.getBoolean("open", false);

                Intent intent;
                if (check) {
                    intent = new Intent(Splash_Screen.this, MainActivity.class);
                } else {
                    intent = new Intent(Splash_Screen.this, Login_Activity1.class);
                }

                startActivity(intent);
                finish();
            }
        },4000);

    }
}