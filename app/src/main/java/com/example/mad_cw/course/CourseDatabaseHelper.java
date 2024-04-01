package com.example.mad_cw.course;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CourseDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "course.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "course_table";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_BRANCH = "branch";
    private static final String COLUMN_COURSE_START_DATE = "course_start_date";
    private static final String COLUMN_PUBLISH_DATE = "publish_date";
    private static final String COLUMN_REGISTRATION_CLOSING_DATE = "registration_closing_date";
    private static final String COLUMN_FEE = "fee";
    private static final String COLUMN_DURATION = "duration";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_INSTRUCTOR = "instructor";
    private static final String COLUMN_AVAILABLE_SEATS = "available_seats";
    ArrayList<CourseModel> courseList;
    CourseModel courseModel;
    Context context;

    public CourseDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_BRANCH + " TEXT, " +
                COLUMN_AVAILABLE_SEATS + " INTEGER, " +
                COLUMN_REGISTRATION_CLOSING_DATE + " TEXT, " +
                COLUMN_COURSE_START_DATE + " TEXT, " +
                COLUMN_DURATION + " TEXT, " +
                COLUMN_PUBLISH_DATE + " TEXT, " +
                COLUMN_FEE + " REAL, " +
                COLUMN_INSTRUCTOR + " TEXT);";
        db.execSQL(query);


    }

    public void insertDummyData() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a list of dummy courses
        ArrayList<CourseModel> dummyCourses = new ArrayList<>();
        dummyCourses.add(new CourseModel("Java Fundamentals", "Learn the basics of Java", "Colombo", 10, "2023-01-01", "2023-02-01", "1 month", "2022-12-01", 100.0, "John Doe"));
        dummyCourses.add(new CourseModel("Python for Beginners", "Start your coding journey with Python", "Kandy", 15, "2023-03-01", "2023-04-01", "1 month", "2023-02-01", 80.0, "Jane Doe"));
        // Add more courses as needed

        // Insert each course into the database
        for (CourseModel course : dummyCourses) {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, course.getName());
            cv.put(COLUMN_DESCRIPTION, course.getDescription());
            cv.put(COLUMN_BRANCH, course.getBranch());
            cv.put(COLUMN_AVAILABLE_SEATS, course.getAvailableSeats());
            cv.put(COLUMN_REGISTRATION_CLOSING_DATE, course.getRegistrationClosingDate());
            cv.put(COLUMN_COURSE_START_DATE, course.getCourseStartDate());
            cv.put(COLUMN_DURATION, course.getDuration());
            cv.put(COLUMN_PUBLISH_DATE, course.getPublishDate());
            cv.put(COLUMN_FEE, course.getFee());
            cv.put(COLUMN_INSTRUCTOR, course.getInstructor());

            long result = db.insert(TABLE_NAME, null, cv);

            if (result == -1) {
                Toast.makeText(context, "Failed to insert course: " + course.getName(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Successfully inserted course: " + course.getName(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addOne(CourseModel courseModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, courseModel.getName());
        cv.put(COLUMN_DESCRIPTION, courseModel.getDescription());
        cv.put(COLUMN_BRANCH, courseModel.getBranch());
        cv.put(COLUMN_AVAILABLE_SEATS, courseModel.getAvailableSeats());
        cv.put(COLUMN_REGISTRATION_CLOSING_DATE, courseModel.getRegistrationClosingDate());
        cv.put(COLUMN_COURSE_START_DATE, courseModel.getCourseStartDate());
        cv.put(COLUMN_DURATION, courseModel.getDuration());
        cv.put(COLUMN_PUBLISH_DATE, courseModel.getPublishDate());
        cv.put(COLUMN_FEE, courseModel.getFee());
        cv.put(COLUMN_INSTRUCTOR, courseModel.getInstructor());

        long insert = db.insert(TABLE_NAME, null, cv);
        return insert == -1 ? false : true;
    }

    ArrayList<CourseModel> getAllCourses() {
        courseList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME;
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
}
