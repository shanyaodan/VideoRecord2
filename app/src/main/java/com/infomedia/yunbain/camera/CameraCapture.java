package com.infomedia.yunbain.camera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.view.SurfaceHolder;


import com.infomedia.yunbain.utils.GLRender;
import com.infomedia.yunbain.utils.L;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by pc on 2018/1/10.
 */

public class CameraCapture implements GLRender.RenderListener, SurfaceTexture.OnFrameAvailableListener {
    private static final String TAG = "CameraCapture";

    /**
     * 相机实体
     */
    private CameraManager mCameraManager;
    private GLRender glRender;
    private GLSurfaceView glSurfaceView;
    private boolean surfaceChanged,previewStarted;

    /**
     * 预览分辨率
     */
    private int mPreviewWidth,mPreviewHeight;
    /**
     * 画布分辨率
     */
    private int renderWidth,renderHeight;
    /**
     * 编码分辨率
     */
    private int mTargetWidth,mTargetHeight;
    private int mTargetResolution;
    private int mPreviewResolution;
    private int cameraId =Camera.CameraInfo.CAMERA_FACING_BACK;

    public CameraCapture() {
        mCameraManager = new CameraManager();
        glRender = new GLRender();
        glRender.setRenderListener(this);
    }


    public void start(int cameraFace) {
        mCameraManager.open(cameraFace);
        previewStarted = true;
        if(surfaceChanged) {
           startPreView();
        }
    }


    public void setDisplayPreview(GLSurfaceView displayPreview) {
        glSurfaceView = displayPreview;
        glRender.init(displayPreview);
    }

    public void setPreviewSolution(int width,int height){


        mCameraManager.setpreviewSize(width,height);
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        L.e(TAG, "onSurfaceCreated");
//        mCameraManager.setPreviewTexture(glRender.getSurfaceTexture());
        glRender.getSurfaceTexture().setOnFrameAvailableListener(this);

//        mCameraManager.startPreview();
//        mCameraManager.open(0);
//        mCameraManager.setPreviewTexture(glRender.getSurfaceTexture());
//        mCameraManager.startPreview();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        L.e(TAG, "onSurfaceChanged");

        mCameraManager.setRenderSize(width,height);
        surfaceChanged = true;
        renderWidth =width;
        renderHeight = height;
        if(previewStarted){
        startPreView();
        }
//        Camera camera = Camera.open();
//        Camera.Parameters parameters = camera.getParameters();
//        parameters.setPreviewSize(1280, 720);
//        parameters.setPictureSize(1280, 720);
//        camera.setParameters(parameters);
//        try {
//            camera.setPreviewTexture(glRender.getSurfaceTexture());
//        } catch (Exception e) {
//            e.printStackTrace();
//            L.e(TAG,"onSurfaceCreated fail@@@@@@@@@@@@@@@@@@@@@@@@@");
//        }
        glRender.getSurfaceTexture().setOnFrameAvailableListener(this);
//        camera.startPreview();
    }

    private void startPreView(){
        calResolution();
        mCameraManager.setpreviewSize(mPreviewWidth,mPreviewHeight);
        mCameraManager.setPreviewTexture(glRender.getSurfaceTexture());
        mCameraManager.startPreview();
    }


    @Override
    public void onDrawFrame(GL10 gl) {
        L.e(TAG, "onDrawFrame");

    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        L.e(TAG, "onFrameAvailable");
        refresh();
    }

    public void refresh() {
        L.e(TAG, "refresh");
        glSurfaceView.requestRender();
    }

    public void setPreviewResolution(int previewResolution) {
        mPreviewResolution = previewResolution;

    }

    public void setEncodeResolution(int mTargetResolution){
        this.mTargetResolution = mTargetResolution;
    }


    public void setSurfaceHolder(SurfaceHolder surfaceHolder){
        mCameraManager.setPreviewDisplay(surfaceHolder);
    }


    private void calResolution() {
        int lenth;
        if(this.mPreviewWidth == 0 && this.mPreviewHeight == 0) {
            lenth = this.getShortEdgeLength(this.mPreviewResolution);
            if(this.renderWidth > this.renderHeight) {
                this.mPreviewHeight = lenth;
            } else {
                this.mPreviewWidth = lenth;
            }
        }

        if(this.mTargetWidth == 0 && this.mTargetHeight == 0) {
            lenth = this.getShortEdgeLength(this.mTargetResolution);
            if(this.renderWidth > this.renderHeight) {
                this.mTargetHeight = lenth;
            } else {
                this.mTargetWidth = lenth;
            }
        }

        if(this.renderWidth != 0 && this.renderHeight != 0) {
            if(this.mPreviewWidth == 0) {
                this.mPreviewWidth = this.mPreviewHeight * this.renderWidth / this.renderHeight;
            } else if(this.mPreviewHeight == 0) {
                this.mPreviewHeight = this.mPreviewWidth * this.renderHeight / this.renderWidth;
            }

            if(this.mTargetWidth == 0) {
                this.mTargetWidth = this.mTargetHeight * this.renderWidth / this.renderHeight;
            } else if(this.mTargetHeight == 0) {
                this.mTargetHeight = this.mTargetWidth * this.renderHeight / this.renderWidth;
            }
        }

        this.mPreviewWidth = this.align(this.mPreviewWidth, 8);
        this.mPreviewHeight = this.align(this.mPreviewHeight, 8);
        this.mTargetWidth = this.align(this.mTargetWidth, 8);
        this.mTargetHeight = this.align(this.mTargetHeight, 8);
    }

    private int align(int var1, int var2) {
        return (var1 + var2 - 1) / var2 * var2;
    }

    private int getShortEdgeLength(int resolution) {
        switch(resolution) {
            case 0:
                return 360;
            case 1:
                return 480;
            case 2:
                return 540;
            case 3:
                return 720;
            case 4:
                return 1080;
            default:
                return 720;
        }
    }
    public void setRotateDegrees(int i) {

        mCameraManager.setDisplayOrientation(i);

    }

    public int getRoateDegrees() {
        return mCameraManager.getRoateDegrees();
    }

    public void switchCamera() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        int  cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数
        if (cameraCount <= 1) {
            return;
        }
        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, cameraInfo);//得到每一个摄像头的信息
            if (cameraId == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
                break;
            } else {
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    cameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
                    break;
                }
            }
        }
        mCameraManager.release();
        start(cameraId);

    }
}
