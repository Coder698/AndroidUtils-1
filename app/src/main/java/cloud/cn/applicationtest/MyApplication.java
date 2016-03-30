package cloud.cn.applicationtest;

import android.app.Application;

import org.xutils.x;

import cloud.cn.androidlib.AppApplication;

/**
 * Created by Cloud on 2016/3/22.
 */
public class MyApplication extends AppApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.setDebug(true);
    }
}
