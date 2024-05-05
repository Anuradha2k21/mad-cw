package com.example.mad_cw.course.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mad_cw.DatabaseHelper;
import com.example.mad_cw.R;
import com.example.mad_cw.admin.AdminHome;
import com.example.mad_cw.course.CourseModel;

import java.sql.SQLException;

public class AddCourse extends AppCompatActivity {

    private EditText etCourseName, etDescription, etBranch, etAvailableSeats, etClosingDate, etStartingDate,etPublishDate, etDuration, etCourseFee, etInstructor;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_course);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etCourseName = findViewById(R.id.edit_text_course_name);
        etDescription = findViewById(R.id.edit_text_course_description);
        etBranch = findViewById(R.id.edit_text_course_branch);
        etAvailableSeats = findViewById(R.id.edit_text_available_seats);
        etClosingDate = findViewById(R.id.edit_text_closing_date);
        etStartingDate = findViewById(R.id.edit_text_start_date);
        etPublishDate = findViewById(R.id.edit_text_publish_date);
        etDuration = findViewById(R.id.edit_text_duration);
        etInstructor = findViewById(R.id.edit_text_instructor);
        etCourseFee = findViewById(R.id.edit_text_course_fee);

        Button btnSave = findViewById(R.id.btn_save);

        dbHelper = new DatabaseHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ImageButton backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCourse.this, AdminHome.class);
                startActivity(intent);
            }
        });

        btnSave.setOnClickListener(v -> {
            try {
                validate();
            } catch (Exception e) {
                Toast.makeText(this, "There's an error processing your request " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });


    }

    private void validate() throws SQLException {

        if (etCourseName.getText().toString().isEmpty()) {
            etCourseName.setError("CourseName is required");
            etCourseName.requestFocus();
        }
        else if (etDescription.getText().toString().isEmpty()) {
            etDescription.setError("Description is required");
            etDescription.requestFocus();
        }
        else if (etBranch.getText().toString().isEmpty()) {
            etBranch.setError("Branch is required");
            etBranch.requestFocus();
        }
        else if (etAvailableSeats.getText().toString().isEmpty()) {
            etAvailableSeats.setError("Available Seats is required");
            etAvailableSeats.requestFocus();
        }
        else if (etClosingDate.getText().toString().isEmpty()) {
            etClosingDate.setError("Closing Date is required");
            etClosingDate.requestFocus();
        }
        else if (etStartingDate.getText().toString().isEmpty()){
            etStartingDate.setError("Starting Date Address.");
            etStartingDate.requestFocus();
        }
        else if (etDuration.getText().toString().isEmpty()) {
            etDuration.setError("Duration is required.");
            etDuration.requestFocus();
        }
        else if (etPublishDate.getText().toString().isEmpty()) {
            etPublishDate.setError("Publish date is required.");
            etPublishDate.requestFocus();
        }
        else if (etCourseFee.getText().toString().isEmpty()) {
            etCourseFee.setError("Course Fee is required.");
            etCourseFee.requestFocus();
        }
        else if (etInstructor.getText().toString().isEmpty()) {
            etInstructor.setError("Instructor is required.");
            etInstructor.requestFocus();
        }
        else {
            saveCourse();
        }
    }

    private void saveCourse() {

        // Create CourseModel object
        CourseModel courseModel = new CourseModel();

        courseModel.setName(etCourseName.getText().toString());
        courseModel.setDescription((etDescription.getText().toString()));
        courseModel.setBranch(etBranch.getText().toString());
        courseModel.setAvailableSeats(Integer.parseInt(etAvailableSeats.getText().toString()));
        courseModel.setRegistrationClosingDate(etClosingDate.getText().toString());
        courseModel.setCourseStartDate(etStartingDate.getText().toString());
        courseModel.setDuration(etDuration.getText().toString());
        courseModel.setPublishDate(etPublishDate.getText().toString());
        courseModel.setFee(Double.parseDouble(etCourseFee.getText().toString()));
        courseModel.setInstructor(etInstructor.getText().toString());

        // Add the course to the database
        boolean isSuccess = dbHelper.addOneCourse(courseModel);

        // Handle insertion result
        if (isSuccess) {
            Toast.makeText(this, "Course added successfully", Toast.LENGTH_SHORT).show();
            // Optionally, clear EditText fields after successful insertion
            clearFields();
            Intent intent = new Intent(AddCourse.this, AdminHome.class);
            finish();
            startActivity(intent);
        } else {
            Toast.makeText(this, "Failed to add course", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        etCourseName.setText("");
        etDescription.setText("");
        etBranch.setText("");
        etDuration.setText("");
        etClosingDate.setText("");
        etStartingDate.setText("");
        etAvailableSeats.setText("");
        etPublishDate.setText("");
        etDuration.setText("");
        etCourseFee.setText("");
        etInstructor.setText("");
        // Clear other EditText fields similarly
    }
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent = new Intent(AddCourse.this, YourNewActivity.class);
//        startActivity(intent);
//        finish();
//    }
}