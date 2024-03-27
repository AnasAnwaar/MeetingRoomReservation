package com.example.meetingroomreservation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
    }

    // Method to handle the login button click
    public void login(View view) {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Check if username and password match the admin credentials
        if (username.equals("admin") && password.equals("12345678")) {
            // Admin login successful, navigate to admin dashboard or any other activity
            Intent intent = new Intent(this, AdminDashboardActivity.class);
            startActivity(intent);
            finish(); // Finish the current activity to prevent returning to the login page
        } else {
            // Admin login failed, show an error message
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }
}
