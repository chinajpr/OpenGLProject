package com.xiaomi.openglproject.utils;

import android.os.Build;
import android.os.HandlerThread;
import android.util.Size;

import androidx.annotation.RequiresApi;
import androidx.camera.core.CameraX;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.lifecycle.LifecycleOwner;

/**
 * 类描述：
 * 作者：jiaopeirong on 2020/6/26
 * 邮箱：jiaopeirong@xiaomi.com
 */
public class CameraHelper {
    private CameraX.LensFacing currentFacing = CameraX.LensFacing.BACK;
    private Preview.OnPreviewOutputUpdateListener listener;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CameraHelper(LifecycleOwner lifecycleOwner, Preview.OnPreviewOutputUpdateListener listener) {
        this.listener = listener;
        CameraX.bindToLifecycle(lifecycleOwner, getPreView());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private Preview getPreView() {
        // 分辨率并不是最终的分辨率，CameraX会自动根据设备的支持情况，结合你的参数，设置一个最为接近的分辨率
        PreviewConfig previewConfig = new PreviewConfig.Builder()
                .setTargetResolution(new Size(640, 480))
                .setLensFacing(currentFacing) //前置或者后置摄像头
                .build();
        Preview preview = new Preview(previewConfig);
        preview.setOnPreviewOutputUpdateListener(listener);
        return preview;
    }
}
