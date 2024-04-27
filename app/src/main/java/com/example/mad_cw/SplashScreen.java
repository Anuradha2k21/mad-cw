package com.example.mad_cw;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mad_cw.user.UserLogin;

public class SplashScreen extends AppCompatActivity {
    private static final int SPLASH_SCREEN = 5000;

    // variables
    Animation topAnim;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        // animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);

        // hooks
        image = findViewById(R.id.imageView);

        image.setAnimation(topAnim);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreen.this, UserLogin.class);
            startActivity(intent);
            finish();
        },SPLASH_SCREEN);
    }
}