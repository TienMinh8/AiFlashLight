package com.aiflashlight.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.util.Log;

/**
 * Utility class for controlling the device flashlight
 */
public class FlashlightUtils {
    private static final String TAG = "FlashlightUtils";
    
    private final Context context;
    private final CameraManager cameraManager;
    private String cameraId;
    private boolean isTorchSupported = false;
    
    /**
     * Constructor initializes camera manager and checks if torch is supported
     * 
     * @param context Application context
     */
    public FlashlightUtils(Context context) {
        this.context = context;
        this.cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        initCameraId();
    }
    
    /**
     * Initialize camera ID and check if torch is supported
     */
    private void initCameraId() {
        try {
            if (cameraManager != null) {
                // Find a camera with flash capability
                for (String id : cameraManager.getCameraIdList()) {
                    CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(id);
                    Boolean hasFlash = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                    
                    if (hasFlash != null && hasFlash) {
                        cameraId = id;
                        isTorchSupported = true;
                        break;
                    }
                }
            }
        } catch (CameraAccessException e) {
            Log.e(TAG, "Failed to access camera: " + e.getMessage());
        }
    }
    
    /**
     * Check if flashlight is available on this device
     * 
     * @return true if flashlight is available
     */
    public boolean isFlashlightAvailable() {
        // Check if device has camera flash
        boolean hasFlash = context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        return hasFlash && isTorchSupported && cameraId != null;
    }
    
    /**
     * Turn on the flashlight
     * 
     * @param brightness Brightness level (0-100)
     * @return true if operation was successful
     */
    public boolean turnOnFlashlight(int brightness) {
        if (!isFlashlightAvailable()) {
            return false;
        }
        
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // For Android 13+ with brightness control
                float normalizedBrightness = brightness / 100f;
                cameraManager.turnOnTorchWithStrengthLevel(cameraId, (int)(normalizedBrightness * 255));
            } else {
                // For older Android versions without brightness control
                cameraManager.setTorchMode(cameraId, true);
            }
            return true;
        } catch (CameraAccessException e) {
            Log.e(TAG, "Failed to turn on flashlight: " + e.getMessage());
            return false;
        } catch (UnsupportedOperationException e) {
            // Fallback if torch strength control is not supported
            try {
                cameraManager.setTorchMode(cameraId, true);
                return true;
            } catch (CameraAccessException ex) {
                Log.e(TAG, "Failed to turn on flashlight (fallback): " + ex.getMessage());
                return false;
            }
        }
    }
    
    /**
     * Turn off the flashlight
     * 
     * @return true if operation was successful
     */
    public boolean turnOffFlashlight() {
        if (!isFlashlightAvailable()) {
            return false;
        }
        
        try {
            cameraManager.setTorchMode(cameraId, false);
            return true;
        } catch (CameraAccessException e) {
            Log.e(TAG, "Failed to turn off flashlight: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Set the brightness level of the flashlight
     * 
     * @param brightness Brightness level (0-100)
     * @return true if operation was successful
     */
    public boolean setBrightness(int brightness) {
        // Only supported on Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            try {
                float normalizedBrightness = brightness / 100f;
                cameraManager.turnOnTorchWithStrengthLevel(cameraId, (int)(normalizedBrightness * 255));
                return true;
            } catch (CameraAccessException | UnsupportedOperationException e) {
                Log.e(TAG, "Failed to set torch brightness: " + e.getMessage());
                // Try to keep the torch on at default brightness if setting brightness fails
                try {
                    cameraManager.setTorchMode(cameraId, true);
                    return true;
                } catch (CameraAccessException ex) {
                    Log.e(TAG, "Failed to maintain torch on: " + ex.getMessage());
                    return false;
                }
            }
        }
        // For devices that don't support brightness control, we just leave the torch on
        return true;
    }
} 