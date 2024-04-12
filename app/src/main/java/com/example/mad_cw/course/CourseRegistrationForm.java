package com.example.mad_cw.course;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mad_cw.R;
import com.example.mad_cw.map.MapActivity;
import com.example.mad_cw.user.UserConfirmation;

public class CourseRegistrationForm extends AppCompatActivity {

    private EditText etCourseName, etFullName, etNIC, etEmail, etContactNo, etAddress,etPromotionCode;
    private Spinner spinnerBranch;
    private Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course_registration_form);

        spinnerBranch = findViewById(R.id.spinner_branch);

        etCourseName = findViewById(R.id.edit_text_course_name);
        etFullName = findViewById(R.id.edit_text_full_name);
        etNIC = findViewById(R.id.edit_text_NIC);
        etEmail = findViewById(R.id.txt_email);
        etContactNo = findViewById(R.id.txt_tp);
        etAddress = findViewById(R.id.txt_address);
        etPromotionCode = findViewById(R.id.txt_promo);

        btnSubmit = findViewById(R.id.btn_submit);

        TextView linkNearbyBranches = findViewById(R.id.link_nearby_branches);

        // Receive the course name passed from CourseAdapter
        String courseName = getIntent().getStringExtra("course_name");
        etCourseName.setText(courseName);
        etCourseName.setEnabled(false); // Make it uneditable

        SpannableString content = new SpannableString( "Fine nearby branch" ) ;
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

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
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

    private void validate() {

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

    private void processRegister() {
        CourseRegisterModel courseRegisterModel = new CourseRegisterModel();
        courseRegisterModel.setName(etFullName.getText().toString());
        courseRegisterModel.setNic(etNIC.getText().toString());
        courseRegisterModel.setEmail(etEmail.getText().toString());
        courseRegisterModel.setTelephone(etContactNo.getText().toString());
        courseRegisterModel.setAddress(etAddress.getText().toString());

        Intent intent = new Intent(CourseRegistrationForm.this, UserConfirmation.class);
        intent.putExtra("courseRegistration",courseRegisterModel );
        startActivity(intent);
    }
}