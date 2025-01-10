package com.example.wifidirectchatsarthitechnology;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Splash screen to check and ensure the required permissions are granted.
 */
public class SplashScreenActivity extends AppCompatActivity {

    private Button buttonGoToMainScreen;

    //private int permissionsIndex = 0;
    @SuppressLint("InlinedApi")
    private final String[] PERMISSIONS = new String[]{
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.CHANGE_NETWORK_STATE,
            android.Manifest.permission.ACCESS_WIFI_STATE,
            android.Manifest.permission.CHANGE_WIFI_STATE,
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.NEARBY_WIFI_DEVICES
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Second check
        if (checkGrantedPermissions()) {
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            finish();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Some permissions missing")
                    .setMessage("Some of the required permissions are not being granted. If you were not prompted to allow permissions, check the app's Android settings to manually enable them.")
                    .setNeutralButton("CLOSE APP", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setIconAttribute(android.R.attr.alertDialogIcon)
                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            finish();
                        }
                    })
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            finish();
                        }
                    })
                    .show();
        }
        /*
        if (requestCode == 0) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                new AlertDialog.Builder(this)
                        .setTitle("Permissions required")
                        .setMessage("Without these permissions, the application won't be able to find and connect to other devices. They are necessary to actually play with other users around you.")
                        .setPositiveButton(android.R.string.ok, null)
                        .setIconAttribute(android.R.attr.alertDialogIcon)
                        .show();
            }
        }
         */
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        super.setContentView(R.layout.activity_splash_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonGoToMainScreen = findViewById(R.id.buttonGoToMainScreen);
        buttonGoToMainScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            }
        });

        boolean allPermissionsGranted = checkGrantedPermissions();

        if (allPermissionsGranted) {
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            finish();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Some permissions required")
                    .setMessage("This application uses WiFi Direct technology to function. You will now be prompted to enable the necessary permissions.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            callRequestPermissions();
                        }
                    })
                    .setIconAttribute(android.R.attr.alertDialogIcon)
                    .setCancelable(false) //Cannot press outside to close/cancel
                    .setOnDismissListener(new DialogInterface.OnDismissListener() { // Just in case
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            callRequestPermissions();
                        }
                    })
                    .setOnCancelListener(new DialogInterface.OnCancelListener() { //Just in case x2
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            callRequestPermissions();
                        }
                    })
                    .show();
        }

    }

    private void callRequestPermissions() {
        requestPermissions(PERMISSIONS, 0);
    }

    private boolean checkGrantedPermissions() {
        Log.d("Permissions","checkGrantedPermissions() called");
        for (String permission : PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                if (!(permission.equals(Manifest.permission.NEARBY_WIFI_DEVICES) && Build.VERSION.SDK_INT < 33)) {
                    Log.d("Permissions", "Not granted: " + permission);
                    return false;
                }
            }
        }
        buttonGoToMainScreen.setEnabled(true);
        return true;
    }
}