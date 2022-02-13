package com.childsenterprises.carcam;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static int CAMERA_PERMISSION_CODE = 100;
    private static int VIDEO_RECORD_CODE = 101;
    private static int IMAGE_CAPTURE_CODE = 102;

    private Uri videoPath;

    // what do I want to have loaded when the app is launched?
    // should splash screen display every time?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isCameraPresent()) {
            Log.i("VIDEO_RECORD_TAG", "Camera is detected");
            getCameraPermission();
        }
        else {
            Log.i("VIDEO_RECORD_TAG", "Camera is not detected");
        }
    }

    private boolean isCameraPresent() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            return true;
        }
        return false;
    }

    private void getCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }
    }

    // migrate to splashScreen
    // determine if there is another way besides Intent
    public void recordButtonPressed(View view) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, VIDEO_RECORD_CODE);
    }

    // migrate to splashScreen
    // determine if there is another way besides Intent
    public void captureImageButtonPressed(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, IMAGE_CAPTURE_CODE);
    }

    // may need to change logic if intent changes
    // would also like to make a function so isn't a block of if/else
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == VIDEO_RECORD_CODE) {
            if (resultCode == RESULT_OK) {
                videoPath = data.getData();
                Log.i("VIDEO_RECORD_CODE", "Video is recorded and available at path " + videoPath);
            }
            else if (resultCode == RESULT_CANCELED) {
                Log.i("VIDEO_RECORD_CODE", "Video recording was cancelled");
            }
            else {
                Log.i("VIDEO_RECORD_CODE", "Video recording was not saved due to some error");
            }
        }
        else if (requestCode == IMAGE_CAPTURE_CODE) {
            if (resultCode == RESULT_OK) {
                videoPath = data.getData();
                Log.i("IMAGE_CAPTURE_CODE", "Image was captured and available at path " + videoPath);
            }
            else if (resultCode == RESULT_CANCELED) {
                Log.i("IMAGE_CAPTURE_CODE", "Image capture was cancelled");
            }
            else {
                Log.i("IMAGE_CAPTURE_CODE", "Image capture was not saved due to some error");
            }
        }
    }
}