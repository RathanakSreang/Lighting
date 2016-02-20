package com.rathanak.sreang.lighting;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.security.Policy;

public class MainActivity extends AppCompatActivity {
    private boolean isLightOn = false;
    private Camera camera;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.buttonFlashlight);
        Context context = this;
        PackageManager pm = context.getPackageManager();

        //check if device support camera
        if(!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Log.e("err", "Device has no camera");
            return;
        }

        camera = Camera.open();
        final Camera.Parameters p = camera.getParameters();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLightOn){
                    Log.i("info", "Touch is turn off");
                    p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    camera.setParameters(p);
                    camera.stopPreview();
                    isLightOn = false;
                }else {
                    Log.i("info", "Touch is turn on");
                    p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    camera.setParameters(p);
                    camera.startPreview();
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
        }
    }
}
