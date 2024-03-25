package com.example.meetingroomreservation;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class ReservationActivity extends AppCompatActivity {

    private EditText editTextReservedFor, editTextMeetingAgenda, editTextDate, editTextTiming;
    private Spinner spinnerRoomNo, spinnerMeetingType;
    private Button buttonReserve;

    private MyDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        dbHelper = new MyDBHelper(this);

        editTextReservedFor = findViewById(R.id.editTextReservedFor);
        editTextMeetingAgenda = findViewById(R.id.editTextMeetingAgenda);
        editTextDate = findViewById(R.id.editTextDate);
        editTextTiming = findViewById(R.id.editTextTiming);
        spinnerRoomNo = findViewById(R.id.spinnerRoomNo);
        spinnerMeetingType = findViewById(R.id.spinnerMeetingType);
        buttonReserve = findViewById(R.id.buttonReserve);

        // Populate room numbers spinner
        ArrayAdapter<String> roomAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getRoomNumbers());
        roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoomNo.setAdapter(roomAdapter);

        // Populate meeting types spinner
        ArrayAdapter<CharSequence> meetingAdapter = ArrayAdapter.createFromResource(this,
                R.array.meeting_types_array, android.R.layout.simple_spinner_item);
        meetingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMeetingType.setAdapter(meetingAdapter);

        // Set OnClickListener for Date EditText
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Set OnClickListener for Reserve button
        buttonReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle reservation process
                reserveMeetingRoom();
            }
        });
    }

    private void showDatePickerDialog() {
        // Get current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Display selected date in EditText
                        editTextDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private String[] getRoomNumbers() {
        // Array of room numbers
        return new String[]{"0A", "0B", "0C", "0D", "1A", "1B", "1C", "1D", "5A", "5B"};
    }

    private void reserveMeetingRoom() {
        // Retrieve values from EditText and Spinner
        String reservedFor = editTextReservedFor.getText().toString().trim();
        String roomNo = spinnerRoomNo.getSelectedItem().toString();
        String meetingType = spinnerMeetingType.getSelectedItem().toString();
        String agenda = editTextMeetingAgenda.getText().toString().trim();
        String date = editTextDate.getText().toString().trim();
        String timing = editTextTiming.getText().toString().trim();

        // Insert reservation into the database
        dbHelper.addReservation(reservedFor, roomNo, meetingType, agenda, date, timing);

        // Display a notification to the user
        NotificationHandler.showNotification(this, "Meeting Room Reserved", "Your meeting room has been successfully reserved!");
    }
}

