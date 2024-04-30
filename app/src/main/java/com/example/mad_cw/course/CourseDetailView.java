package com.example.mad_cw.course;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mad_cw.DatabaseHelper;
import com.example.mad_cw.R;
import com.example.mad_cw.course.CourseModel;

public class CourseDetailView extends Activity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view);

        // Initialize TextViews
        TextView nameTextView = findViewById(R.id.name_text_view);
        TextView branchTextView = findViewById(R.id.branch_text_view);
        TextView courseStartDateTextView = findViewById(R.id.course_start_date_text_view);
        TextView publishDateTextView = findViewById(R.id.publish_date_text_view);
        TextView registrationClosingDateTextView = findViewById(R.id.registration_closing_date_text_view);
        TextView descriptionTextView = findViewById(R.id.description_text_view);
        TextView instructorTextView = findViewById(R.id.instructor_text_view);
        TextView feeTextView = findViewById(R.id.fee_text_view);
        TextView availableSeatsTextView = findViewById(R.id.available_seat_text_view);
        TextView durationTextView = findViewById(R.id.duration_text_view);

        // Instantiate your database helper class
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // Get the course ID from intent extras
        int courseId = (int) getIntent().getIntExtra("course_id", -1);

        // Retrieve course details from the database based on course ID
        CourseModel courseModel = dbHelper.getCourse(courseId);

        // Set course details to TextViews
        if (courseModel != null) {
            nameTextView.setText("Course Name: " + courseModel.getName());
            branchTextView.setText("Branch: " + courseModel.getBranch());
            courseStartDateTextView.setText("Starting Date: " + courseModel.getCourseStartDate());
            publishDateTextView.setText("Publish Date: " + courseModel.getPublishDate());
            registrationClosingDateTextView.setText("Registration Due Date: " + courseModel.getRegistrationClosingDate());
            descriptionTextView.setText("Description: " + courseModel.getDescription());
            instructorTextView.setText("Instructor: " + courseModel.getInstructor());
            feeTextView.setText("Course Fee: " + courseModel.getFee());
            availableSeatsTextView.setText("Available Seats: " + courseModel.getAvailableSeats());
            durationTextView.setText("Duration: " + courseModel.getDuration());
        }

        // Set click listener for the "Back" button
        Button backButton = findViewById(R.id.button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous activity
                finish();
            }
        });
    }
}
