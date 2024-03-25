package com.example.meetingroomreservation;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MyDatabase.db";

    // Define table and column names for users
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMPLOYEE_ID = "employee_id";
    private static final String COLUMN_DEPARTMENT = "department";
    private static final String COLUMN_DESIGNATION = "designation";
    private static final String COLUMN_CONTACT_NUMBER = "contact_number";
    private static final String COLUMN_COMPANY_EMAIL = "company_email";
    private static final String COLUMN_PASSWORD = "password";

    // Define table and column names for reservations
    private static final String TABLE_RESERVATIONS = "reservations";
    private static final String COLUMN_RESERVED_FOR = "reserved_for";
    private static final String COLUMN_ROOM_NO = "room_no";
    private static final String COLUMN_MEETING_TYPE = "meeting_type";
    private static final String COLUMN_AGENDA = "agenda";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIMING = "timing";

    private static final String SQL_CREATE_USERS_TABLE =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_EMPLOYEE_ID + " TEXT," +
                    COLUMN_DEPARTMENT + " TEXT," +
                    COLUMN_DESIGNATION + " TEXT," +
                    COLUMN_CONTACT_NUMBER + " TEXT," +
                    COLUMN_COMPANY_EMAIL + " TEXT," +
                    COLUMN_PASSWORD + " TEXT)";

    private static final String SQL_CREATE_RESERVATIONS_TABLE =
            "CREATE TABLE " + TABLE_RESERVATIONS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_RESERVED_FOR + " TEXT," +
                    COLUMN_ROOM_NO + " TEXT," +
                    COLUMN_MEETING_TYPE + " TEXT," +
                    COLUMN_AGENDA + " TEXT," +
                    COLUMN_DATE + " TEXT," +
                    COLUMN_TIMING + " TEXT)";

    private static final String SQL_DELETE_USERS_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_USERS;

    private static final String SQL_DELETE_RESERVATIONS_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_RESERVATIONS;

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USERS_TABLE);
        db.execSQL(SQL_CREATE_RESERVATIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_USERS_TABLE);
        db.execSQL(SQL_DELETE_RESERVATIONS_TABLE);
        onCreate(db);
    }

    // Method to insert a user into the database
    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_EMPLOYEE_ID, user.getEmployeeId());
        values.put(COLUMN_DEPARTMENT, user.getDepartment());
        values.put(COLUMN_DESIGNATION, user.getDesignation());
        values.put(COLUMN_CONTACT_NUMBER, user.getContactNumber());
        values.put(COLUMN_COMPANY_EMAIL, user.getCompanyEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());

        // Inserting Row
        long newRowId = db.insert(TABLE_USERS, null, values);
        db.close();
        return newRowId;
    }

    // Method to insert a reservation into the database
    public long addReservation(String reservedFor, String roomNo, String meetingType, String agenda, String date, String timing) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_RESERVED_FOR, reservedFor);
        values.put(COLUMN_ROOM_NO, roomNo);
        values.put(COLUMN_MEETING_TYPE, meetingType);
        values.put(COLUMN_AGENDA, agenda);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_TIMING, timing);

        // Inserting Row
        long newRowId = db.insert(TABLE_RESERVATIONS, null, values);
        db.close();
        return newRowId;
    }
}
