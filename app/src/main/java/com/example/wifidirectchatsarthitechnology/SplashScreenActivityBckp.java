package com.example.wifidirectchatsarthitechnology;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
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
public class SplashScreenActivityBckp extends AppCompatActivity {

    private Button buttonGoToMainScreen;

    private int permissionsIndex = 0;
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
        /*
        if (requestCode == PERMISSION_GPS_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, location_permission_granted_msg, Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(this, location_permission_not_granted_msg, Toast.LENGTH_SHORT).show();

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
                startActivity(new Intent(SplashScreenActivityBckp.this, MainActivity.class));
            }
        });


        //Alert Dialog does not pause execution
        new AlertDialog.Builder(this)
                .setTitle("Some permissions required")
                .setMessage("This application uses WiFi Direct technology to function. You will now be prompted to enable the necessary permissions.")
                .setPositiveButton(android.R.string.ok, null)
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .show();


        //Neither does requestPermissions
        requestPermissions(PERMISSIONS, 0);


        //Need to put this in a method (checkPermissions or something like that) that is then called on onRequestPermissionsResult
        //or first check permissions to see whether to direct to MainActivity or to prompt for permissions. Maybe this is best
        boolean allPermissionsGranted = true;
        for (String permission : PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                allPermissionsGranted = false;
                Log.d("Permissions", "Not granted: " + permission);
            }
        }

        if (allPermissionsGranted) {
            buttonGoToMainScreen.setEnabled(true);
            startActivity(new Intent(SplashScreenActivityBckp.this, MainActivity.class));
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Some permissions missing")
                    .setMessage("Some of the required permissions are not being granted. If you were not prompted to allow permissions, check the app's Android settings to manually enable them.")
                    .setPositiveButton(android.R.string.ok, null)
                    .setIconAttribute(android.R.attr.alertDialogIcon)
                    .show();
            finish();
            System.exit(0);
        }

        /*
        String permission = android.Manifest.permission.ACCESS_FINE_LOCATION;
        if (ActivityCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.
                checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]
                    {permission}, PERMISSION_GPS_CODE);

        }
         */

        /*
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.NEARBY_WIFI_DEVICES) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            requestPermissions(permissions,0);
            return;
        }

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.REQUESTED_PERMISSION) ==
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            performAction(...);
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.REQUESTED_PERMISSION)) {
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected, and what
            // features are disabled if it's declined. In this UI, include a
            // "cancel" or "no thanks" button that lets the user continue
            // using your app without granting the permission.
            showInContextUI(...);
        } else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            requestPermissionLauncher.launch(
                    Manifest.permission.REQUESTED_PERMISSION);
        }
        */
    }
}