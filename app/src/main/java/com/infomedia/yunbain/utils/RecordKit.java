package com.infomedia.yunbain.utils;

import android.app.Activity;
import android.opengl.GLSurfaceView;

import com.infomedia.yunbain.camera.CameraCapture;


/**
 * Created by pc on 2017/12/25.
 */

public class RecordKit {

    private Activity activity;
    private GLSurfaceView glSurfaceView;
    CameraCapture mCameraCapture;
    private int cameraFace;
    public RecordKit(Activity activity) {
        this.activity = activity;
        mCameraCapture = new CameraCapture();
    }

    public void setPreviewFps() {

    }

    public void setEncodeFps() {


    }

    public void setVideoKBitrate() {

    }


    public void setPreviewResolution(int previewResolution) {
       int resolution = previewResolution;

        mCameraCapture.setPreviewResolution(previewResolution);

    }


    public void setEncodeResolution(int encodeResolution) {
        mCameraCapture.setEncodeResolution(encodeResolution);
    }

    public void setVideoEncodeType() {

    }

    public void setRotateDegrees() {

    }

    public void setCameraFace(int face) {
        cameraFace = face;
    }

    public void stopCameraPreview() {

    }

    public void setDisplayPreview(GLSurfaceView glSurfaceView) {
        this.glSurfaceView = glSurfaceView;
        mCameraCapture.setDisplayPreview(glSurfaceView);
    }

    public void switchCamera() {

    }

    public void toggleTorch() {


    }

    public void stopRecord() {

    }

    public void startRecord(String url) {


    }

    public void setAudioKBitrate() {

    }


    public void onResume() {

    }

    public void onPause() {

    }

    public void startPreview(){
        mCameraCapture.start(cameraFace);
    }


}
