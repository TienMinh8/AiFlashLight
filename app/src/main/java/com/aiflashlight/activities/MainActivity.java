package com.aiflashlight.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.aiflashlight.R;
import com.aiflashlight.fragments.FlashlightFragment;
import com.aiflashlight.fragments.MusicEffectFragment;
import com.aiflashlight.fragments.ScreenLightFragment;
import com.aiflashlight.fragments.SettingsFragment;
import com.aiflashlight.fragments.SosFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

/**
 * Main Activity containing the navigation and fragments for different features
 */
public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private static final int PERMISSION_REQUEST_CAMERA = 100;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnItemSelectedListener(this);

        // Check camera permission for flashlight
        checkCameraPermission();
        
        // Load default fragment
        if (savedInstanceState == null) {
            loadFragment(new FlashlightFragment());
        }
    }

    /**
     * Handle bottom navigation item selection
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        
        // Determine which fragment to load based on selected menu item
        switch (item.getItemId()) {
            case R.id.nav_flashlight:
                fragment = new FlashlightFragment();
                break;
            case R.id.nav_screen_light:
                fragment = new ScreenLightFragment();
                break;
            case R.id.nav_sos:
                fragment = new SosFragment();
                break;
            case R.id.nav_music:
                fragment = new MusicEffectFragment();
                break;
            case R.id.nav_settings:
                fragment = new SettingsFragment();
                break;
        }
        
        // Load the selected fragment
        return loadFragment(fragment);
    }

    /**
     * Load a fragment into the container
     * @param fragment Fragment to load
     * @return true if successful
     */
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    /**
     * Check camera permission for flashlight functionality
     */
    private void checkCameraPermission() {
        // Check if permission is already granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) 
                != PackageManager.PERMISSION_GRANTED) {
            
            // Request permission if not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CAMERA);
        }
    }

    /**
     * Handle permission request results
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, 
                                          @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, can use flashlight
                Toast.makeText(this, "Camera permission granted for flashlight", Toast.LENGTH_SHORT).show();
            } else {
                // Permission denied
                Toast.makeText(this, R.string.flashlight_permission_required, Toast.LENGTH_LONG).show();
            }
        }
    }
} 