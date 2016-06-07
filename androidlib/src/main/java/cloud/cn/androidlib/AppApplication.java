package cloud.cn.androidlib;

import android.app.Application;
import android.os.Handler;

import org.xutils.x;

import cloud.cn.androidlib.crash.DefaultCrashHandler;

/**
 * Created by Cloud on 2016/3/28.
 */
public class AppApplication extends Application{
    private static int mainTid; //主线程id
    private static Handler handler; //在主线程中的handler

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        DefaultCrashHandler.getInstance().init(this);
        mainTid = android.os.Process.myTid();
        handler = new Handler();
    }

    public static int getMainTid() {
        return mainTid;
    }

    public static Handler getHandler() {
        return handler;
    }
}
