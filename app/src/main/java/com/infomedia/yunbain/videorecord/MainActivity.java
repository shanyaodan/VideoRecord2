package com.infomedia.yunbain.videorecord;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;


import com.infomedia.yunbain.utils.Constants;
import com.infomedia.yunbain.utils.L;
import com.infomedia.yunbain.utils.RecordKit;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CAMERA_AUDIOREC = 123;
    RecordKit mRecordKit;
    private GLSurfaceView glView;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!checkCameraHardware()){
            Toast.makeText(this,"不支持相机功能",Toast.LENGTH_LONG).show();
            return;
        }

        mRecordKit = new RecordKit(this);
        glView =findViewById(R.id.glView);
        mRecordKit.setDisplayPreview(glView);
        mRecordKit.setPreviewResolution(Constants.DEFAULT_PREVIEW_RESOLUTION);
        mRecordKit.setEncodeResolution(Constants.DEFAULT_PREVIEW_RESOLUTION);
        mRecordKit.setRotateDegrees(0);
        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mRecordKit.setRotateDegrees(mRecordKit.getRoateDegrees()+90);
                mRecordKit.switchCamera();
            }
        });


//       final MySurfaceView surfaceView = findViewById(R.id.myglview);
//        startPreView();/**/
//        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                CameraManager cameraManager = new CameraManager();
////                cameraManager.open(0);
////                cameraManager.setPreviewTexture(surfaceView.mSurfaceTexture);
////                cameraManager.startPreview();
//            }
//        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        startPreView();
    }
    /** Check if this device has a camera */
    private boolean checkCameraHardware() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    private void startPreView(){
        int cameraPerm = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int audioPerm = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        if (cameraPerm != PackageManager.PERMISSION_GRANTED ||
                audioPerm != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                L.e(TAG, "No CAMERA or AudioRecord permission, please check");
                Toast.makeText(this, "No CAMERA or AudioRecord permission, please check",
                        Toast.LENGTH_LONG).show();
            } else {
                String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CAMERA_AUDIOREC);
            }
        }else {
            mRecordKit.startPreview();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CAMERA_AUDIOREC: {
                // If request is cancelled, the result arrays are empty
                // .
                int cameraPerm = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
                int audioPerm = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
                if (cameraPerm == PackageManager.PERMISSION_GRANTED &&
                        audioPerm == PackageManager.PERMISSION_GRANTED) {
                    mRecordKit.startPreview();
                } else {
                    L.e(TAG, "No CAMERA or AudioRecord permission");
                    Toast.makeText(this, "No CAMERA or AudioRecord permission", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

}
