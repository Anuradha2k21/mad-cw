package com.example.mad_cw.course;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mad_cw.DatabaseHelper;
import com.example.mad_cw.R;
import com.example.mad_cw.map.MapActivity;
import com.example.mad_cw.user.UserLogin;
import com.example.mad_cw.user.UserModel;

import java.sql.SQLException;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class CourseRegistrationForm extends AppCompatActivity {

    private static final String TAG = "CourseRegistrationForm"; // Define a tag for logging
    private EditText etCourseName, etFullName, etNIC, etEmail, etContactNo, etAddress,etPromotionCode;
    private Spinner spinnerBranch;
    DatabaseHelper dbHelper;
    private static final String CHANNEL_ID = "course_registration_channel";
    private UserModel userModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course_registration_form);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userModel = (UserModel) getIntent().getSerializableExtra("user");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ImageButton backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseRegistrationForm.this, CourseRecyclerView.class);
                startActivity(intent);
            }
        });

        Log.d(TAG, "onCreate() method called");

        spinnerBranch = findViewById(R.id.spinner_branch);

        etCourseName = findViewById(R.id.edit_text_course_name);
        etFullName = findViewById(R.id.edit_text_full_name);
        etNIC = findViewById(R.id.edit_text_NIC);
        etEmail = findViewById(R.id.txt_email);
        etContactNo = findViewById(R.id.txt_tp);
        etAddress = findViewById(R.id.txt_address);
        etPromotionCode = findViewById(R.id.txt_promo);

        Button btnSubmit = findViewById(R.id.btn_submit);

        createNotificationChannel();
        checkNotificationPermission();

        dbHelper = new DatabaseHelper(this);
        dbHelper.addDummyPromotionCode();

        TextView linkNearbyBranches = findViewById(R.id.link_nearby_branches);

        // Receive the course name passed from CourseAdapter
        String courseName = getIntent().getStringExtra("course_name");
        etCourseName.setText(courseName);
        etCourseName.setEnabled(false); // Make it uneditable

        SpannableString content = new SpannableString( "Find the nearest branch" ) ;
        content.setSpan( new UnderlineSpan() , 0 , content.length() , 0 ) ;
        linkNearbyBranches.setText(content) ;

        linkNearbyBranches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseRegistrationForm.this, MapActivity.class);
                startActivity(intent);
            }
        });

        // Creating adapter for spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.branchesArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Setting adapter to spinner
        spinnerBranch.setAdapter(adapter);

        // Set listener for spinner item selection
        spinnerBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Validate spinner selection
                validateSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing if nothing is selected
            }
        });

        // Retrieve the courseId from the Intent
        int courseId = getIntent().getIntExtra("course_id", -1);
        if (courseId != -1) {
            double courseFee = getCourseFee(courseId);
            // Use courseFee as needed
        } else {
            Log.e(TAG, "Invalid course id");
            // Handle the case where courseId is not passed correctly
        }



        btnSubmit.setOnClickListener(v -> {
            try {
                Log.d(TAG, "Submit button clicked");
                validate();
            } catch (Exception e) {
                Toast.makeText(this, "There's an error processing your request " + e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error processing request: " + e.getMessage());
            }

        });

    }



    private void validateSpinner() {
        String selectedBranch = spinnerBranch.getSelectedItem().toString();
        if (selectedBranch.equals(getString(R.string.select_branch))) {
            // Handle invalid selection
            spinnerBranch.setTop(Integer.parseInt("Please select a branch"));
            spinnerBranch.requestFocus();
        }
    }

    private void validate() throws SQLException {
        Log.d(TAG, "validate() method called");

        String emailPattern = "^[a-zA-Z0-9+_.-]{3,32}+@[a-zA-Z0-9.-]{2,32}+$";
        String nicPattern = "^(?:\\d{9}[vV]|\\d{12})$";
        String tpPattern = "^[0]{1}[7]{1}[01245678]{1}\\s?[0-9]{3}\\s?[0-9]{4}$";

        if (etFullName.getText().toString().isEmpty()) {
            etFullName.setError("Name is required");
            etFullName.requestFocus();
        }
        else if (etFullName.getText().toString().length() < 3 ){
            etFullName.setError("Name is too short. Please enter First and last name.");
            etFullName.requestFocus();
        }
        else if (etFullName.getText().toString().length() > 25) {
            etFullName.setError("Name is too long. Please enter First and last name only.");
            etFullName.requestFocus();
        }
        else if (etNIC.getText().toString().isEmpty()) {
            etNIC.setError("NIC number is required");
            etNIC.requestFocus();
        }
        else if(!etNIC.getText().toString().matches(nicPattern)) {
            etNIC.setError("Invalid NIC number");
            etNIC.requestFocus();
        }
        else if (etEmail.getText().toString().isEmpty()) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
        }
        else if(!etEmail.getText().toString().matches(emailPattern)) {
            etEmail.setError("Invalid Email");
            etEmail.requestFocus();
        }
        else if (etContactNo.getText().toString().isEmpty()) {
            etContactNo.setError("Phone number is required");
            etContactNo.requestFocus();
        }
        else if(!etContactNo.getText().toString().matches(tpPattern)) {
            etContactNo.setError("Invalid Phone number");
            etContactNo.requestFocus();
        }
        else if (etAddress.getText().toString().isEmpty()) {
            etAddress.setError("Address is required");
            etAddress.requestFocus();
        }
        else if (etAddress.getText().toString().isEmpty()) {
            etAddress.setError("Address is required");
            etAddress.requestFocus();
        }
        else if (etAddress.getText().toString().length() < 3 ){
            etAddress.setError("Invalid Address.");
            etAddress.requestFocus();
        }
        else if (etAddress.getText().toString().length() > 25) {
            etAddress.setError("Address length is too long. Please Check Again.");
            etAddress.requestFocus();
        }
        else {
            processRegister();
        }
    }

    private void processRegister() throws SQLException {

        Log.d("CourseRegistrationForm", "Processing course registration...");
        CourseRegisterModel courseRegisterModel = new CourseRegisterModel();

        // Fetch course fee
        Log.d(TAG, "Fetching course fee...");
        double courseFee = 0;
        int courseId = getIntent().getIntExtra("course_id", -1);

        if (courseId != -1) {
            courseFee = getCourseFee(courseId);
            Log.d(TAG, "Course fee fetched: " + courseFee);
            // Use courseFee as needed
        } else {
            Log.e(TAG, "Invalid course id");
            // Handle the case where courseId is not passed correctly
        }

        double discountAmount = 0;
        double finalTotal;
        // Check promotion code and calculate discount amount and final total
        if (handlePromotionCode(etPromotionCode.getText().toString().trim())) {
            Log.d(TAG, "Promotion code is valid");
            // If valid promotion code, adjust discount and final total
            double discountPercentage = dbHelper.getDiscountPercentageByPromoCode(etPromotionCode.getText().toString().trim());
            discountAmount = courseFee * (discountPercentage / 100);
            Log.d(TAG, "Discount amount calculated: " + discountAmount);
            finalTotal = courseFee - discountAmount;
            // Save discount amount and final total to database
            courseRegisterModel.setDiscountAmount(discountAmount);
            Log.d(TAG, "Final total calculated: " + finalTotal);
            courseRegisterModel.setFinalTotal(finalTotal);
        }
        else if (etPromotionCode.getText().toString().trim().isEmpty()){
            finalTotal = courseFee - discountAmount;
            courseRegisterModel.setDiscountAmount(discountAmount);
            courseRegisterModel.setFinalTotal(finalTotal);
        }
        else {
            // Invalid promotion code
            Log.d(TAG, "Invalid promotion code entered");
            Toast.makeText(this, "Invalid promotion code.", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d(TAG, "Saving registration data to database...");
        saveDataToDatabase(courseFee,discountAmount,finalTotal);
    }

    private double getCourseFee(int courseId) {
        dbHelper = new DatabaseHelper(this);
        return dbHelper.getCourseFeeByCourseId(courseId);
    }

    private boolean handlePromotionCode(String promoCode) {
        return dbHelper.isValidPromotionCode(promoCode);
    }

    private void saveDataToDatabase(double courseFee,double discountAmount, double finalTotal){
        // Create a CourseRegisterModel object and set its values from intent data
        CourseRegisterModel courseRegisterModel = new CourseRegisterModel();
        courseRegisterModel.setCourse_name(etCourseName.getText().toString());
        courseRegisterModel.setCourse_branch(spinnerBranch.getSelectedItem().toString());
        courseRegisterModel.setName(etFullName.getText().toString());
        courseRegisterModel.setNic(etNIC.getText().toString());
        courseRegisterModel.setEmail(etEmail.getText().toString());
        courseRegisterModel.setTelephone(etContactNo.getText().toString());
        courseRegisterModel.setAddress(etAddress.getText().toString());
        courseRegisterModel.setPromotionCode(etPromotionCode.getText().toString());
        courseRegisterModel.setCourseAmount(courseFee);

          //Save the data to the database
          boolean isSaved = dbHelper.addCourseRegistration(courseRegisterModel,courseFee,discountAmount,finalTotal);

          if (isSaved) {
            Toast.makeText(this, "Registration saved successfully.", Toast.LENGTH_SHORT).show();
              Intent intent = new Intent(CourseRegistrationForm.this, CourseConfirmation.class);
              intent.putExtra(CourseConfirmation.COURSE_NAME, etCourseName.getText().toString());
              intent.putExtra(CourseConfirmation.BRANCH, spinnerBranch.getSelectedItem().toString());
              intent.putExtra(CourseConfirmation.NAME, etFullName.getText().toString());
              intent.putExtra(CourseConfirmation.NIC, etNIC.getText().toString());
              intent.putExtra(CourseConfirmation.EMAIL, etEmail.getText().toString());
              intent.putExtra(CourseConfirmation.CONTACT_NO, etContactNo.getText().toString());
              intent.putExtra(CourseConfirmation.ADDRESS, etAddress.getText().toString());
              intent.putExtra(CourseConfirmation.DISCOUNT_AMOUNT, discountAmount);
              intent.putExtra(CourseConfirmation.COURSE_FEE, courseRegisterModel.getCourseAmount());
              intent.putExtra(CourseConfirmation.FINAL_TOTAL, finalTotal);
              intent.putExtra("user", userModel);
              startActivity(intent);

              ConfirmationEmail confirmationEmail = new ConfirmationEmail();
              confirmationEmail.sendEmail(etCourseName.getText().toString(), etEmail.getText().toString());

              showRegistrationSuccessNotification();

//              Intent intent1 = new Intent(getApplicationContext(), CourseRecyclerView.class);
//              intent1.putExtra("user", userModel);
//              startActivity(intent1);
              finish();

          }
          else {
            Toast.makeText(this, "Failed to save registration.", Toast.LENGTH_SHORT).show();
          }
    }
    //  1. Create a notification channel
    //  2. Check notification permission
    //  3. Show a notification
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Course Registration Channel";
            String description = "Channel for course registration notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showRegistrationSuccessNotification() {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.splash)
                    .setContentTitle("Course Registration")
                    .setContentText("Successfully registered for the course")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(1, builder.build());

    }

    private void checkNotificationPermission() {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (!notificationManager.areNotificationsEnabled()) {
            // Notifications are not enabled. Navigate the user to the settings page.
            Intent intent = new Intent();
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");

            //for Android 5-7
            intent.putExtra("app_package", getPackageName());
            intent.putExtra("app_uid", getApplicationInfo().uid);

            // for Android 8 and above
            intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());

            Toast.makeText(this, "Please allow notification permissions", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    }

}