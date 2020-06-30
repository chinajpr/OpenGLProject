package com.xiaomi.openglproject.widget;

import android.content.Context;
import android.graphics.Camera;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

import androidx.annotation.RequiresApi;

/**
 * 类描述：
 * 作者：jiaopeirong on 2020/6/26
 * 邮箱：jiaopeirong@xiaomi.com
 */
public class CameraView extends GLSurfaceView {
    private CameraRender render;

    public CameraView(Context context) {
        this(context , null);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //使用OpenGL ES 2.0
        setEGLContextClientVersion(2);
        //设置渲染回调接口
        render = new CameraRender(this);
        setRenderer(render);
        //设置为手动刷新
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        super.surfaceDestroyed(holder);
        render.onDestroy();
    }
}
