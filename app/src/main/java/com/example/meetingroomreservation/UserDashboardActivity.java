package com.example.meetingroomreservation;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class UserDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
    }

    // Method to navigate to the ReservationActivity
    public void goToReservationActivity(View view) {
        Intent intent = new Intent(this, ReservationActivity.class);
        startActivity(intent);
        finish();
    }

    // Method to navigate to the RoomStatusActivity
    public void goToRoomStatusActivity(View view) {
        Intent intent = new Intent(this, RoomStatusActivity.class);
        startActivity(intent);
        finish();
    }
}
