package cloud.cn.androidlib.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
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

    /** 把自身从父View中移除 */
    public static void removeSelfFromParent(View view) {
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) parent;
                group.removeView(view);
            }
        }
    }

    /** 请求View树重新布局，用于解决中层View有布局状态而导致上层View状态断裂 */
    public static void requestLayoutParent(View view, boolean isAll) {
        ViewParent parent = view.getParent();
        while (parent != null && parent instanceof View) {
            if (!parent.isLayoutRequested()) {
                parent.requestLayout();
                if (!isAll) {
                    break;
                }
            }
            parent = parent.getParent();
        }
    }

    /** 判断触点是否落在该View上 */
    public static boolean isTouchInView(MotionEvent ev, View v) {
        int[] vLoc = new int[2];
        v.getLocationOnScreen(vLoc);
        float motionX = ev.getRawX();
        float motionY = ev.getRawY();
        return motionX >= vLoc[0] && motionX <= (vLoc[0] + v.getWidth()) && motionY >= vLoc[1] && motionY <= (vLoc[1] + v.getHeight());
    }

    /** FindViewById的泛型封装，减少强转代码 */
    public static <T extends View> T findViewById(View layout, int id) {
        return (T) layout.findViewById(id);
    }
}
