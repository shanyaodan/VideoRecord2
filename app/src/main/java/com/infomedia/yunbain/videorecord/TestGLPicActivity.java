package com.infomedia.yunbain.videorecord;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.infomedia.yunbain.utils.Glutil;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by pc on 2018/4/24.
 */

public class TestGLPicActivity extends AppCompatActivity {

    public static  String vert="uniform mat4 u_MVPMatrix;" +
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

    float[]objcood={-1f,-1f,
                    1f,-1f,
                     -1f,1f,
                       1f,1f};

    float textcood[]={
            0f,1f,
            1f,1f,
            0f,0f,
            1f,0f,
            };

    private static final float[] TEX_VERTICES = {
            0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f
    };

    private static final float[] POS_VERTICES = {
            -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f
    };

   private float mvpmatrix [] = new float[16];
   private float mProjMatrix [] = new float[16];
   private float mVMatrix [] = new float[16];
    FloatBuffer objbyteBuffer,textByteBuffer ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    /*   final EglCore eglCore = new EglCore();
        SurfaceView surfaceView = findViewById(R.id.surface);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                EGLSurface eglSurface =eglCore.createWindowSurface(holder.getSurface());
                renderer.onSurfaceCreated(null,null);
                renderer.onDrawFrame(null);
                eglCore.swapBuffers(eglSurface);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });*/




//        GLSurfaceView glSurfaceView = findViewById(R.id.glView);
//        glSurfaceView.setEGLContextClientVersion(2);
//        glSurfaceView.setRenderer(renderer);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                renderer.onSurfaceCreated(null,null);
//                renderer.onDrawFrame(null);
//            }
//        },2000);

    }

