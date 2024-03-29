package com.example.meetingroomreservation;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserRegistrationActivity extends AppCompatActivity {
    private EditText editTextName, editTextEmployeeId, editTextDepartment, editTextDesignation, editTextContactNumber, editTextCompanyEmail, editTextPassword;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        // Initialize views
        editTextName = findViewById(R.id.editTextName);
        editTextEmployeeId = findViewById(R.id.editTextEmployeeId);
        editTextDepartment = findViewById(R.id.editTextDepartment);
        editTextDesignation = findViewById(R.id.editTextDesignation);
        editTextContactNumber = findViewById(R.id.editTextContactNumber);
        editTextCompanyEmail = findViewById(R.id.editTextCompanyEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonRegister = findViewById(R.id.buttonRegister);

        // Set click listener for register button
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String name = editTextName.getText().toString().trim();
                String employeeId = editTextEmployeeId.getText().toString().trim();
                String department = editTextDepartment.getText().toString().trim();
                String designation = editTextDesignation.getText().toString().trim();
                String contactNumber = editTextContactNumber.getText().toString().trim();
                String companyEmail = editTextCompanyEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                // Check if any field is empty
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(employeeId) || TextUtils.isEmpty(department) || TextUtils.isEmpty(designation) ||
                        TextUtils.isEmpty(contactNumber) || TextUtils.isEmpty(companyEmail) || TextUtils.isEmpty(password)) {
                    // Display error message if any field is empty
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Add user to database
                    registerUser(name, employeeId, department, designation, contactNumber, companyEmail, password);
                    Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(UserRegistrationActivity.this, UserLoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void registerUser(String name, String employeeId, String department, String designation, String contactNumber, String companyEmail, String password) {
        // Get instance of SQLite database
        SQLiteDatabase db = new MyDBHelper(this).getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("employee_id", employeeId);
        values.put("department", department);
        values.put("designation", designation);
        values.put("contact_number", contactNumber);
        values.put("company_email", companyEmail);
        values.put("password", password);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert("users", null, values);

        // Show success message or handle errors
    }
}
