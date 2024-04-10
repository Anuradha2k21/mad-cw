package com.example.mad_cw.course;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad_cw.DatabaseHelper;
import com.example.mad_cw.R;
import com.example.mad_cw.admin.AdminModel;
import com.example.mad_cw.user.UserLogin;
import com.example.mad_cw.user.UserModel;

import java.util.ArrayList;

public class CourseRecyclerView extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseHelper courseDatabaseHelper;
    ArrayList<CourseModel> courseList;
    private Toolbar toolbar;
    private ImageButton backButton, optionsButton;
    UserModel userModel;
    AdminModel adminModel;
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

        Intent intent = getIntent();
        userModel = (UserModel) intent.getSerializableExtra("user");
        adminModel = (AdminModel) intent.getSerializableExtra("admin");

        recyclerView = findViewById(R.id.course_recycler_view);
        courseDatabaseHelper = new DatabaseHelper(this);
        courseDatabaseHelper.addDummyCourses();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        backButton = findViewById(R.id.back_button);
        optionsButton = findViewById(R.id.options_button);

        loadCourses();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle back button click
            }
        });

        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserLogin", MODE_PRIVATE);
                if (sharedPreferences.contains("Email") && sharedPreferences.contains("Password")) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("Email");
                    editor.remove("Password");
                    editor.apply();
                }

                Intent intent = new Intent(getApplicationContext(), UserLogin.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //  clear all activities on top of UserLogin activity
                startActivity(intent);
                Toast.makeText(CourseRecyclerView.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            }
        });
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