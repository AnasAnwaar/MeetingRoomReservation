package com.example.meetingroomreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    }
}