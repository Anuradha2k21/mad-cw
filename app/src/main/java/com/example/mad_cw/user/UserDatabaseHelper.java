package com.example.mad_cw.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


import java.sql.SQLException;
import java.util.ArrayList;

public class UserDatabaseHelper extends SQLiteOpenHelper {
    Context context;
    private static final String DB_NAME = "user.db";
    private static final int DB_VERSION = 2;
    private static final String TABLE_NAME = "user_table";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_TELEPHONE = "telephone";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_CITY = "city";
    private static final String COLUMN_NIC = "nic";
    private static final String COLUMN_DOB = "dob";
    ArrayList<UserModel> userList;
    UserModel userModel;
    public UserDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_TELEPHONE + " TEXT, " +
                COLUMN_GENDER + " TEXT, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_CITY + " TEXT, " +
                COLUMN_NIC + " TEXT, " +
                COLUMN_DOB + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public boolean addUser(UserModel userModel) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, userModel.getName());
        cv.put(COLUMN_EMAIL, userModel.getEmail());
        cv.put(COLUMN_PASSWORD, userModel.getPassword());
        cv.put(COLUMN_TELEPHONE, userModel.getTelephone());
        cv.put(COLUMN_GENDER, userModel.getGender());
        cv.put(COLUMN_ADDRESS, userModel.getAddress());
        cv.put(COLUMN_CITY, userModel.getCity());
        cv.put(COLUMN_NIC, userModel.getNic());
        cv.put(COLUMN_DOB, userModel.getDob());

        long insert = db.insertOrThrow(TABLE_NAME, null, cv);
        return insert == -1 ? false : true;
    }

    ArrayList<UserModel> getAllUsers() {
        userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.getCount() == 0) {
            return null;
        }
        else {
            while (cursor.moveToNext()){
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                String email = cursor.getString(2);
                String password = cursor.getString(3);
                String telephone = cursor.getString(4);
                String gender = cursor.getString(5);
                String address = cursor.getString(6);
                String city = cursor.getString(7);
                String nic = cursor.getString(8);
                String dob = cursor.getString(9);

                userModel = new UserModel(userID, userName, email, password, telephone, gender, address, city, nic, dob);
                userList.add(userModel);
            }
        }
        cursor.close();
        db.close();
        return userList;
    }
    UserModel checkLogin(String mail, String pword) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " = '" +
                mail + "' AND " + COLUMN_PASSWORD + " = '" + pword + "';";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() == 0) {
            return null;
        }
        else {
            cursor.moveToFirst();
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                String email = cursor.getString(2);
                String password = cursor.getString(3);
                String telephone = cursor.getString(4);
                String gender = cursor.getString(5);
                String address = cursor.getString(6);
                String city = cursor.getString(7);
                String nic = cursor.getString(8);
                String dob = cursor.getString(9);

                userModel = new UserModel(userID, userName, email, password, telephone, gender, address, city, nic, dob);
        }
        cursor.close();
        db.close();
        return userModel;
    }
}
