package com.infomedia.yunbain.videorecord;

import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.infomedia.yunbain.camera.CameraCapture;
import com.infomedia.yunbain.egl.EglCore;
import com.infomedia.yunbain.egl.WindowSurface;
import com.infomedia.yunbain.utils.GLDrawer2D;
import com.infomedia.yunbain.utils.Glutil;
import com.infomedia.yunbain.utils.MiscUtils;
import com.infomedia.yunbain.utils.TextureMovieEncoder2;
import com.infomedia.yunbain.utils.VideoEncoderCore;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.FloatBuffer;

/**
 * Created by pc on 2018/4/27.
 */

public class TestEgl2 extends AppCompatActivity implements SurfaceHolder.Callback{

    private static String TAG = "TestEgl";
    public static String vert = "uniform mat4 u_MVPMatrix;" +
            "attribute vec4 a_position;"
            + "attribute vec2 a_texturecoord;"
            + "varying vec2 v_texturecoord;"
            + "void main()"
            + "{"
            + "gl_Position = u_MVPMatrix*a_position;"
            + "v_texturecoord = a_texturecoord;"
            + "}";

    final String sFragmentShader =
            "precision mediump float;"
                    + "varying vec2 v_texturecoord;"
                    + "uniform sampler2D u_samplerTexture;"
                    + "void main()"
                    + "{"
                    + "gl_FragColor = texture2D(u_samplerTexture,v_texturecoord);"
                    + "}";

    float[] objcood = {-1f, -1f,
            1f, -1f,
            -1f, 1f,
            1f, 1f};

    float textcood[] = {
            0f, 1f,
            1f, 1f,
            0f, 0f,
            1f, 0f,
    };

    private static final float[] TEX_VERTICES = {
            0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f
    };

    private static final float[] POS_VERTICES = {
            -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f
    };

    private float mvpmatrix[] = new float[16];
    private float mProjMatrix[] = new float[16];
    private float mVMatrix[] = new float[16];
    FloatBuffer objbyteBuffer, textByteBuffer;
    private SurfaceView surfaceView;
//    private static RenderThread mRenderThread;
    private CameraCapture cameraCapture;

    private Camera camera;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        surfaceView = findViewById(R.id.surfaceview);
        surfaceView.getHolder().addCallback(this);
        camera =Camera.open();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

}




