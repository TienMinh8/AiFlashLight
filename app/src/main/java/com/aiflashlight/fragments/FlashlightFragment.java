package com.aiflashlight.fragments;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aiflashlight.R;
import com.aiflashlight.utils.FlashlightUtils;

/**
 * Fragment responsible for the basic flashlight functionality
 */
public class FlashlightFragment extends Fragment {

    private ImageButton btnToggleFlashlight;
    private SeekBar seekBarBrightness;
    private TextView tvBrightnessValue;
    private boolean isFlashlightOn = false;
    private FlashlightUtils flashlightUtils;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flashlight, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Initialize flashlight utility
        flashlightUtils = new FlashlightUtils(requireContext());
        
        // Initialize views
        initViews(view);
        
        // Set up listeners
        setupListeners();
        
        // Check if flashlight is available
        if (!flashlightUtils.isFlashlightAvailable()) {
            showFlashlightNotAvailableMessage();
            disableFlashlightControls();
        }
    }

    /**
     * Initialize the UI components
     */
    private void initViews(View view) {
        btnToggleFlashlight = view.findViewById(R.id.btn_toggle_flashlight);
        seekBarBrightness = view.findViewById(R.id.seekbar_brightness);
        tvBrightnessValue = view.findViewById(R.id.tv_brightness_value);
        
        // Set initial brightness value
        seekBarBrightness.setProgress(100);
        tvBrightnessValue.setText(getString(R.string.flashlight_brightness) + ": 100%");
    }

    /**
     * Set up click and change listeners for UI components
     */
    private void setupListeners() {
        // Toggle flashlight button
        btnToggleFlashlight.setOnClickListener(v -> toggleFlashlight());
        
        // Brightness seekbar
        seekBarBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update brightness value text
                tvBrightnessValue.setText(getString(R.string.flashlight_brightness) + ": " + progress + "%");
                
                // If flashlight is on, apply brightness change
                if (isFlashlightOn) {
                    // Note: Not all devices support brightness control, 
                    // this will be handled in FlashlightUtils
                    flashlightUtils.setBrightness(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed
            }
        });
    }

    /**
     * Toggle the flashlight on/off
     */
    private void toggleFlashlight() {
        if (isFlashlightOn) {
            // Turn off flashlight
            flashlightUtils.turnOffFlashlight();
            updateFlashlightUI(false);
        } else {
            // Turn on flashlight
            boolean success = flashlightUtils.turnOnFlashlight(seekBarBrightness.getProgress());
            updateFlashlightUI(success);
        }
    }

    /**
     * Update the UI based on flashlight state
     * 
     * @param isOn true if flashlight is on, false otherwise
     */
    private void updateFlashlightUI(boolean isOn) {
        isFlashlightOn = isOn;
        
        if (isOn) {
            btnToggleFlashlight.setImageResource(R.drawable.ic_flashlight_on);
            btnToggleFlashlight.setContentDescription(getString(R.string.flashlight_off));
        } else {
            btnToggleFlashlight.setImageResource(R.drawable.ic_flashlight_off);
            btnToggleFlashlight.setContentDescription(getString(R.string.flashlight_on));
        }
    }

    /**
     * Display message that flashlight is not available
     */
    private void showFlashlightNotAvailableMessage() {
        Toast.makeText(requireContext(), 
                R.string.flashlight_not_available, 
                Toast.LENGTH_LONG).show();
    }

    /**
     * Disable flashlight controls when not available
     */
    private void disableFlashlightControls() {
        btnToggleFlashlight.setEnabled(false);
        seekBarBrightness.setEnabled(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Turn off flashlight when fragment is paused
        if (isFlashlightOn && flashlightUtils != null) {
            flashlightUtils.turnOffFlashlight();
            updateFlashlightUI(false);
        }
    }
} 