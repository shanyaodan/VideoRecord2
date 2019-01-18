package com.infomedia.yunbain.camera;


import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Size;
import android.view.SurfaceHolder;


import com.infomedia.yunbain.utils.L;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by pc on 2018/1/10.
 */

public class CameraManager {
    private static final String TAG = "CameraManager";

    private Camera mCamera;
    private CameraHandler cameraHandler;
    private Camera.Parameters parameters;
    private int renderWidth,renderHeight;
    private int previewWidth,previewHeight;
    private int roateDegrees = 0;
    private ConditionVariable locker = new ConditionVariable();
    public CameraManager() {
        HandlerThread handlerThread = new HandlerThread("CameraManagerHandler");
        handlerThread.start();
        cameraHandler = new CameraHandler(handlerThread.getLooper());
    }


    public Camera camera(){

        return  mCamera;
    }

    public void open(int cameraFace) {
        locker.close();
        cameraHandler.obtainMessage(24,cameraFace).sendToTarget();
    }



    public void release() {
        locker.close();
        cameraHandler.removeCallbacksAndMessages(null);
        cameraHandler.sendEmptyMessage(1);
    }

    public void reconnect() {
        cameraHandler.sendEmptyMessage(2);
    }

    public void unlock() {
        locker.close();
        cameraHandler.removeCallbacksAndMessages(null);
        cameraHandler.sendEmptyMessage(3);
        locker.open();
    }

    public void lock() {
        locker.close();
        cameraHandler.removeCallbacksAndMessages(null);
        cameraHandler.sendEmptyMessage(4);
        locker.open();
    }

    public void a(SurfaceTexture var1) {
        cameraHandler.obtainMessage(5, var1).sendToTarget();
    }

    public void setPreviewTexture(SurfaceTexture var1) {

        cameraHandler.removeCallbacksAndMessages(null);
        cameraHandler.obtainMessage(23, var1).sendToTarget();
    }

    public void setPreviewDisplay(SurfaceHolder var1) {
        locker.close();
        cameraHandler.removeCallbacksAndMessages(null);
        cameraHandler.obtainMessage(19, var1).sendToTarget();
        locker.open();
    }

    public void setSurfaceHolder(SurfaceHolder var1) {
        locker.close();
        cameraHandler.removeCallbacksAndMessages(null);
        cameraHandler.obtainMessage(21, var1).sendToTarget();
        locker.open();
    }

    public void startPreview() {
        cameraHandler.sendEmptyMessage(6);
        locker.open();
    }

    public void f() {
        locker.close();
        cameraHandler.removeCallbacksAndMessages(null);
        cameraHandler.sendEmptyMessage(22);
        locker.open();
    }

    public void stopPreview() {
        locker.close();
        cameraHandler.removeCallbacksAndMessages(null);
        cameraHandler.sendEmptyMessage(7);
        locker.open();
    }

    public void setPreviewCallbackWithBuffer(Camera.PreviewCallback var1) {
        locker.close();
        cameraHandler.removeCallbacksAndMessages(null);
        cameraHandler.obtainMessage(8, var1).sendToTarget();
        locker.open();
    }

    public void addCallbackBuffer(byte[] var1) {
        locker.close();
        cameraHandler.removeCallbacksAndMessages(null);
        cameraHandler.obtainMessage(9, var1).sendToTarget();
        locker.open();
    }

    public void autoFocus(Camera.AutoFocusCallback var1) {
        cameraHandler.obtainMessage(10, var1).sendToTarget();
    }

    public void cancelAutoFocus() {
        locker.close();
        cameraHandler.removeCallbacksAndMessages(null);
        cameraHandler.sendEmptyMessage(11);
        locker.open();
    }

//    public void a(final Camera.ShutterCallback var1, final Camera.PictureCallback var2, final Camera.PictureCallback var3, final Camera.PictureCallback var4) {
//
//        cameraHandler.post(new Runnable() {
//            public void run() {
//                b.this.F.takePicture(var1, var2, var3, var4);
//                ;
//            }
//        });
//
//    }

    public void setDisplayOrientation(int var1) {
        locker.close();
        cameraHandler.removeCallbacksAndMessages(null);
        roateDegrees = var1%360;
        cameraHandler.obtainMessage(12, roateDegrees, 0).sendToTarget();
        locker.open();
    }

    public void setZoomChangeListener(Camera.OnZoomChangeListener var1) {
        locker.close();
        cameraHandler.removeCallbacksAndMessages(null);
        cameraHandler.obtainMessage(13, var1).sendToTarget();
        locker.open();
    }

