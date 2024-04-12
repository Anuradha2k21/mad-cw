package com.example.mad_cw;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.mad_cw.admin.AdminModel;
import com.example.mad_cw.course.CourseModel;
import com.example.mad_cw.user.UserModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private final Context context;

    //  database attributes
    private static final String DB_NAME = "course.db";
    private static final int DB_VERSION = 1;

    //  admin table attributes
    private static final String ADMIN_TABLE_NAME = "admin_table";
    private static final String ADMIN_COLUMN_ID = "_id";
    private static final String ADMIN_COLUMN_EMAIL = "email";
    private static final String ADMIN_COLUMN_PASSWORD = "password";
    private AdminModel adminModel;
    private ArrayList<AdminModel> adminList;

    // course table attributes
    private static final String COURSE_TABLE_NAME = "course_table";
    private static final String COURSE_COLUMN_ID = "_id";
    private static final String COURSE_COLUMN_NAME = "name";
    private static final String COURSE_COLUMN_BRANCH = "branch";
    private static final String COURSE_COLUMN_COURSE_START_DATE = "course_start_date";
    private static final String COURSE_COLUMN_PUBLISH_DATE = "publish_date";
    private static final String COURSE_COLUMN_REGISTRATION_CLOSING_DATE = "registration_closing_date";
    private static final String COURSE_COLUMN_FEE = "fee";
    private static final String COURSE_COLUMN_DURATION = "duration";
    private static final String COURSE_COLUMN_DESCRIPTION = "description";
    private static final String COURSE_COLUMN_INSTRUCTOR = "instructor";
    private static final String COURSE_COLUMN_AVAILABLE_SEATS = "available_seats";
    private ArrayList<CourseModel> courseList;
    private CourseModel courseModel;

    //  user table attributes
    private static final String USER_TABLE_NAME = "user_table";
    private static final String USER_COLUMN_ID = "_id";
    private static final String USER_COLUMN_NAME = "name";
    private static final String USER_COLUMN_EMAIL = "email";
    private static final String USER_COLUMN_PASSWORD = "password";
    private static final String USER_COLUMN_TELEPHONE = "telephone";
    private static final String USER_COLUMN_GENDER = "gender";
    private static final String USER_COLUMN_ADDRESS = "address";
    private static final String USER_COLUMN_CITY = "city";
    private static final String USER_COLUMN_NIC = "nic";
    private static final String USER_COLUMN_DOB = "dob";

    private static final String USER_COLUMN_IMAGE = "image";
    private ArrayList<UserModel> userList;
    private UserModel userModel;
    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //  create admin table
        String adminQuery = "CREATE TABLE " + ADMIN_TABLE_NAME +
                " (" + ADMIN_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ADMIN_COLUMN_EMAIL + " TEXT, " +
                ADMIN_COLUMN_PASSWORD + " TEXT);";
        db.execSQL(adminQuery);


        Intent intent = new Intent();
        userModel = (UserModel) intent.getSerializableExtra("user");


        //  create course table
        String courseQuery = "CREATE TABLE " + COURSE_TABLE_NAME +
                " (" + COURSE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COURSE_COLUMN_NAME + " TEXT, " +
                COURSE_COLUMN_DESCRIPTION + " TEXT, " +
                COURSE_COLUMN_BRANCH + " TEXT, " +
                COURSE_COLUMN_AVAILABLE_SEATS + " INTEGER, " +
                COURSE_COLUMN_REGISTRATION_CLOSING_DATE + " TEXT, " +
                COURSE_COLUMN_COURSE_START_DATE + " TEXT, " +
                COURSE_COLUMN_DURATION + " TEXT, " +
                COURSE_COLUMN_PUBLISH_DATE + " TEXT, " +
                COURSE_COLUMN_FEE + " REAL, " +
                COURSE_COLUMN_INSTRUCTOR + " TEXT);";
        db.execSQL(courseQuery);

        //  create user table
        String userQuery = "CREATE TABLE " + USER_TABLE_NAME +
                " (" + USER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_COLUMN_NAME + " TEXT, " +
                USER_COLUMN_EMAIL + " TEXT, " +
                USER_COLUMN_PASSWORD + " TEXT, " +
                USER_COLUMN_TELEPHONE + " TEXT, " +
                USER_COLUMN_GENDER + " TEXT, " +
                USER_COLUMN_ADDRESS + " TEXT, " +
                USER_COLUMN_CITY + " TEXT, " +
                USER_COLUMN_NIC + " TEXT, " +

                USER_COLUMN_DOB + " TEXT, " +
                USER_COLUMN_IMAGE + " BLOB);";

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ADMIN_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + COURSE_TABLE_NAME);
        onCreate(db);
    }
    public boolean addUser(UserModel userModel) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(USER_COLUMN_NAME, userModel.getName());
        cv.put(USER_COLUMN_EMAIL, userModel.getEmail());
        cv.put(USER_COLUMN_PASSWORD, userModel.getPassword());
        cv.put(USER_COLUMN_TELEPHONE, userModel.getTelephone());
        cv.put(USER_COLUMN_GENDER, userModel.getGender());
        cv.put(USER_COLUMN_ADDRESS, userModel.getAddress());
        cv.put(USER_COLUMN_CITY, userModel.getCity());
        cv.put(USER_COLUMN_NIC, userModel.getNic());
        cv.put(USER_COLUMN_DOB, userModel.getDob());

        cv.put(USER_COLUMN_IMAGE, userModel.getImageBytes());


        long insert = db.insertOrThrow(USER_TABLE_NAME, null, cv);
        return insert == -1 ? false : true;
    }

    public UserModel updateUser(UserModel userModel) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(USER_COLUMN_NAME, userModel.getName());
        cv.put(USER_COLUMN_EMAIL, userModel.getEmail());
        cv.put(USER_COLUMN_PASSWORD, userModel.getPassword());
        cv.put(USER_COLUMN_TELEPHONE, userModel.getTelephone());
        cv.put(USER_COLUMN_GENDER, userModel.getGender());
        cv.put(USER_COLUMN_ADDRESS, userModel.getAddress());
        cv.put(USER_COLUMN_CITY, userModel.getCity());
        cv.put(USER_COLUMN_NIC, userModel.getNic());
        cv.put(USER_COLUMN_DOB, userModel.getDob());
        cv.put(USER_COLUMN_IMAGE, userModel.getImageBytes());

        String whereClause = "_id=?";
        String[] whereArgs = new String[] {String.valueOf(userModel.getId())};

        int count = db.update("user_table", cv, whereClause, whereArgs);
        if (count > 0) {
            return userModel;
        } else {
            return null;
        }
    }


    ArrayList<UserModel> getAllUsers() {
        userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + USER_TABLE_NAME;
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

                byte[] imageBytes = cursor.getBlob(10);

                userModel = new UserModel(userID, userName, email, password, telephone, gender, address, city, nic, dob, imageBytes);

                userList.add(userModel);
            }
        }
        cursor.close();
        db.close();
        return userList;
    }
    public UserModel checkUserLogin(String mail, String pword) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + USER_TABLE_NAME + " WHERE " + USER_COLUMN_EMAIL + " = '" +
                mail + "' AND " + USER_COLUMN_PASSWORD + " = '" + pword + "';";
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

            byte[] imageBytes = cursor.getBlob(10);

            userModel = new UserModel(userID, userName, email, password, telephone, gender, address, city, nic, dob, imageBytes);

        }
        cursor.close();
        db.close();
        return userModel;
    }
    public void addDummyCourses() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a list of dummy courses
        ArrayList<CourseModel> dummyCourses = new ArrayList<>();
        dummyCourses.add(new CourseModel("Java Fundamentals", "Learn the basics of Java", "Colombo", 10, "2023-01-01", "2023-02-01", "1 month", "2022-12-01", 100.0, "John Doe"));
        dummyCourses.add(new CourseModel("Python for Beginners", "Start your coding journey with Python", "Kandy", 15, "2023-03-01", "2023-04-01", "1 month", "2023-02-01", 80.0, "Jane Doe"));
        // Add more courses as needed

        // Insert each course into the database
        for (CourseModel course : dummyCourses) {
            ContentValues cv = new ContentValues();
            cv.put(COURSE_COLUMN_NAME, course.getName());
            cv.put(COURSE_COLUMN_DESCRIPTION, course.getDescription());
            cv.put(COURSE_COLUMN_BRANCH, course.getBranch());
            cv.put(COURSE_COLUMN_AVAILABLE_SEATS, course.getAvailableSeats());
            cv.put(COURSE_COLUMN_REGISTRATION_CLOSING_DATE, course.getRegistrationClosingDate());
            cv.put(COURSE_COLUMN_COURSE_START_DATE, course.getCourseStartDate());
            cv.put(COURSE_COLUMN_DURATION, course.getDuration());
            cv.put(COURSE_COLUMN_PUBLISH_DATE, course.getPublishDate());
            cv.put(COURSE_COLUMN_FEE, course.getFee());
            cv.put(COURSE_COLUMN_INSTRUCTOR, course.getInstructor());

            long result = db.insert(COURSE_TABLE_NAME, null, cv);

            if (result == -1) {
                Toast.makeText(context, "Failed to insert course: " + course.getName(), Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(context, "Successfully inserted course: " + course.getName(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean addOneCourse(CourseModel courseModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COURSE_COLUMN_NAME, courseModel.getName());
        cv.put(COURSE_COLUMN_DESCRIPTION, courseModel.getDescription());
        cv.put(COURSE_COLUMN_BRANCH, courseModel.getBranch());
        cv.put(COURSE_COLUMN_AVAILABLE_SEATS, courseModel.getAvailableSeats());
        cv.put(COURSE_COLUMN_REGISTRATION_CLOSING_DATE, courseModel.getRegistrationClosingDate());
        cv.put(COURSE_COLUMN_COURSE_START_DATE, courseModel.getCourseStartDate());
        cv.put(COURSE_COLUMN_DURATION, courseModel.getDuration());
        cv.put(COURSE_COLUMN_PUBLISH_DATE, courseModel.getPublishDate());
        cv.put(COURSE_COLUMN_FEE, courseModel.getFee());
        cv.put(COURSE_COLUMN_INSTRUCTOR, courseModel.getInstructor());

        long insert = db.insert(COURSE_TABLE_NAME, null, cv);
        return insert == -1 ? false : true;
    }

    public ArrayList<CourseModel> getAllCourses() {
        courseList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + COURSE_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.getCount() == 0) {
            return null;
        }
        else {
            while (cursor.moveToNext()){
                int courseID = cursor.getInt(0);
                String courseName = cursor.getString(1);
                String description = cursor.getString(2);
                String courseBranch = cursor.getString(3);
                int availableSeats = cursor.getInt(4);
                String registrationClosingDate = cursor.getString(5);
                String courseStartDate = cursor.getString(6);
                String duration = cursor.getString(7);
                String publishDate = cursor.getString(8);
                double fee = cursor.getDouble(9);
                String instructor = cursor.getString(10);

                courseModel = new CourseModel(courseID, courseName, description, courseBranch, availableSeats, registrationClosingDate, courseStartDate, duration, publishDate, fee, instructor);
                courseList.add(courseModel);
            }
        }
        cursor.close();
        db.close();
        return courseList;
    }

    public void addDummyAdmin() {

        adminModel = new AdminModel("admin@gmail.com", "12345678");

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ADMIN_COLUMN_EMAIL, adminModel.getEmail());
        cv.put(ADMIN_COLUMN_PASSWORD, adminModel.getPassword());

        db.insert(ADMIN_TABLE_NAME, null, cv);
        db.close();
    }



    public AdminModel checkAdminLogin(String mail, String pword) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + ADMIN_TABLE_NAME + " WHERE " + ADMIN_COLUMN_EMAIL + " = '" +
                mail + "' AND " + ADMIN_COLUMN_PASSWORD + " = '" + pword + "';";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() == 0) {
            return null;
        } else {
            cursor.moveToFirst();
            int adminID = cursor.getInt(0);
            String email = cursor.getString(1);
            String password = cursor.getString(2);

            adminModel = new AdminModel(adminID, email, password);
        }
        cursor.close();
        db.close();
        return adminModel;
    }
}