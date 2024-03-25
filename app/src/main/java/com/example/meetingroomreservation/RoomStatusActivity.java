package com.example.meetingroomreservation;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RoomStatusActivity extends AppCompatActivity {

    private MyDBHelper dbHelper;
    private TextView textViewRoomStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_status);

        dbHelper = new MyDBHelper(this);
        textViewRoomStatus = findViewById(R.id.textViewRoomStatus);

        // Retrieve the room number from intent extras
        String roomNumber = getIntent().getStringExtra("ROOM_NUMBER");

        // Query the database for entries related to the specified room number
        String roomStatus = queryRoomStatus(roomNumber);

        // Display the room status information
        textViewRoomStatus.setText(roomStatus);
    }

    private String queryRoomStatus(String roomNumber) {
        StringBuilder roomStatus = new StringBuilder();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get current date and next day date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, 1); // Add one day
        Date nextDay = calendar.getTime();

        // Define the query
        String[] projection = {
                MyDBHelper.COLUMN_RESERVED_FOR,
                MyDBHelper.COLUMN_MEETING_TYPE,
                MyDBHelper.COLUMN_AGENDA,
                MyDBHelper.COLUMN_DATE,
                MyDBHelper.COLUMN_TIMING
        };
        String selection = MyDBHelper.COLUMN_ROOM_NO + " = ? AND " +
                MyDBHelper.COLUMN_DATE + " >= ? AND " +
                MyDBHelper.COLUMN_DATE + " <= ?";
        String[] selectionArgs = {roomNumber, dateFormat.format(currentDate), dateFormat.format(nextDay)};

        // Query the database
        Cursor cursor = db.query(
                MyDBHelper.TABLE_RESERVATIONS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        // Iterate through the cursor to build room status information
        while (cursor.moveToNext()) {
            String reservedFor = cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.COLUMN_RESERVED_FOR));
            String meetingType = cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.COLUMN_MEETING_TYPE));
            String agenda = cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.COLUMN_AGENDA));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.COLUMN_DATE));
            String timing = cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.COLUMN_TIMING));

            // Append the room status information to the StringBuilder
            roomStatus.append("Reserved for: ").append(reservedFor).append("\n")
                    .append("Meeting Type: ").append(meetingType).append("\n")
                    .append("Agenda: ").append(agenda).append("\n")
                    .append("Date: ").append(date).append("\n")
                    .append("Timing: ").append(timing).append("\n\n");
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return roomStatus.toString();
    }
}