    public void setErrorCallback(Camera.ErrorCallback var1) {
        locker.close();
        cameraHandler.removeCallbacksAndMessages(null);
        cameraHandler.obtainMessage(14, var1).sendToTarget();
        locker.open();
    }

    public void a(Camera.Parameters var1) {
        locker.close();
        cameraHandler.removeCallbacksAndMessages(null);
        cameraHandler.obtainMessage(15, var1).sendToTarget();
        locker.open();
    }

    public Camera.Parameters getParams(){
        if(null!=mCamera){
        return     mCamera.getParameters();
        }

       return null;
    }

    public boolean b(Camera.Parameters var1) {
        try {
            this.a(var1);
            return true;
        } catch (RuntimeException var3) {
            L.e("CameraManager", "Camera set parameters failed");
            return false;
        }
    }

    public void c(Camera.Parameters var1) {
        cameraHandler.removeMessages(17);
        cameraHandler.obtainMessage(17, var1).sendToTarget();
    }

    public Camera.Parameters getParameters() {

        cameraHandler.sendEmptyMessage(16);

        Camera.Parameters var1 = parameters;
        cameraHandler = null;
        return var1;
    }

    public void setRenderSize(int width, int height) {
        renderWidth = width;
        renderHeight = height;
    }

    /**
     * 设置预览尺寸
     * @param width
     * @param height
     * 设置这个参数的时候证明camera已经初始化了
     */
    public void setpreviewSize(int width, int height) {

        // rotate camera preview according to the device orientation
//		setRotation(params);
        // get the actual preview size
        if(null!=mCamera) {
            locker.close();
            cameraHandler.obtainMessage(16,0);
            locker.block();
            Camera.Parameters parameters = CameraManager.this.parameters;
            final Camera.Size previewSize = parameters.getPreviewSize();
            Log.i(TAG, String.format("previewSize(%d, %d)", previewSize.width, previewSize.height));
            // adjust view size with keeping the aspect ration of camera preview.
            // here is not a UI thread and we should request parent view to execute.
            Camera.Size size = getPropPreviewSize(parameters, 1280 * 1f / 720, width);
            parameters.setPreviewSize(size.width, size.height);
            parameters.setPictureSize(size.width, size.height);
            previewWidth = size.width;
            previewHeight = size.height;
            a(parameters);
        }
//        mCamera.getParameters().getPreferredPreviewSizeForVideo();
//        mCamera.setParameters(parameters);

    }



    private Camera.Size getPropPreviewSize(Camera.Parameters parameters, float th, int minWidth){

        List<Camera.Size> list = parameters.getSupportedPreviewSizes();

        Collections.sort(list, sizeComparator);

        int i = 0;
        for(Camera.Size s:list){
            if((s.height >= minWidth) && equalRate(s, th)){
                break;
            }
            i++;
        }
        if(i>= list.size()){
         return  parameters.getPreferredPreviewSizeForVideo();
        }
        return list.get(i);
    }
    private static boolean equalRate(Camera.Size s, float rate){
        float r = (float)(s.width)/(float)(s.height);
        if(Math.abs(r - rate) <= 0.03) {
            return true;
        }else{
            return false;
        }
    }
    private Comparator<Camera.Size> sizeComparator=new Comparator<Camera.Size>(){
        public int compare(Camera.Size lhs, Camera.Size rhs) {
            if(lhs.height == rhs.height){
                return 0;
            }else if(lhs.height > rhs.height){
                return 1;
            }else{
                return -1;
            }
        }
    };

    public int getRoateDegrees() {
      return roateDegrees;
    }


    private class CameraHandler extends Handler {

