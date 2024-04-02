package com.example.mad_cw.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mad_cw.R;
import com.example.mad_cw.course.CourseRecyclerView;

public class UserLogin extends AppCompatActivity {
    private LinearLayout adminClick;
    private EditText etEmail, etPassword;
    private Button btnLogin, btnRegister, btnGuest;
    private ImageButton btnViewPassword;
    

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

        btnViewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    btnViewPassword.setImageResource(R.drawable.ic_view_password);
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    btnViewPassword.setImageResource(R.drawable.ic_hide_password);
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
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
//                Intent intent = new Intent(UserLogin.this, CourseRecyclerView.class);
//                startActivity(intent);
            }
        });
        adminClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(UserLogin.this, AdminLogin.class);
//                startActivity(intent);
            }
        });

    }
}