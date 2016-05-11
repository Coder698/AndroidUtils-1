package cloud.cn.androidlib.utils;

import android.app.ActivityManager;
import android.content.Context;

import org.xutils.x;

import java.util.List;

/**
 * Created by Cloud on 2016/5/11.
 */
public class ServiceUtils {
    /**
     * 查询service是否运行
     * @param serviceName service的全限定名字
     * @return
     */
    public static boolean isServiceRunning(String serviceName) {
        ActivityManager am = (ActivityManager) x.app().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> infos = am.getRunningServices(100);
        for(ActivityManager.RunningServiceInfo info : infos) {
            if(info.service.getClassName().equals(serviceName)) {
                return true;
            }
        }
        return false;
    }
}
