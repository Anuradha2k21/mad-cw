package com.example.mad_cw.course.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mad_cw.DatabaseHelper;
import com.example.mad_cw.R;
import com.example.mad_cw.admin.AdminHome;

public class DeleteCourse extends AppCompatActivity {

    private EditText courseNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_course);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        courseNameEditText = findViewById(R.id.edit_text_course_name);

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the activity and return to the previous one
            }
        });

        Button deleteButton = findViewById(R.id.btn_delete);
        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String courseIdString = courseNameEditText.getText().toString(); if (!courseIdString.isEmpty()) {
                    // Convert the course ID to an integer
                    int courseId = Integer.parseInt(courseIdString);

                    // Create an instance of DatabaseHelper
                    DatabaseHelper dbHelper = new DatabaseHelper(DeleteCourse.this);

                    // Perform delete operation using dbHelper instance
                    dbHelper.deleteCourse(courseId);

                    // Close the dbHelper instance after use
                    dbHelper.close();

                    // Display a toast message indicating successful deletion
                    Toast.makeText(DeleteCourse.this, "Course deleted successfully", Toast.LENGTH_SHORT).show();
                    clearFields();
                    Intent intent = new Intent(DeleteCourse.this, AdminHome.class);
                    startActivity(intent);
                } else {
                    // If the course ID is empty, display an error message
                    Toast.makeText(DeleteCourse.this, "Please enter a valid course ID", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void clearFields() {
        courseNameEditText.setText("");
        // Clear other EditText fields similarly
    }

}