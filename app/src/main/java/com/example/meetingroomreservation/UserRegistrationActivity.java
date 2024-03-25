package com.example.meetingroomreservation;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

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

                // Add user to database
                registerUser(name, employeeId, department, designation, contactNumber, companyEmail, password);
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
