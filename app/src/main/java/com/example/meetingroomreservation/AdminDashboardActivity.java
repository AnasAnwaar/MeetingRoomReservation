package com.example.meetingroomreservation;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

public class AdminDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
    }

    // Called when "Room Status" button is clicked
    public void onRoomStatusClick(View view) {
        Intent intent = new Intent(this, RoomStatusActivity.class);
        startActivity(intent);
    }

    // Called when "Approvals" button is clicked
    public void onApprovalsClick(View view) {
        Intent intent = new Intent(this, ApprovalsActivity.class);
        startActivity(intent);
    }
}
