package com.aiflashlight.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.aiflashlight.R;

/**
 * Splash screen activity shown when app launches
 */
public class SplashActivity extends AppCompatActivity {

    // Splash screen duration in milliseconds
    private static final long SPLASH_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Use a handler to delay the transition to MainActivity
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Start MainActivity after delay
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            
            // Close this activity to prevent going back to splash screen
            finish();
        }, SPLASH_DELAY);
    }
} 