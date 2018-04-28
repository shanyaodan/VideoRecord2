package com.infomedia.yunbain.camera;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.infomedia.yunbain.utils.GLDrawer2D;
import com.infomedia.yunbain.utils.GLRender;
import com.infomedia.yunbain.utils.Glutil;

import java.util.jar.Attributes;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by pc on 2018/4/10.
 */

public class MySurfaceView extends GLSurfaceView implements GLSurfaceView.Renderer,SurfaceTexture.OnFrameAvailableListener {

    private CameraManager cameraManager;
    public SurfaceTexture mSurfaceTexture;
    private GLDrawer2D glDrawer2D;

    public MySurfaceView(Context context, AttributeSet attributes) {
        super(context,attributes);

        setEGLContextClientVersion(2);
        setRenderer(this);
        setRenderMode(RENDERMODE_WHEN_DIRTY);
//        cameraManager = new CameraManager();
    }

    int mOESTextureId;
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

         mOESTextureId = Glutil.createOESTextureObject();
        mSurfaceTexture = new SurfaceTexture(mOESTextureId);
        mSurfaceTexture.setOnFrameAvailableListener(this);
        glDrawer2D = new GLDrawer2D();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
             GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        mSurfaceTexture.updateTexImage();
                float[] mtx = new float[16];
        mSurfaceTexture.getTransformMatrix(mtx);
        glDrawer2D.draw(mOESTextureId,mtx);
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        requestRender();
    }
}
