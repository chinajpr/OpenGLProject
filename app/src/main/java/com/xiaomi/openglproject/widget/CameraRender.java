package com.xiaomi.openglproject.widget;

import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.camera.core.Preview;
import androidx.lifecycle.LifecycleOwner;

import com.xiaomi.openglproject.filter.ScreenFilter;
import com.xiaomi.openglproject.utils.CameraHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 类描述：
 * 作者：jiaopeirong on 2020/6/26
 * 邮箱：jiaopeirong@xiaomi.com
 */
public class CameraRender implements GLSurfaceView.Renderer, Preview.OnPreviewOutputUpdateListener, SurfaceTexture.OnFrameAvailableListener {
    private CameraView cameraView;
    private CameraHelper cameraHelper;
    private int[] textures;
    //摄像头的图像
    private SurfaceTexture mCameraTexture;
    private ScreenFilter screenFilter;

    float[] mtx = new float[16];

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CameraRender(CameraView cameraView) {
        this.cameraView = cameraView;
        LifecycleOwner lifecycleOwner = (LifecycleOwner) cameraView.getContext();
        cameraHelper = new CameraHelper(lifecycleOwner , this);
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        cameraView.requestRender();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        textures = new int[1];
        mCameraTexture.attachToGLContext(textures[0]);
        //当摄像头的数据有更新回调  onFrameAvailable
        mCameraTexture.setOnFrameAvailableListener(this);

        screenFilter = new ScreenFilter(cameraView.getContext());
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        screenFilter.setSize(width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //更新纹理
        mCameraTexture.updateTexImage();

        mCameraTexture.getTransformMatrix(mtx);

        screenFilter.setTransformMatrix(mtx);
        screenFilter.onDraw(textures[0]);
    }

    @Override
    public void onUpdated(Preview.PreviewOutput output) {
        mCameraTexture = output.getSurfaceTexture();
    }

    public void onDestroy(){

    }
}
