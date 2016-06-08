package cloud.cn.androidlib.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Toast;

import org.xutils.x;

import cloud.cn.androidlib.AppApplication;

/**
 * Created by Cloud on 2016/6/7.
 */
public class UiUtils {
    /**
     * 获取到字符数组
     *
     * @param tabNames 字符数组的id
     */
    public static String[] getStringArray(int tabNames) {
        return getResource().getStringArray(tabNames);
    }

    /** 获取文字 */
    public static String getString(int resId) {
        return getResource().getString(resId);
    }

    public static Resources getResource() {
        return x.app().getResources();
    }

    public static Context getContext() {
        return x.app();
    }

    /**
     * 把Runnable 方法提交到主线程运行
     *
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable) {
        // 在主线程运行
        if (android.os.Process.myTid() == AppApplication.getMainTid()) {
            runnable.run();
        } else {
            //获取handler
            AppApplication.getHandler().post(runnable);
        }
    }

    public static View inflate(int id) {
        return View.inflate(getContext(), id, null);
    }

    public static Drawable getDrawalbe(int id) {
        return getResource().getDrawable(id);
    }

    public static int getDimens(int homePictureHeight) {
        return (int) getResource().getDimension(homePictureHeight);
    }

    /**
     * 延迟执行 任务
     * @param run   任务
     * @param time  延迟的时间
     */
    public static void postDelayed(Runnable run, int time) {
        AppApplication.getHandler().postDelayed(run, time); // 调用Runable里面的run方法
    }

    /**
     * 取消任务
     * @param auToRunTask
     */
    public static void cancel(Runnable auToRunTask) {
        AppApplication.getHandler().removeCallbacks(auToRunTask);
    }

    /** 对toast的简易封装。线程安全，可以在非UI线程调用。 */
    public static void showToastSafe(final int resId) {
        showToastSafe(getString(resId));
    }

    //判断当前的线程是不是在主线程
    public static boolean isRunInMainThread() {
        return android.os.Process.myTid() == AppApplication.getMainTid();
    }

    /** 对toast的简易封装。线程安全，可以在非UI线程调用。 */
    public static void showToastSafe(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast(str);
            }
        });
    }

    public static void showToast(String str) {
        Toast.makeText(x.app(), str, Toast.LENGTH_LONG).show();
    }
}
