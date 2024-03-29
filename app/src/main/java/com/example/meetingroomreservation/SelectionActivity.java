package com.example.meetingroomreservation;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

public class SelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
    }

    // Called when "Login as Admin" button is clicked
    public void onAdminLoginClick(View view) {
        Intent intent = new Intent(this, AdminLoginActivity.class);
        startActivity(intent);
        finish();
    }

    // Called when "Login as User" button is clicked
    public void onUserLoginClick(View view) {
        Intent intent = new Intent(this, UserLoginActivity.class);
        startActivity(intent);
        finish();
    }

    // Called when "Make Display Device" button is clicked
    public void onDisplayDeviceClick(View view) {
        Intent intent = new Intent(this, DisplayScreenActivity.class);
        startActivity(intent);
        finish();
    }
}
