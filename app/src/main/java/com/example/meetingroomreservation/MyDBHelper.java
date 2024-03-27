package com.example.meetingroomreservation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MyDatabase.db";

    // Define table and column names for reservations
    static final String TABLE_RESERVATIONS = "reservations";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_RESERVED_FOR = "reserved_for";
    static final String COLUMN_ROOM_NO = "room_no";
    static final String COLUMN_MEETING_TYPE = "meeting_type";
    static final String COLUMN_AGENDA = "agenda";
    static final String COLUMN_DATE = "date";
    static final String COLUMN_TIMING = "timing";
    static final String COLUMN_STATUS = "status";

    private static final String SQL_CREATE_RESERVATIONS_TABLE =
            "CREATE TABLE " + TABLE_RESERVATIONS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_RESERVED_FOR + " TEXT," +
                    COLUMN_ROOM_NO + " TEXT," +
                    COLUMN_MEETING_TYPE + " TEXT," +
                    COLUMN_AGENDA + " TEXT," +
                    COLUMN_DATE + " TEXT," +
                    COLUMN_TIMING + " TEXT," +
                    COLUMN_STATUS + " TEXT)";

    private static final String SQL_DELETE_RESERVATIONS_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_RESERVATIONS;

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_RESERVATIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_RESERVATIONS_TABLE);
        onCreate(db);
    }

    // Method to update reservation status
    public void updateReservationStatus(long reservationId, String status) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, status);

        // Updating status based on reservation ID
        db.update(TABLE_RESERVATIONS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(reservationId)});
        db.close();
    }

    // Method to update reservation details
    public void updateReservationDetails(long reservationId, String reservedFor, String roomNo, String meetingType, String agenda, Date date, String timing) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_RESERVED_FOR, reservedFor);
        values.put(COLUMN_ROOM_NO, roomNo);
        values.put(COLUMN_MEETING_TYPE, meetingType);
        values.put(COLUMN_AGENDA, agenda);
        values.put(COLUMN_DATE, formatDate(date)); // Insert formatted date string
        values.put(COLUMN_TIMING, timing);

        // Updating the row
        db.update(TABLE_RESERVATIONS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(reservationId)});
        db.close();
    }

    // Method to retrieve pending approvals from the database
    public List<Reservation> getPendingApprovals() {
        List<Reservation> pendingApprovals = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_RESERVATIONS, null, COLUMN_STATUS + " = ?", new String[]{"Pending"}, null, null, null);

        if (cursor != null) {
            try {
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                int reservedForIndex = cursor.getColumnIndex(COLUMN_RESERVED_FOR);
                int roomNoIndex = cursor.getColumnIndex(COLUMN_ROOM_NO);
                int meetingTypeIndex = cursor.getColumnIndex(COLUMN_MEETING_TYPE);
                int agendaIndex = cursor.getColumnIndex(COLUMN_AGENDA);
                int dateIndex = cursor.getColumnIndex(COLUMN_DATE);
                int timingIndex = cursor.getColumnIndex(COLUMN_TIMING);
                int statusIndex = cursor.getColumnIndex(COLUMN_STATUS);

                while (cursor.moveToNext()) {
                    long id = -1;
                    String reservedFor = "";
                    String roomNo = "";
                    String meetingType = "";
                    String agenda = "";
                    Date date = null;
                    String timing = "";
                    String status = "";

                    if (idIndex != -1) {
                        id = cursor.getLong(idIndex);
                    }
                    if (reservedForIndex != -1) {
                        reservedFor = cursor.getString(reservedForIndex);
                    }
                    if (roomNoIndex != -1) {
                        roomNo = cursor.getString(roomNoIndex);
                    }
                    if (meetingTypeIndex != -1) {
                        meetingType = cursor.getString(meetingTypeIndex);
                    }
                    if (agendaIndex != -1) {
                        agenda = cursor.getString(agendaIndex);
                    }
                    if (dateIndex != -1) {
                        String dateString = cursor.getString(dateIndex);
                        date = parseDate(dateString);
                    }
                    if (timingIndex != -1) {
                        timing = cursor.getString(timingIndex);
                    }
                    if (statusIndex != -1) {
                        status = cursor.getString(statusIndex);
                    }

                    // Create Reservation object and add it to the list
                    Reservation reservation = new Reservation(id, reservedFor, roomNo, meetingType, agenda, date, timing, status);
                    pendingApprovals.add(reservation);
                }
            } finally {
                cursor.close();
            }
        }

        db.close();
        return pendingApprovals;
    }

    // Method to parse date string to Date object
    private Date parseDate(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to format date string
    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(date);
    }

    public void addReservation(String reservedFor, String roomNo, String meetingType, String agenda, String date, String timing) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_RESERVED_FOR, reservedFor);
        values.put(COLUMN_ROOM_NO, roomNo);
        values.put(COLUMN_MEETING_TYPE, meetingType);
        values.put(COLUMN_AGENDA, agenda);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_TIMING, timing);
        values.put(COLUMN_STATUS, "Pending"); // Assuming the initial status is "Pending"

        // Inserting the new reservation row
        db.insert(TABLE_RESERVATIONS, null, values);
        db.close();
    }

    public void updateReservationDetails(long id, String updatedReservedFor, String updatedMeetingAgenda, String updatedDate, String updatedTiming) {
    }
}