        public CameraHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message var1) {
            L.e(TAG, "handleMessage");
            if (var1 != null) {
                try {
                    switch (var1.what) {
                        case 1:
                            if(null!=mCamera) {
                                mCamera.release();
                                mCamera = null;
                                mCamera = null;
                            }
                            break;
                        case 2:
                            try {
                                mCamera.reconnect();
                            } catch (Throwable var9) {
//                                b.this.f = var9;
                            }
                            break;
                        case 3:
                            mCamera.unlock();
                            break;
                        case 4:
                            mCamera.lock();
                            break;
                        case 5:
//                            this.a(var1.obj);
                            return;
                        case 6:
                            if(null!=mCamera) {
                                mCamera.startPreview();
                            }
                            L.e(TAG,"startPreview");
                            return;
                        case 7:
                            if(null!=mCamera) {
                                mCamera.stopPreview();
                            }
                            break;
                        case 8:
                            if(null!=mCamera) {
                                mCamera.setPreviewCallbackWithBuffer((Camera.PreviewCallback) var1.obj);
                            }
                            break;
                        case 9:
                            if(null!=mCamera) {
                                mCamera.addCallbackBuffer((byte[]) ((byte[]) var1.obj));
                            }
                            break;
                        case 10:
                            if(null!=mCamera) {
                                mCamera.autoFocus((Camera.AutoFocusCallback) var1.obj);
                            }
                            break;
                        case 11:
                            mCamera.cancelAutoFocus();
                            break;
                        case 12:
                            if(null!=mCamera) {
                                mCamera.setDisplayOrientation(var1.arg1);
                            }
                            break;
                        case 13:
                            if(null!=mCamera) {
                                mCamera.setZoomChangeListener((Camera.OnZoomChangeListener) var1.obj);
                            }
                            break;
                        case 14:
                            if(null!=mCamera) {
                                mCamera.setErrorCallback((Camera.ErrorCallback) var1.obj);
                            }
                            break;
                        case 15:
//                            b.this.e = null;
                            try {
                                mCamera.setParameters((Camera.Parameters) var1.obj);
                            } catch (Throwable var7) {
                                var7.printStackTrace();
                            }
                            break;
                        case 16:
                            if(null!=mCamera) {
                                parameters = mCamera.getParameters();
                            }

                            break;
                        case 17:
                            try {
                                mCamera.setParameters((Camera.Parameters) var1.obj);
                            } catch (Throwable var5) {
                                L.e(TAG, "Camera set parameters failed");
                            }

                            return;
                        case 18:
                            break;
                        case 19:
                            try {
                                L.e(TAG,"startPreview");
                                mCamera.setPreviewDisplay((SurfaceHolder) var1.obj);
                                return;
                            } catch (Throwable var6) {
                                throw new RuntimeException(var6);
                            }
                        case 20:
                            mCamera.setPreviewCallback((Camera.PreviewCallback) var1.obj);
                            break;
                        case 21:
                            try {
                                mCamera.setPreviewDisplay((SurfaceHolder) var1.obj);
                                break;
                            } catch (Throwable var8) {
                                throw new RuntimeException(var8);
                            }
                        case 22:
                            mCamera.startPreview();
                            break;
                        case 23:
                            try {
                                L.e(TAG,"setPreviewTexture");
                                mCamera.setPreviewTexture((SurfaceTexture) var1.obj);
                            } catch (Throwable var3) {
                                throw new RuntimeException(var3);
                            }
                            break;
                        case 24:
                            openCamera((int)var1.obj);

                            break;
                        default:
                            throw new RuntimeException("Invalid CameraProxy message=" + var1.what);
                    }
                } catch (RuntimeException var10) {
                    if (var1.what != 1 && mCamera != null) {
                        try {
                            mCamera.release();
                        } catch (Exception var4) {
                            Log.e("CameraManager", "Fail to release the camera.");
                        }

                        mCamera = null;

                    }
                }
            }else {
                L.e(TAG, "handleMessage  null");
            }
            locker.open();
        }
    }

    ;


    private void openCamera(int cameraFace){

        mCamera = Camera.open(cameraFace);
        final Camera.Parameters params = mCamera.getParameters();
        final List<String> focusModes = params.getSupportedFocusModes();
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        } else if(focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }
        // let's try fastest frame rate. You will get near 60fps, but your device become hot.
        final List<int[]> supportedFpsRange = params.getSupportedPreviewFpsRange();
//					final int n = supportedFpsRange != null ? supportedFpsRange.size() : 0;
//					int[] range;
//					for (int i = 0; i < n; i++) {
//						range = supportedFpsRange.get(i);
//						Log.i(TAG, String.format("supportedFpsRange(%d)=(%d,%d)", i, range[0], range[1]));
//					}
        final int[] max_fps = supportedFpsRange.get(supportedFpsRange.size() - 1);
        Log.i(TAG, String.format("fps:%d-%d", max_fps[0], max_fps[1]));
        params.setPreviewFpsRange(max_fps[0], max_fps[1]);
        mCamera.setDisplayOrientation(roateDegrees);
//        params.setRotation(90);
        params.setRecordingHint(true);
        mCamera.setParameters(params);
        mCamera.autoFocus(null);
        // request closest supported preview size
        setpreviewSize(previewWidth,previewHeight);
    }

}
