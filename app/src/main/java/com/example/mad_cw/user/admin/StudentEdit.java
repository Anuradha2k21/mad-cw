package com.example.mad_cw.user.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mad_cw.DatabaseHelper;
import com.example.mad_cw.R;
import com.example.mad_cw.user.UserModel;

public class StudentEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_edit);
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
                Intent intent = new Intent(StudentEdit.this, StudentHome.class);
                startActivity(intent);
            }
        });

        String userName = getIntent().getStringExtra("user_name");
        String userNic = getIntent().getStringExtra("user_nic");
        String userEmail = getIntent().getStringExtra("user_email");
        String userTp = getIntent().getStringExtra("user_contactNo");
        String userAddress = getIntent().getStringExtra("user_address");

        EditText userNameEditText = findViewById(R.id.edit_text_full_name);
        EditText userNicEditText = findViewById(R.id.edit_text_NIC);
        EditText userEmailEditText = findViewById(R.id.edit_text_email);
        EditText userTpEditText = findViewById(R.id.edit_text_tp);
        EditText userAddressEditText = findViewById(R.id.edit_text_address);

        userNameEditText.setText(String.valueOf(userName));  // Course name in courseIdEditText?
        userNicEditText.setText(String.valueOf(userNic));
        userEmailEditText.setText(String.valueOf(userEmail));
        userTpEditText.setText(String.valueOf(userTp));
        userAddressEditText.setText(userAddress);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Retrieve updated values from EditText fields
                String updatedUserName = userNameEditText.getText().toString();
                String updatedUserNic= userNicEditText.getText().toString();
                String updatedUserEmail = userEmailEditText.getText().toString();
                String updatedUserTp = userTpEditText.getText().toString();
                String updatedUserAddress = userAddressEditText.getText().toString();


                // Create a Course object with the updated values
                UserModel updateduser = new UserModel();
                updateduser.setName(updatedUserName);
                updateduser.setNic(updatedUserNic);
                updateduser.setEmail(updatedUserEmail);
                updateduser.setTelephone(updatedUserTp);
                updateduser.setAddress(updatedUserAddress);

                DatabaseHelper dbHelper = new DatabaseHelper(StudentEdit.this);
                // Perform update operation using DatabaseHelper
               // dbHelper.updateUser(updateduser);

                // Close the dbHelper instance after use
                dbHelper.close();

                // Display a toast message indicating successful update
//                Toast.makeText(EditCourse.this, "Course updated successfully", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(EditCourse.this, AdminHome.class);
//                startActivity(intent);
            }
        });
    }
}