package cloud.cn.androidlib;

import android.app.Application;

import org.xutils.x;

import cloud.cn.androidlib.crash.DefaultCrashHandler;

/**
 * Created by Cloud on 2016/3/28.
 */
public class AppApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        DefaultCrashHandler.getInstance().init(this);
    }
}
