package com.example.mad_cw.course;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad_cw.R;

import java.util.ArrayList;

public class CourseRecyclerView extends AppCompatActivity {
    RecyclerView recyclerView;
    CourseDatabaseHelper courseDatabaseHelper;
    ArrayList<CourseModel> courseList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course_recycler_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.course_recycler_view);
        courseDatabaseHelper = new CourseDatabaseHelper(this);
        courseDatabaseHelper.insertDummyData();
        loadCourses();
    }

    private void loadCourses() {
        courseList = courseDatabaseHelper.getAllCourses();

        if (courseList.isEmpty()) {
            Toast.makeText(this, "No Courses available", Toast.LENGTH_SHORT).show();
        } else {
            // Create an adapter and set it to the RecyclerView
            CourseAdapter courseAdapter = new CourseAdapter(this, courseList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(courseAdapter);
        }
    }
}