package com.example.meetingroomreservation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    // Splash screen timer
    private static final int SPLASH_TIME_OUT = 2000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Rooms room1 = new Rooms("0A");
        Rooms room2 = new Rooms("0B");
        Rooms room3 = new Rooms("0C");
        Rooms room4 = new Rooms("0D");
        Rooms room5 = new Rooms("1A");
        Rooms room6 = new Rooms("1B");
        Rooms room7 = new Rooms("1C");
        Rooms room8 = new Rooms("1D");
        Rooms room9 = new Rooms("5A");
        Rooms room10 = new Rooms("5B");

        // Delay the transition to the main activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the main activity after the splash screen duration
                Intent intent = new Intent(SplashActivity.this, SelectionActivity.class);
                startActivity(intent);
                finish(); // Close the splash activity to prevent going back to it
            }
        }, SPLASH_TIME_OUT);
    }
}
