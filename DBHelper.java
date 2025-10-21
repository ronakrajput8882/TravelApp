package com.example.travelapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.travelapp.models.Booking;
import com.example.travelapp.models.Trip;
import com.example.travelapp.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TravelApp.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DBHelper";

    // User Table
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "userId";
    private static final String COLUMN_USER_NAME = "name";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "password";
    private static final String COLUMN_USER_PHONE = "phoneNumber";

    // Trip Table
    private static final String TABLE_TRIPS = "trips";
    private static final String COLUMN_TRIP_ID = "tripId";
    private static final String COLUMN_TRIP_TITLE = "title";
    private static final String COLUMN_TRIP_LOCATION = "location";
    private static final String COLUMN_TRIP_DESCRIPTION = "description";
    private static final String COLUMN_TRIP_DURATION = "duration";
    private static final String COLUMN_TRIP_PRICE = "price";
    private static final String COLUMN_TRIP_RATING = "rating";
    private static final String COLUMN_TRIP_IMAGE_URL = "imageUrl";

    // Booking Table
    private static final String TABLE_BOOKINGS = "bookings";
    private static final String COLUMN_BOOKING_ID = "bookingId";
    private static final String COLUMN_BOOKING_USER_ID = "userId";
    private static final String COLUMN_BOOKING_TRIP_ID = "tripId";
    private static final String COLUMN_BOOKING_DATE = "bookingDate";
    private static final String COLUMN_BOOKING_PEOPLE = "numberOfPeople";
    private static final String COLUMN_BOOKING_STATUS = "status";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " TEXT PRIMARY KEY,"
                + COLUMN_USER_NAME + " TEXT,"
                + COLUMN_USER_EMAIL + " TEXT UNIQUE,"
                + COLUMN_USER_PASSWORD + " TEXT,"
                + COLUMN_USER_PHONE + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        // Create Trips table
        String CREATE_TRIPS_TABLE = "CREATE TABLE " + TABLE_TRIPS + "("
                + COLUMN_TRIP_ID + " TEXT PRIMARY KEY,"
                + COLUMN_TRIP_TITLE + " TEXT,"
                + COLUMN_TRIP_LOCATION + " TEXT,"
                + COLUMN_TRIP_DESCRIPTION + " TEXT,"
                + COLUMN_TRIP_DURATION + " TEXT,"
                + COLUMN_TRIP_PRICE + " REAL,"
                + COLUMN_TRIP_RATING + " REAL,"
                + COLUMN_TRIP_IMAGE_URL + " TEXT" + ")";
        db.execSQL(CREATE_TRIPS_TABLE);

        // Create Bookings table with the new COLUMN_BOOKING_PEOPLE
        String CREATE_BOOKINGS_TABLE = "CREATE TABLE " + TABLE_BOOKINGS + "("
                + COLUMN_BOOKING_ID + " TEXT PRIMARY KEY,"
                + COLUMN_BOOKING_USER_ID + " TEXT,"
                + COLUMN_BOOKING_TRIP_ID + " TEXT,"
                + COLUMN_BOOKING_DATE + " TEXT,"
                + COLUMN_BOOKING_PEOPLE + " INTEGER,"
                + COLUMN_BOOKING_STATUS + " TEXT,"
                + "FOREIGN KEY(" + COLUMN_BOOKING_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "),"
                + "FOREIGN KEY(" + COLUMN_BOOKING_TRIP_ID + ") REFERENCES " + TABLE_TRIPS + "(" + COLUMN_TRIP_ID + ")"
                + ")";
        db.execSQL(CREATE_BOOKINGS_TABLE);
        Log.d(TAG, "Database created with all tables.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
        onCreate(db);
        Log.d(TAG, "Database upgraded and tables recreated.");
    }

    // User methods
    public synchronized boolean addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, user.getUserId());
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_PHONE, user.getPhoneNumber());

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;
    }

    public synchronized User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, COLUMN_USER_EMAIL + " = ?", new String[]{email}, null, null, null);
        User user = null;
        if (cursor != null && cursor.moveToFirst()) {
            user = new User(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_PASSWORD)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_PHONE))
            );
            cursor.close();
        }
        db.close();
        return user;
    }

    // New method to get a user by their ID
    public synchronized User getUserById(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, COLUMN_USER_ID + " = ?", new String[]{userId}, null, null, null);
        User user = null;
        if (cursor != null && cursor.moveToFirst()) {
            user = new User(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_PASSWORD)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_PHONE))
            );
            cursor.close();
        }
        db.close();
        return user;
    }

    // Trip methods
    public synchronized boolean addTrip(Trip trip) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRIP_ID, trip.getTripId());
        values.put(COLUMN_TRIP_TITLE, trip.getTitle());
        values.put(COLUMN_TRIP_LOCATION, trip.getLocation());
        values.put(COLUMN_TRIP_DESCRIPTION, trip.getDescription());
        values.put(COLUMN_TRIP_DURATION, trip.getDuration());
        values.put(COLUMN_TRIP_PRICE, trip.getPrice());
        values.put(COLUMN_TRIP_RATING, trip.getRating());
        values.put(COLUMN_TRIP_IMAGE_URL, trip.getImageUrl());

        long result = db.insert(TABLE_TRIPS, null, values);
        db.close();
        return result != -1;
    }

    public synchronized List<Trip> getAllTrips() {
        List<Trip> tripList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TRIPS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Trip trip = new Trip();
                trip.setTripId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_ID)));
                trip.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_TITLE)));
                trip.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_LOCATION)));
                trip.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_DESCRIPTION)));
                trip.setDuration(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_DURATION)));
                trip.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TRIP_PRICE)));
                trip.setRating(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TRIP_RATING)));
                trip.setImageUrl(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_IMAGE_URL)));
                tripList.add(trip);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tripList;
    }

    public synchronized Trip getTripById(String tripId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TRIPS, null, COLUMN_TRIP_ID + " = ?", new String[]{tripId}, null, null, null);
        Trip trip = null;
        if (cursor != null && cursor.moveToFirst()) {
            trip = new Trip(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_LOCATION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_DURATION)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TRIP_PRICE)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TRIP_RATING)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_IMAGE_URL))
            );
            cursor.close();
        }
        db.close();
        return trip;
    }

    public synchronized void addSampleTrips() {
        if (getAllTrips().size() == 0) {
            addTrip(new Trip(UUID.randomUUID().toString(), "Swiss Alps Hiking", "Interlaken, Switzerland", "Hike through the breathtaking Swiss Alps, enjoying scenic views, fresh mountain air, and picturesque villages.", "5 Days", 1500.00, 4.8, "https://images.unsplash.com/photo-1498836528340-a3e30f1d5e56"));
            addTrip(new Trip(UUID.randomUUID().toString(), "Cairo Pyramids Tour", "Cairo, Egypt", "Journey back in time to ancient Egypt. Explore the Great Pyramids of Giza, the Sphinx, and the historic streets of Cairo.", "6 Days", 1200.00, 4.5, "https://images.unsplash.com/photo-1555503022-d7b32d2b5167"));
            addTrip(new Trip(UUID.randomUUID().toString(), "Kyoto Temples", "Kyoto, Japan", "Discover the ancient beauty of Kyoto. Visit serene temples, traditional gardens, and experience the rich Japanese culture.", "4 Days", 950.00, 4.7, "https://images.unsplash.com/photo-1542385108-769a835b89a8"));
            addTrip(new Trip(UUID.randomUUID().toString(), "Bora Bora Getaway", "Bora Bora, French Polynesia", "Relax in an overwater bungalow in paradise. Enjoy crystal-clear turquoise waters, white-sand beaches, and stunning sunsets.", "7 Days", 3500.00, 4.9, "https://images.unsplash.com/photo-1510414902-b2586a1f81d8"));
        }
    }

    // Booking methods
    public synchronized boolean addBooking(Booking booking) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOKING_ID, booking.getBookingId());
        values.put(COLUMN_BOOKING_USER_ID, booking.getUserId());
        values.put(COLUMN_BOOKING_TRIP_ID, booking.getTripId());
        values.put(COLUMN_BOOKING_DATE, booking.getBookingDate());
        values.put(COLUMN_BOOKING_PEOPLE, booking.getNumberOfPeople());
        values.put(COLUMN_BOOKING_STATUS, booking.getStatus());
        long result = db.insert(TABLE_BOOKINGS, null, values);
        db.close();
        return result != -1;
    }

    public synchronized List<Booking> getBookingsByUserId(String userId) {
        List<Booking> bookingList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BOOKINGS, null, COLUMN_BOOKING_USER_ID + " = ?", new String[]{userId}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Booking booking = new Booking();
                booking.setBookingId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_ID)));
                booking.setUserId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_USER_ID)));
                booking.setTripId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_TRIP_ID)));
                booking.setBookingDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_DATE)));
                booking.setNumberOfPeople(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_PEOPLE)));
                booking.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_STATUS)));
                bookingList.add(booking);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookingList;
    }

    public synchronized boolean deleteAllBookings() {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_BOOKINGS, null, null);
        db.close();
        Log.d(TAG, "Deleted " + rowsDeleted + " rows from bookings table.");
        return rowsDeleted > 0;
    }
}