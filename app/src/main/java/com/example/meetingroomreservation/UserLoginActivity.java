package com.example.meetingroomreservation;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UserLoginActivity extends AppCompatActivity {

    private EditText editTextEmployeeId, editTextPassword;
    private Button buttonLogin, buttonRegister;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        editTextEmployeeId = findViewById(R.id.editTextEmployeeId);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);

        MyDBHelper dbHelper = new MyDBHelper(this);
        db = dbHelper.getReadableDatabase();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String employeeId = editTextEmployeeId.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (authenticateUser(employeeId, password)) {
                    // Authentication successful
                    Toast.makeText(UserLoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), UserDashboardActivity.class);
                    startActivity(intent);
                } else {
                    // Authentication failed
                    Toast.makeText(getApplicationContext(), "Invalid Employee ID or Password", Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open UserRegistrationActivity for registration
                Intent intent = new Intent(UserLoginActivity.this, UserRegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean authenticateUser(String employeeId, String password) {
        String[] projection = {
                "user_id"
        };

        String selection = "employee_id = ? AND password = ?";
        String[] selectionArgs = {employeeId, password};

        Cursor cursor = db.query(
                "users",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int count = cursor.getCount();
        cursor.close();

        return count > 0;
    }
}
