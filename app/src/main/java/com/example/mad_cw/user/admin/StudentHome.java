package com.example.mad_cw.user.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mad_cw.DatabaseHelper;
import com.example.mad_cw.R;
import com.example.mad_cw.admin.AdminHome;
import com.example.mad_cw.user.UserModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class StudentHome extends AppCompatActivity {

    FloatingActionButton studentEditFab, studentDeleteFab, mainFab;
    Boolean isAllFabsVisible;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private DatabaseHelper dbHelper;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ImageButton backButton = findViewById(R.id.back_button);

        listView = findViewById(R.id.viewcourse);
        dbHelper = new DatabaseHelper(this);
        SearchView searchView = findViewById(R.id.search_view);

        // Get list of users from the database
        List<UserModel> userList = dbHelper.getAllUsers();

        // Create a list of strings to hold the course names
        final List<String> userNic = new ArrayList<>();
        for (UserModel user : userList) {
            userNic.add(user.getNic());
        }

        // Set up adapter for the ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userNic);
        listView.setAdapter(adapter);

        // Set item click listener for the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the clicked course name
                String usersNic = userNic.get(position);

                // Find the corresponding Course object
                UserModel selectedNIC = null;
                for (UserModel user : userList) {
                    if (user.getNic().equals(usersNic)) {
                        selectedNIC = user;
                        break;
                    }
                }

                // Check if a course was found
                if (selectedNIC != null) {

                    // Create an Intent to start the Update activity
                    Intent intent = new Intent(StudentHome.this, StudentEdit.class);
                    // Pass the selected Course object's related data to the Update activity
                    intent.putExtra("user_name", selectedNIC.getName());
                    intent.putExtra("user_nic", selectedNIC.getNic());
                    intent.putExtra("user_email", selectedNIC.getEmail());
                    intent.putExtra("user_contactNo", selectedNIC.getTelephone());
                    intent.putExtra("user_address", selectedNIC.getAddress());

                    // Add more extras for other related data if needed
                    startActivity(intent);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentHome.this, AdminHome.class);
                startActivity(intent);
            }
        });

        mainFab = findViewById(R.id.fab);
        studentEditFab = findViewById(R.id.edit_student);
        studentDeleteFab = findViewById(R.id.student_delete);

        // set all the FABs and all the action name texts as GONE
        studentEditFab.setVisibility(View.GONE);
        studentDeleteFab.setVisibility(View.GONE);

        isAllFabsVisible = false;

        mainFab.setOnClickListener(view -> {
            if (!isAllFabsVisible) {
                // when isAllFabsVisible becomes true make all
                // the action name texts and FABs VISIBLE
                studentEditFab.show();
                studentDeleteFab.show();

                // make the boolean variable true as we
                // have set the sub FABs visibility to GONE
                isAllFabsVisible = true;
            } else {
                // when isAllFabsVisible becomes true make
                // all the action name texts and FABs GONE.
                studentEditFab.hide();
                studentDeleteFab.hide();

                // make the boolean variable false as we
                // have set the sub FABs visibility to GONE
                isAllFabsVisible = false;
            }
        });

        studentEditFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentHome.this, StudentEdit.class);
                startActivity(intent);
            }
        });

        studentDeleteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentHome.this, StudentEdit.class);
                startActivity(intent);
            }
        });



    }
}