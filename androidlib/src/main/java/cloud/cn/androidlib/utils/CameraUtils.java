package cloud.cn.androidlib.utils;

import android.hardware.Camera;

/**
 * Created by john on 2016/5/6.
 */
public class CameraUtils {
    /**
     * 获得摄像头实例，如果camera不可用或者被占用则返回空
     * @param type 1为前置摄像头
     * @return
     */
    public static Camera getCameraInstance(int type){
        Camera c = null;
        try {
            c = Camera.open(type); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }
}
