package com.rathanak.sreang.lighting;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private boolean isLightOn = true;
    private Camera camera;
    private Button button;
    private Camera.Parameters param;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.buttonFlashlight);
        Context context = this;
        PackageManager pm = context.getPackageManager();

        //check if device support camera
        if(!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Log.e("err", "Device has no camera");
            return;
        }

        getCamera();
        Log.i("info", "Touch is turn_on");
        param.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(param);
        camera.startPreview();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLightOn){
                    Log.i("info", "Touch is turn_off");
                    param.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    camera.setParameters(param);
                    camera.stopPreview();
                    button.setBackgroundResource(R.drawable.turn_on);
                    isLightOn = false;
                }else {
                    if (camera == null || param == null) {
                        Log.i("error", "Touch ");
                        return;
                    }
                    Log.i("info", "Touch is turn_on");
                    param.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    camera.setParameters(param);
                    camera.startPreview();
                    button.setBackgroundResource(R.drawable.turn_off);
                    isLightOn = true;
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(camera != null){
            camera.release();
            param =null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Rathanak Camera", "Pause Camera");
        if(camera != null){
            camera.release();
        }
        System.exit(1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Rathanak Camera", "Resume Camera");
        getCamera();
    }

    // Get the camera
    private void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                param = camera.getParameters();
                Log.i("Rathanak Camera", "Init Camera");
            } catch (RuntimeException e) {
                Log.e("Camera Error. Failed to Open. Error: ", e.getMessage());
            }
        }
    }
}
