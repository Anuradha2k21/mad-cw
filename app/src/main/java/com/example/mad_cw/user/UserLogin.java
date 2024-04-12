package com.example.mad_cw.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mad_cw.DatabaseHelper;
import com.example.mad_cw.R;
import com.example.mad_cw.admin.AdminLogin;
import com.example.mad_cw.course.CourseRecyclerView;

public class UserLogin extends AppCompatActivity {
    private LinearLayout adminClick;
    private EditText etEmail, etPassword;
    private Button btnLogin, btnRegister, btnGuest;
    private ImageButton btnViewPassword;
    private DatabaseHelper databaseHelper;
    private UserModel userModel;
    private CheckBox rememberMe;
    private SharedPreferences sharedPreferences;
    private TextView tvForgotPassword;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_login);

        btnViewPassword = findViewById(R.id.btn_view_password);
        etPassword = findViewById(R.id.txt_pword);
        etEmail = findViewById(R.id.txt_email);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register_redirect);
        btnGuest = findViewById(R.id.btn_guest);
        adminClick = findViewById(R.id.admin_click);
        rememberMe = findViewById(R.id.checkBox);
        tvForgotPassword = findViewById(R.id.tv_forgot_pword);
        sharedPreferences = getSharedPreferences("UserLogin", MODE_PRIVATE);

        // Check if user details exist in SharedPreferences
        if (sharedPreferences.contains("Email") && sharedPreferences.contains("Password")) {
            automaticLogin();
        }
        btnViewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButtonView();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLogin.this, UserRegister.class);
                startActivity(intent);
            }
        });
        btnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLogin.this, CourseRecyclerView.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
        adminClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLogin.this, AdminLogin.class);
                startActivity(intent);

            }
        });
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        changeTextColorToDefault();
    }

    private void validate() {
        if (etEmail.getText().toString().isEmpty()) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
        }
        else if (etEmail.getText().toString().length() < 8 || etEmail.getText().toString().length() > 50) {
            etEmail.setError("Invalid Email");
            etEmail.requestFocus();
        }
        else if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
        }
        else if (etPassword.getText().toString().length() < 8 || etPassword.getText().toString().length() > 20) {
            etPassword.setError("Password should be 8-20 characters long");
            etPassword.requestFocus();
        }
        else {
            processLogin();
        }
    }

    private void changeTextColorToDefault() {
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                etEmail.setTextColor(getResources().getColor(R.color.black));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                etEmail.setTextColor(getResources().getColor(R.color.black));
            }

            @Override
            public void afterTextChanged(Editable s) {
                etEmail.setTextColor(getResources().getColor(R.color.black));
                etPassword.setTextColor(getResources().getColor(R.color.black));
            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                etPassword.setTextColor(getResources().getColor(R.color.black));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                etPassword.setTextColor(getResources().getColor(R.color.black));
            }

            @Override
            public void afterTextChanged(Editable s) {
                etPassword.setTextColor(getResources().getColor(R.color.black));
                etEmail.setTextColor(getResources().getColor(R.color.black));
            }
        });
    }

    private void onClickButtonView() {
        if (etPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
            btnViewPassword.setImageResource(R.drawable.ic_view_password);
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            btnViewPassword.setImageResource(R.drawable.ic_hide_password);
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    public void processLogin(){
        databaseHelper = new DatabaseHelper(UserLogin.this);
        userModel = databaseHelper.checkUserLogin(etEmail.getText().toString(), etPassword.getText().toString());

        if(userModel != null) {
            //  only runs if remember me is checked
            if (rememberMe.isChecked()) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Email", etEmail.getText().toString());
                editor.putString("Password", etPassword.getText().toString());
                editor.apply();
            }
            //  runs anyway
            Intent intent = new Intent(UserLogin.this, CourseRecyclerView.class);
            intent.putExtra("user", userModel);
            startActivity(intent);
        }
        else {
            etEmail.setTextColor(getResources().getColor(R.color.red_warning));
            etPassword.setTextColor(getResources().getColor(R.color.red_warning));
            Toast.makeText(UserLogin.this, "Email or Password is wrong", Toast.LENGTH_SHORT).show();
        }
    }

    public void automaticLogin(){
        databaseHelper = new DatabaseHelper(UserLogin.this);
        userModel = databaseHelper.checkUserLogin(sharedPreferences.getString("Email", ""), sharedPreferences.getString("Password", ""));

        if(userModel != null) {
            Intent intent = new Intent(UserLogin.this, CourseRecyclerView.class);
            intent.putExtra("user", userModel);
            startActivity(intent);
        }
        else {
            etEmail.setTextColor(getResources().getColor(R.color.red_warning));
            etPassword.setTextColor(getResources().getColor(R.color.red_warning));
            Toast.makeText(UserLogin.this, "Please login again", Toast.LENGTH_SHORT).show();
        }
    }
}