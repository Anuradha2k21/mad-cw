package com.example.mad_cw.admin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mad_cw.DatabaseHelper;
import com.example.mad_cw.R;
import com.example.mad_cw.course.CourseRecyclerView;

public class AdminLogin extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Button btnLogin;
    private DatabaseHelper databaseHelper;
    private AdminModel adminModel;
    private ImageButton btnViewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etEmail = findViewById(R.id.txt_email);
        etPassword = findViewById(R.id.txt_pword);
        btnLogin = findViewById(R.id.btn_login);
        btnViewPassword = findViewById(R.id.btn_view_password);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
        btnViewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButtonView();
            }
        });
        changeTextColorToDefault();
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
    private void processLogin() {
        databaseHelper = new DatabaseHelper(AdminLogin.this);
        databaseHelper.addDummyAdmin();
        adminModel = databaseHelper.checkAdminLogin(etEmail.getText().toString().trim(), etPassword.getText().toString());

        if(adminModel != null) {
            //  redirect to admin panel
//            Intent intent = new Intent(AdminLogin.this, AdminPanel.class);
//            intent.putExtra("admin", adminModel);
//            startActivity(intent);
            Toast.makeText(this, "where admin panel - HANSANI?", Toast.LENGTH_LONG).show();
        }
        else {
            etEmail.setTextColor(getResources().getColor(R.color.red_warning));
            etPassword.setTextColor(getResources().getColor(R.color.red_warning));
            Toast.makeText(AdminLogin.this, "Email or Password is wrong", Toast.LENGTH_SHORT).show();
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
}