//    GLSurfaceView.Renderer renderer = new GLSurfaceView.Renderer() {
//
//        int textureid;
//        int program;
//        int attposid,a_texturecoordid,u_samplerTextureid,u_MVPMatrixid;
//        SurfaceTexture mTexture;
//        @Override
//        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//            int []ids = new int[1];
//            objbyteBuffer =ByteBuffer.allocateDirect(objcood.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
//
//            objbyteBuffer.put(objcood).position(0);
//
//
//            textByteBuffer =ByteBuffer.allocateDirect(textcood.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
//            textByteBuffer.put(textcood).position(0);
//            GLES20.glGenTextures(1,ids,0);
//            textureid =ids[0];
//            mTexture = new SurfaceTexture(textureid);
////            eglCore.createWindowSurface(mTexture);
//            mTexture.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() {
//                @Override
//                public void onFrameAvailable(SurfaceTexture surfaceTexture) {
//
//                }
//            });
//            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureid);
//            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
//            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_LINEAR);
//            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_CLAMP_TO_EDGE);
//            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_CLAMP_TO_EDGE);
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.test);
//            Glutils.texImage2D(GLES20.GL_TEXTURE_2D,0,bitmap,0);
//            program = Glutil.createProgram(vert,sFragmentShader);
//            GLES20.glUseProgram(program);
//
//            attposid =GLES20.glGetAttribLocation(program,"a_position");
//            a_texturecoordid = GLES20.glGetAttribLocation(program,"a_texturecoord");
//            u_samplerTextureid=GLES20.glGetUniformLocation(program,"u_samplerTexture");
//            u_MVPMatrixid=GLES20.glGetUniformLocation(program,"u_MVPMatrix");
//            Matrix.setIdentityM(mvpmatrix,0);
//            GLES20.glVertexAttribPointer(attposid,2,GLES20.GL_FLOAT,false,0,objbyteBuffer);
//            GLES20.glEnableVertexAttribArray(attposid);
//            GLES20.glVertexAttribPointer(a_texturecoordid,2,GLES20.GL_FLOAT,false,0,textByteBuffer);
//            GLES20.glEnableVertexAttribArray(a_texturecoordid);
//            GLES20.glDisable(GLES20.GL_CULL_FACE);
//            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
//            GLES20.glViewport(0,0,bitmap.getWidth(),bitmap.getHeight());
//            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
//            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureid);
//            GLES20.glUniform1i(u_samplerTextureid,0);
//        }
//        @Override
//        public void onSurfaceChanged(GL10 gl, int width, int height) {
//
//
//
//        }
//
//        @Override
//        public void onDrawFrame(GL10 gl) {
//
//            GLES20.glUseProgram(program);
//            GLES20.glUniformMatrix4fv(u_MVPMatrixid, 1, false, mvpmatrix, 0);
//            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP,0,4);
//            GLES20.glUseProgram(0);
//        }
//    };


    GLSurfaceView.Renderer renderer = new GLSurfaceView.Renderer() {

        int textureid;
        int program;
        int attposid,a_texturecoordid,u_samplerTextureid,u_MVPMatrixid;
        SurfaceTexture mTexture;
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            int []ids = new int[1];
            objbyteBuffer =ByteBuffer.allocateDirect(objcood.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();

            objbyteBuffer.put(POS_VERTICES).position(0);


            textByteBuffer =ByteBuffer.allocateDirect(textcood.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            textByteBuffer.put(TEX_VERTICES).position(0);

             GLES20.glGenTextures(1,ids,0);
             textureid =ids[0];
//             mTexture = new SurfaceTexture(textureid);
             GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureid);
             GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
             GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_LINEAR);
             GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_CLAMP_TO_EDGE);
             GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_CLAMP_TO_EDGE);

             program = Glutil.createProgram(vert,sFragmentShader);
             GLES20.glUseProgram(program);


            attposid =GLES20.glGetAttribLocation(program,"a_position");
            a_texturecoordid =    GLES20.glGetAttribLocation(program,"a_texturecoord");
            u_samplerTextureid=GLES20.glGetUniformLocation(program,"u_samplerTexture");
            u_MVPMatrixid=GLES20.glGetUniformLocation(program,"u_MVPMatrix");
            Matrix.setIdentityM(mvpmatrix,0);
            Matrix.setIdentityM(mProjMatrix,0);
            Matrix.setIdentityM(mVMatrix,0);
             GLES20.glVertexAttribPointer(attposid,2,GLES20.GL_FLOAT,false,0,objbyteBuffer);
             GLES20.glEnableVertexAttribArray(attposid);

            GLES20.glVertexAttribPointer(a_texturecoordid,2,GLES20.GL_FLOAT,false,0,textByteBuffer);
            GLES20.glEnableVertexAttribArray(a_texturecoordid);


                 }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES20.glViewport(0,0,width,height);


            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D,0,bitmap,0);
            int w=bitmap.getWidth();
            int h=bitmap.getHeight();
            float sWH=w/(float)h;
            float sWidthHeight=width/(float)height;
            if(width>height){
                if(sWH>sWidthHeight){
                    Matrix.orthoM(mProjMatrix, 0, -sWidthHeight*sWH,sWidthHeight*sWH, -1,1, 2, 7);
                }else{
                    Matrix.orthoM(mProjMatrix, 0, -sWidthHeight/sWH,sWidthHeight/sWH, -1,1, 2, 7);
                }
            }else{
                if(sWH>sWidthHeight){
                    Matrix.orthoM(mProjMatrix, 0, -1, 1, -1/sWidthHeight*sWH, 1/sWidthHeight*sWH,2, 7);
                }else{
                    Matrix.orthoM(mProjMatrix, 0, -1, 1, -sWH/sWidthHeight, sWH/sWidthHeight,2, 7);
                }
            }
            //设置相机位置
            Matrix.setLookAtM(mVMatrix, 0, 0, 0, 7.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
            //计算变换矩阵
            Matrix.multiplyMM(mvpmatrix,0,mProjMatrix,0,mVMatrix,0);

//            float ratio = (float) width / height;
//
//// create a projection matrix from device screen geometry
//            Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
//            Matrix.multiplyMM(mvpmatrix, 0, mProjMatrix, 0, mVMatrix, 0);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
//            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
//            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureid);
//            GLES20.glUniform1i(u_samplerTextureid,0);


            GLES20.glUseProgram(program);
            GLES20.glUniformMatrix4fv(u_MVPMatrixid, 1, false, mvpmatrix, 0);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP,0,4);
            GLES20.glUseProgram(0);
        }
    };

}
