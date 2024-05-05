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

public class EditCourse extends AppCompatActivity {
    int courseId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_course);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ImageButton backButton = findViewById(R.id.back_button);
        Button btnUpdate = findViewById(R.id.btn_update);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditCourse.this, AdminHome.class);
                startActivity(intent);
            }
        });

        // Retrieve data from Intent extras
        courseId = getIntent().getIntExtra("course_id", -1);
        String courseName = getIntent().getStringExtra("course_name");
        String courseDescription = getIntent().getStringExtra("course_description");
        String courseBranches = getIntent().getStringExtra("course_branches");
        int availableSeats = getIntent().getIntExtra("available_seats", 0);
        String courseStartingDate = getIntent().getStringExtra("course_StartingDate");
        String courseClosingDate = getIntent().getStringExtra("course_registrationClosingDate");
        String courseDuration = getIntent().getStringExtra("course_duration");
        String coursePublishedDate = getIntent().getStringExtra("course_PublishedDate");
        double courseFee = getIntent().getDoubleExtra("course_fee", 0.0);
        String instructor= getIntent().getStringExtra("instructor");

        // Populate EditText fields with the received data
        EditText courseIdEditText = findViewById(R.id.edit_text_course_name);
        EditText courseDescriptionEditText = findViewById(R.id.edit_text_course_description);
        EditText branchesEditText = findViewById(R.id.edit_text_course_branch);
        EditText courseAvailableSeatsEditText = findViewById(R.id.edit_text_available_seats);
        EditText startingDateEditText = findViewById(R.id.edit_text_start_date);
        EditText closingDateEditText = findViewById(R.id.edit_text_closing_date);
        EditText durationEditText = findViewById(R.id.edit_text_duration);
        EditText publishedDateEditText = findViewById(R.id.edit_text_publish_date);
        EditText courseFeeEditText = findViewById(R.id.edit_text_course_fee);
        EditText instructorEditText = findViewById(R.id.edit_text_instructor);

        courseIdEditText.setText(String.valueOf(courseName));  // Course name in courseIdEditText?
        courseDescriptionEditText.setText(String.valueOf(courseDescription));
        branchesEditText.setText(String.valueOf(courseBranches));
        courseAvailableSeatsEditText.setText(String.valueOf(availableSeats));
        startingDateEditText.setText(courseStartingDate);
        closingDateEditText.setText(courseClosingDate);
        durationEditText.setText(String.valueOf(courseDuration));
        publishedDateEditText.setText(coursePublishedDate);
        courseFeeEditText.setText(String.valueOf(courseFee));
        instructorEditText.setText(String.valueOf(instructor));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Retrieve updated values from EditText fields
                    String updatedCourseName = courseIdEditText.getText().toString();
                    String updatedDescription = courseDescriptionEditText.getText().toString();
                    String updatedCourseBranches = branchesEditText.getText().toString();
                    int updatedAvailableSeats = Integer.parseInt(courseAvailableSeatsEditText.getText().toString());
                    String updatedCourseStartingDate = startingDateEditText.getText().toString();
                    String updatedCourseClosingDate = closingDateEditText.getText().toString();
                    String updatedCourseDuration = durationEditText.getText().toString();
                    String updatedCoursePublishedDate = publishedDateEditText.getText().toString();
                    double updatedCourseFee = Double.parseDouble(courseFeeEditText.getText().toString());
                    String updatedInstructor = instructorEditText.getText().toString();

                    // Create a Course object with the updated values
                    CourseModel updatedCourse = new CourseModel();
                    updatedCourse.setId(courseId); // Make sure courseId is correct
                    updatedCourse.setName(updatedCourseName);
                    updatedCourse.setDescription(updatedDescription);
                    updatedCourse.setBranch(updatedCourseBranches);
                    updatedCourse.setAvailableSeats(updatedAvailableSeats);
                    updatedCourse.setCourseStartDate(updatedCourseStartingDate);
                    updatedCourse.setRegistrationClosingDate(updatedCourseClosingDate);
                    updatedCourse.setDuration(updatedCourseDuration);
                    updatedCourse.setPublishDate(updatedCoursePublishedDate);
                    updatedCourse.setFee(updatedCourseFee);
                    updatedCourse.setInstructor(updatedInstructor);

                    DatabaseHelper dbHelper = new DatabaseHelper(EditCourse.this);
                    // Perform update operation using DatabaseHelper
                    boolean result = dbHelper.updateCourse(updatedCourse);

                    // Display a toast message indicating successful update
                    if (result) {
                        Toast.makeText(EditCourse.this, "Course updated successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditCourse.this, AdminHome.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(EditCourse.this, "Failed to update course", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(EditCourse.this, "Error: Please enter valid numbers", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}