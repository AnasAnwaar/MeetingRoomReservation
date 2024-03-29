package com.example.meetingroomreservation;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DisplayScreenActivity extends AppCompatActivity {
    private Spinner spinnerRoom;
    private Button buttonCheckSchedule;
    private TextView textViewRoomNumber, textViewOccupancyStatus;
    private TableLayout tableLayout;

    private MyDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_screen);

        // Initialize views
        spinnerRoom = findViewById(R.id.spinnerRoom);
        buttonCheckSchedule = findViewById(R.id.buttonCheckSchedule);
        textViewRoomNumber = findViewById(R.id.textViewRoomNumber);
        textViewOccupancyStatus = findViewById(R.id.textViewOccupancyStatus);
        tableLayout = findViewById(R.id.tableLayout);

        // Initialize database helper
        dbHelper = new MyDBHelper(this);

        // Populate spinner with room numbers
        populateSpinner();

        // Set click listener for check schedule button
        buttonCheckSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get selected room number
                String selectedRoom = spinnerRoom.getSelectedItem().toString();

                // Check schedule for selected room
                checkRoomSchedule(selectedRoom);
            }
        });
    }

    private void populateSpinner() {
        // Get room numbers from the database
        List<String> roomNumbers = getRoomNumbers();

        // Create adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roomNumbers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set adapter to spinner
        spinnerRoom.setAdapter(adapter);
    }

    private List<String> getRoomNumbers() {
        List<String> roomNumbers = new ArrayList<>();

        // Get instance of SQLite database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query to get distinct room numbers from reservations table
        Cursor cursor = db.rawQuery("SELECT DISTINCT " + MyDBHelper.COLUMN_ROOM_NO + " FROM " + MyDBHelper.TABLE_RESERVATIONS, null);

        // Add room numbers to the list
        if (cursor != null && cursor.moveToFirst()) {
            do {
                roomNumbers.add(cursor.getString(0));
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return roomNumbers;
    }

    private void checkRoomSchedule(String roomNumber) {
        // Clear table layout
        tableLayout.removeAllViews();

        // Get current date
        String currentDate = getCurrentDate();

        // Get reservations for the selected room and current date
        List<Reservation> reservations = dbHelper.getReservationsForRoom(roomNumber, currentDate);

        // Display reservations in table layout
        displayReservations(reservations);

        // Display room number
        textViewRoomNumber.setText("Room Number: " + roomNumber);

        // Check if room is occupied or free at current time
        boolean isOccupied = dbHelper.isRoomOccupied(roomNumber, currentDate);
        if (isOccupied) {
            textViewOccupancyStatus.setText("Occupancy Status: Occupied");
        } else {
            textViewOccupancyStatus.setText("Occupancy Status: Free");
        }
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }

    private void displayReservations(List<Reservation> reservations) {
        for (Reservation reservation : reservations) {
            TableRow row = new TableRow(this);

            TextView textViewDate = new TextView(this);
            textViewDate.setText(reservation.getDate());
            row.addView(textViewDate);
            TextView textViewTiming = new TextView(this);
            textViewTiming.setText(reservation.getTiming());
            row.addView(textViewTiming);
            TextView textViewReservedFor = new TextView(this);
            textViewReservedFor.setText(reservation.getReservedFor());
            row.addView(textViewReservedFor);

            // Add row to table layout
            tableLayout.addView(row);
        }
    }
}
