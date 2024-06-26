package com.example.mad_cw;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.mad_cw.admin.AdminModel;
import com.example.mad_cw.course.CourseModel;
import com.example.mad_cw.course.CourseRegisterModel;
import com.example.mad_cw.course.PromotionCodeModel;
import com.example.mad_cw.user.UserModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private final Context context;

    //  database attributes
    private static final String DB_NAME = "course.db";
    private static final int DB_VERSION = 2;

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

    // course registration table attributes
    private static final String COURSE_REGISTRATION_TABLE_NAME = "course_registration_table";
    private static final String COURSE_REGISTRATION_COLUMN_ID = "_id";
    private static final String COURSE_REGISTRATION_COLUMN_COURSE_NAME = "course_name";
    private static final String COURSE_REGISTRATION_COLUMN_COURSE_BRANCH = "course_branch";
    private static final String COURSE_REGISTRATION_COLUMN_FULL_NAME = "full_name";
    private static final String COURSE_REGISTRATION_COLUMN_NIC = "nic";
    private static final String COURSE_REGISTRATION_COLUMN_EMAIL = "email";
    private static final String COURSE_REGISTRATION_COLUMN_CONTACT_NO = "contact_no";
    private static final String COURSE_REGISTRATION_COLUMN_ADDRESS = "address";
    private static final String COURSE_REGISTRATION_COLUMN_PROMOTION_CODE = "promotion_code";
    private static final String COURSE_REGISTRATION_COLUMN_COURSE_FEE = "course_fee";
    private static final String COURSE_REGISTRATION_COLUMN_DISCOUNT_AMOUNT = "discount_amount";
    private static final String COURSE_REGISTRATION_COLUMN_FINAL_TOTAL = "final_total";

    //  promotion table attributes
    private static final String PROMOTION_CODE_TABLE_NAME = "promotion_code";
    private static final String PROMOTION_CODE_COLUMN_ID = "_id";
    private static final String PROMOTION_CODE_COLUMN_PROMO_CODE = "promo_code";
    private static final String PROMOTION_CODE_COLUMN_DISCOUNT_PERCENTAGE = "discount_percentage";

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

        // Create the course registration table with a foreign key relationship to the course table
        String courseRegistrationQuery = "CREATE TABLE " + COURSE_REGISTRATION_TABLE_NAME +
                " (" + COURSE_REGISTRATION_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COURSE_REGISTRATION_COLUMN_COURSE_NAME + " TEXT, " +
                COURSE_REGISTRATION_COLUMN_COURSE_BRANCH + " TEXT, " +
                COURSE_REGISTRATION_COLUMN_FULL_NAME + " TEXT, " +
                COURSE_REGISTRATION_COLUMN_NIC + " TEXT, " +
                COURSE_REGISTRATION_COLUMN_EMAIL + " TEXT, " +
                COURSE_REGISTRATION_COLUMN_CONTACT_NO + " TEXT, " +
                COURSE_REGISTRATION_COLUMN_ADDRESS + " TEXT, " +
                COURSE_REGISTRATION_COLUMN_PROMOTION_CODE + " TEXT, " +
                COURSE_REGISTRATION_COLUMN_COURSE_FEE + " REAL, " +
                COURSE_REGISTRATION_COLUMN_DISCOUNT_AMOUNT + " REAL, " +
                COURSE_REGISTRATION_COLUMN_FINAL_TOTAL + " REAL);";
        db.execSQL(courseRegistrationQuery);

        // Create the promotion code table
        String promotionCodeQuery = "CREATE TABLE " + PROMOTION_CODE_TABLE_NAME +
                " (" + PROMOTION_CODE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PROMOTION_CODE_COLUMN_PROMO_CODE + " TEXT, " +
                PROMOTION_CODE_COLUMN_DISCOUNT_PERCENTAGE + " REAL);";
        db.execSQL(promotionCodeQuery);

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
        db.execSQL(userQuery);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int version = oldVersion + 1; version <= newVersion; version++) {
            upgradeTo(db, version);
        }
    }
    private void upgradeTo(SQLiteDatabase db, int version) {
        switch (version) {
            case 2:
                // Upgrade from version 1 to 2
                db.execSQL("ALTER TABLE " + COURSE_TABLE_NAME + " ADD COLUMN new_column INTEGER");
                break;
            case 3:
                // Upgrade from version 2 to 3
                db.execSQL("ALTER TABLE " + USER_TABLE_NAME + " ADD COLUMN new_column TEXT");
                break;
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int version = oldVersion - 1; version >= newVersion; version--) {
            downgradeTo(db, version);
        }
    }
    private void downgradeTo(SQLiteDatabase db, int version) {
        switch (version) {
            case 2:
                break;
            case 1:
                break;
        }
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
        return insert != -1;
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

    public ArrayList<UserModel> getAllUsers() {
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
        addDummyCourses();
        return userModel;
    }
    public void addDummyCourses() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a list of dummy courses
        ArrayList<CourseModel> dummyCourses = new ArrayList<>();
        dummyCourses.add(new CourseModel("Java Fundamentals", "Learn the basics of Java", "Colombo, Mathara, Galle", 10, "2023-01-01", "2023-02-01", "1 month", "2022-12-01", 80000.0, "John Doe"));
        dummyCourses.add(new CourseModel("Python for Beginners", "Start your coding journey with Python", "Kandy", 15, "2023-03-01", "2023-04-01", "1 month", "2023-02-01", 850000.0, "Jane Doe"));

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
            }
            else {
//                Toast.makeText(context, "Successfully inserted course: " + course.getName(), Toast.LENGTH_SHORT).show();
            }
        }
        db.close();
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
        return insert != -1;
    }

    public ArrayList<CourseModel> getAllCourses() {
        courseList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + COURSE_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.getCount() == 0) {
            return new ArrayList<>();
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


    public void addDummyPromotionCode() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a list of dummy courses
        ArrayList<PromotionCodeModel> dummyPromoCode = new ArrayList<>();
        dummyPromoCode.add(new PromotionCodeModel("M563432",25.0));
        dummyPromoCode.add(new PromotionCodeModel("S663435",40.0));
        dummyPromoCode.add(new PromotionCodeModel("L763434",60.0));

        // Insert each course into the database
        for (PromotionCodeModel promotionCode : dummyPromoCode) {
            ContentValues cv = new ContentValues();
            cv.put(PROMOTION_CODE_COLUMN_PROMO_CODE, promotionCode.getPromoCode());
            cv.put(PROMOTION_CODE_COLUMN_DISCOUNT_PERCENTAGE, promotionCode.getDiscountPercentage());

            long result = db.insert(PROMOTION_CODE_TABLE_NAME, null, cv);

            if (result == -1) {
                Toast.makeText(context, "Failed to insert promotion code", Toast.LENGTH_SHORT).show();
            }
            else {
//                Toast.makeText(context, "Successfully inserted promotion code", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean addOnePromotionCode(PromotionCodeModel promotionCodeModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(PROMOTION_CODE_COLUMN_PROMO_CODE, promotionCodeModel.getPromoCode());
        cv.put(PROMOTION_CODE_COLUMN_DISCOUNT_PERCENTAGE, promotionCodeModel.getDiscountPercentage());

        long insert = db.insert(PROMOTION_CODE_TABLE_NAME, null, cv);
        return insert != -1;
    }

    public double getDiscountPercentageByPromoCode(String promoCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        double discountPercentage = 0;

        String query = "SELECT " + PROMOTION_CODE_COLUMN_DISCOUNT_PERCENTAGE + " FROM " + PROMOTION_CODE_TABLE_NAME +
                " WHERE " + PROMOTION_CODE_COLUMN_PROMO_CODE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{promoCode});

        if (cursor.moveToFirst()) {
            discountPercentage = cursor.getDouble(0);
        }

        cursor.close();
        db.close();

        return discountPercentage;
    }

    public boolean isValidPromotionCode(String promoCode) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + PROMOTION_CODE_TABLE_NAME +
                " WHERE " + PROMOTION_CODE_COLUMN_PROMO_CODE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{promoCode});

        boolean isValid = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return isValid;
    }

    public boolean addCourseRegistration(CourseRegisterModel courseRegisterModel,double courseFee,double discountAmount, double finalTotal){
        Log.d("DatabaseHelper", "Adding course registration to the database...");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COURSE_REGISTRATION_COLUMN_COURSE_NAME, courseRegisterModel.getCourse_name());
        cv.put(COURSE_REGISTRATION_COLUMN_COURSE_BRANCH, courseRegisterModel.getCourse_branch());
        cv.put(COURSE_REGISTRATION_COLUMN_FULL_NAME, courseRegisterModel.getName());
        cv.put(COURSE_REGISTRATION_COLUMN_NIC, courseRegisterModel.getNic());
        cv.put(COURSE_REGISTRATION_COLUMN_EMAIL, courseRegisterModel.getEmail());
        cv.put(COURSE_REGISTRATION_COLUMN_CONTACT_NO, courseRegisterModel.getTelephone());
        cv.put(COURSE_REGISTRATION_COLUMN_ADDRESS, courseRegisterModel.getAddress());
        cv.put(COURSE_REGISTRATION_COLUMN_PROMOTION_CODE, courseRegisterModel.getPromotionCode());
        cv.put(COURSE_REGISTRATION_COLUMN_COURSE_FEE, courseFee);
        cv.put(COURSE_REGISTRATION_COLUMN_DISCOUNT_AMOUNT, discountAmount);
        cv.put(COURSE_REGISTRATION_COLUMN_FINAL_TOTAL, finalTotal);

        try {
            long insert = db.insertOrThrow(COURSE_REGISTRATION_TABLE_NAME, null, cv);
            return insert != -1;
        } catch (android.database.SQLException e) {
            Log.e("DatabaseHelper", "Error inserting course registration: " + e.getMessage());
            return false;
        } finally {
            db.close(); // Close the database connection
        }
    }
    public CourseModel getCourseName(String courseName){
        SQLiteDatabase db = this.getReadableDatabase();
        CourseModel courseModel = null;
        String query = "SELECT * FROM " + COURSE_TABLE_NAME + " WHERE " + COURSE_COLUMN_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{courseName});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int courseId = cursor.getInt(cursor.getColumnIndex(COURSE_COLUMN_ID));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(COURSE_COLUMN_DESCRIPTION));
            @SuppressLint("Range") String courseBranch = cursor.getString(cursor.getColumnIndex(COURSE_COLUMN_BRANCH));
            @SuppressLint("Range") int availableSeats = cursor.getInt(cursor.getColumnIndex(COURSE_COLUMN_AVAILABLE_SEATS));
            @SuppressLint("Range") String registrationClosingDate = cursor.getString(cursor.getColumnIndex(COURSE_COLUMN_REGISTRATION_CLOSING_DATE));
            @SuppressLint("Range") String courseStartDate = cursor.getString(cursor.getColumnIndex(COURSE_COLUMN_COURSE_START_DATE));
            @SuppressLint("Range") String duration = cursor.getString(cursor.getColumnIndex(COURSE_COLUMN_DURATION));
            @SuppressLint("Range") String publishDate = cursor.getString(cursor.getColumnIndex(COURSE_COLUMN_PUBLISH_DATE));
            @SuppressLint("Range") double fee = cursor.getDouble(cursor.getColumnIndex(COURSE_COLUMN_FEE));
            @SuppressLint("Range") String instructor = cursor.getString(cursor.getColumnIndex(COURSE_COLUMN_INSTRUCTOR));

            courseModel = new CourseModel(courseId, courseName, description, courseBranch, availableSeats, registrationClosingDate, courseStartDate, duration, publishDate, fee, instructor);
        }
        cursor.close();
        db.close();
        return courseModel;
    }

    public double getCourseFeeByCourseId(int courseId) {
        SQLiteDatabase db = this.getReadableDatabase();
        double courseFee = 0;

        String query = "SELECT " + COURSE_COLUMN_FEE + " FROM " + COURSE_TABLE_NAME +
                " WHERE " + COURSE_COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(courseId)});

        if (cursor.moveToFirst()) {
            courseFee = cursor.getDouble(0);
        }

        cursor.close();
        db.close();

        return courseFee;
    }
    public boolean updateCourse(CourseModel course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COURSE_COLUMN_NAME, course.getName());
        values.put(COURSE_COLUMN_DESCRIPTION, course.getDescription());
        values.put(COURSE_COLUMN_BRANCH, course.getBranch());
        values.put(COURSE_COLUMN_AVAILABLE_SEATS, course.getAvailableSeats());
        values.put(COURSE_COLUMN_COURSE_START_DATE, course.getCourseStartDate());
        values.put(COURSE_COLUMN_REGISTRATION_CLOSING_DATE, course.getRegistrationClosingDate());
        values.put(COURSE_COLUMN_DURATION, course.getDuration());
        values.put(COURSE_COLUMN_PUBLISH_DATE, course.getPublishDate());
        values.put(COURSE_COLUMN_FEE, course.getFee());
        values.put(COURSE_COLUMN_INSTRUCTOR, course.getInstructor());

        // Log the courseId and ContentValues
        Log.d("DatabaseHelper", "Updating course with ID: " + course.getId());
        Log.d("DatabaseHelper", "ContentValues: " + values.toString());

        // Update the course record
        int rows = db.update(COURSE_TABLE_NAME, values, COURSE_COLUMN_ID + " = ?",
                new String[]{String.valueOf(course.getId())});
        db.close();
        return rows > 0;
    }
    public void deleteCourse(int courseId) {
        Log.d("Database", "Deleting course with ID: " + courseId); // Print courseId to log
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(COURSE_TABLE_NAME,  COURSE_COLUMN_ID + " = ?", new String[]{String.valueOf(courseId)});
        db.close(); // Closing database connection

        if (deletedRows > 0) {
            Log.d("Database", "Course deleted successfully");
        } else {
            Log.d("Database", "Failed to delete course");
}
}

    public CourseModel getCourse(int courseId) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Log the courseId
        Log.d(TAG, "Fetching details for course with ID: " + courseId);

        // Construct the SQL query
        String query = "SELECT * FROM " + COURSE_TABLE_NAME +
                " WHERE " + COURSE_COLUMN_ID + " = ?";

        // Log the SQL query
        Log.d(TAG, "SQL Query: " + query);

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(courseId)});

        CourseModel course = null;

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String courseName = cursor.getString(cursor.getColumnIndex(COURSE_COLUMN_NAME));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(COURSE_COLUMN_DESCRIPTION));
            @SuppressLint("Range") String courseBranch = cursor.getString(cursor.getColumnIndex(COURSE_COLUMN_BRANCH));
            @SuppressLint("Range") int availableSeats = cursor.getInt(cursor.getColumnIndex(COURSE_COLUMN_AVAILABLE_SEATS));
            @SuppressLint("Range") String registrationClosingDate = cursor.getString(cursor.getColumnIndex(COURSE_COLUMN_REGISTRATION_CLOSING_DATE));
            @SuppressLint("Range") String courseStartDate = cursor.getString(cursor.getColumnIndex(COURSE_COLUMN_COURSE_START_DATE));
            @SuppressLint("Range") String duration = cursor.getString(cursor.getColumnIndex(COURSE_COLUMN_DURATION));
            @SuppressLint("Range") String publishDate = cursor.getString(cursor.getColumnIndex(COURSE_COLUMN_PUBLISH_DATE));
            @SuppressLint("Range") double fee = cursor.getDouble(cursor.getColumnIndex(COURSE_COLUMN_FEE));
            @SuppressLint("Range") String instructor = cursor.getString(cursor.getColumnIndex(COURSE_COLUMN_INSTRUCTOR));
            course = new CourseModel(courseName, description, courseBranch, availableSeats, registrationClosingDate, courseStartDate, duration, publishDate, fee, instructor);
        } else {
            // Log if no course details found
            Log.d(TAG, "No course details found for course ID: " + courseId);
        }

        cursor.close();
        db.close();

        return course;
    }

}

