package cloud.cn.applicationtest;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Cloud on 2016/3/22.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);
    }
}
