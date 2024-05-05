package com.example.mad_cw.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mad_cw.DatabaseHelper;
import com.example.mad_cw.R;
import com.example.mad_cw.course.CourseModel;
import com.example.mad_cw.course.admin.AddCourse;
import com.example.mad_cw.course.admin.DeleteCourse;
import com.example.mad_cw.course.admin.EditCourse;
import com.example.mad_cw.user.admin.StudentHome;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AdminHome extends AppCompatActivity {

    FloatingActionButton courseAddFab, courseEditFab, courseDeleteFab, mainFab;

    Boolean isAllFabsVisible;
    Button studentButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private DatabaseHelper dbHelper;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AdminModel adminModel = (AdminModel) getIntent().getSerializableExtra("admin");

        listView = findViewById(R.id.viewcourse);
        dbHelper = new DatabaseHelper(this);
        SearchView searchView = findViewById(R.id.search_view);

        // Get list of courses from the database
        List<CourseModel> courseList = dbHelper.getAllCourses();

        // Create a list of strings to hold the course IDs and names
        final List<String> courseInfoList = new ArrayList<>();
        for (CourseModel course : courseList) {
            // Format the course ID and name as a single string
            String courseInfo = String.format("%d: %s", course.getId(), course.getName());
            courseInfoList.add(courseInfo);
        }

        // Set up adapter for the ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseInfoList);
        listView.setAdapter(adapter);

        // Set item click listener for the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the clicked course info (ID and name)
                String courseInfo = courseInfoList.get(position);
                // Split the course info string to extract ID and name
                String[] parts = courseInfo.split(": ");
                int courseId = Integer.parseInt(parts[0]);
                String courseName = parts[1];

                // Find the corresponding Course object
                CourseModel selectedCourse = null;
                for (CourseModel course : courseList) {
                    if (course.getId() == courseId && course.getName().equals(courseName)) {
                        selectedCourse = course;
                        break;
                    }
                }

                // Check if a course was found
                if (selectedCourse != null) {
                    // Create an Intent to start the Update activity
                    Intent intent = new Intent(AdminHome.this, EditCourse.class);
                    // Pass the selected Course object's related data to the Update activity
                    intent.putExtra("course_id", selectedCourse.getId());
                    intent.putExtra("course_name", selectedCourse.getName());
                    intent.putExtra("course_description", selectedCourse.getDescription());
                    intent.putExtra("course_branches", selectedCourse.getBranch());
                    intent.putExtra("available_seats", selectedCourse.getAvailableSeats());
                    intent.putExtra("course_StartingDate", selectedCourse.getCourseStartDate());
                    intent.putExtra("course_registrationClosingDate", selectedCourse.getRegistrationClosingDate());
                    intent.putExtra("course_duration", selectedCourse.getDuration());
                    intent.putExtra("course_PublishedDate", selectedCourse.getPublishDate());
                    intent.putExtra("course_fee", selectedCourse.getFee());
                    intent.putExtra("instructor", selectedCourse.getInstructor());

                    // Add more extras for other related data if needed
                    startActivity(intent);
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Search for the entered course name in the database
                searchCourse(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mainFab = findViewById(R.id.fab);
        courseAddFab = findViewById(R.id.add_course_fab);
        courseEditFab = findViewById(R.id.edit_course);
        courseDeleteFab = findViewById(R.id.course_delete);
        studentButton = findViewById(R.id.student_button);

        // set all the FABs and all the action name texts as GONE
        courseAddFab.setVisibility(View.GONE);
        courseEditFab.setVisibility(View.GONE);
        courseDeleteFab.setVisibility(View.GONE);

        isAllFabsVisible = false;

        mainFab.setOnClickListener(view -> {
            if (!isAllFabsVisible) {
                // when isAllFabsVisible becomes true make all
                // the action name texts and FABs VISIBLE
                courseAddFab.show();
                courseEditFab.show();
                courseDeleteFab.show();

                // make the boolean variable true as we
                // have set the sub FABs visibility to GONE
                isAllFabsVisible = true;
            } else {
                // when isAllFabsVisible becomes true make
                // all the action name texts and FABs GONE.
                courseAddFab.hide();
                courseEditFab.hide();
                courseDeleteFab.hide();

                // make the boolean variable false as we
                // have set the sub FABs visibility to GONE
                isAllFabsVisible = false;
            }
        });

        courseAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, AddCourse.class);
                startActivity(intent);
            }
        });

        courseDeleteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, DeleteCourse.class);
                startActivity(intent);
            }
        });

        studentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, StudentHome.class);
                startActivity(intent);
            }
        });

    }

    private void searchCourse(String query) {
        CourseModel course = dbHelper.getCourseName(query);
        if (course != null) {
            // Display the details of the found course
            Toast.makeText(this, "Course Found: " + course.getName(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Course Not Found", Toast.LENGTH_SHORT).show();
        }
    }

        @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AdminHome.this, AdminLogin.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK); // Clear the back stack
        startActivity(intent);
        }
}
