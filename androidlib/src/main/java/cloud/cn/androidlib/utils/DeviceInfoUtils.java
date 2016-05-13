package cloud.cn.androidlib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import org.xutils.common.util.LogUtil;
import org.xutils.x;

public class DeviceInfoUtils {
  /*
   * 获取屏幕高度,不包括虚拟键盘，包括状态栏
   */
  public static int getScreenHeight() {
    WindowManager wm = (WindowManager) x.app()
            .getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics dm = new DisplayMetrics();
    int height = wm.getDefaultDisplay().getHeight();
    return height;
  }

  /*
   * 获取屏幕宽度
   */
  public static int getScreenWidth() {
    WindowManager wm = (WindowManager) x.app()
            .getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics dm = new DisplayMetrics();
    wm.getDefaultDisplay().getMetrics(dm);
    int width = dm.widthPixels;
    return width;
  }

  /**
   * 获取activity显示区域,包括虚拟键盘，不包括状态栏,需要activity渲染完成才能取到正确的值
   * @param context
   * @return
     */
  public static Rect getDisplayRect(Activity context) {
    Rect frame = new Rect();
    context.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
    return frame;
  }

  /**
   * @return 1:Android Phone; 2:Android Tablet;
   */
  public static int getDeviceType() {
    boolean isTable =
        (x.app().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
            >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    LogUtil.i("当前设备是" + (isTable ? "平板" : "手机"));
    return isTable ? 2 : 1;
  }

  /**
   * 是否为手机
   * @return
   */
  public static boolean isPhone() {
    boolean isTable =
        (x.app().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
            >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    return !isTable;
  }

  /**
   * 获取当前应用版本名称
   * @return
   */
  public static String getVersionName() {
    return getPackageInfo().versionName;
  }

  //获取当前应用版本号
  public static int getVersionCode() {
    return getPackageInfo().versionCode;
  }

  private static PackageInfo getPackageInfo() {
    PackageManager packageManager = x.app().getPackageManager();
    PackageInfo packageInfo = null;
    try {
      packageInfo = packageManager.getPackageInfo(x.app().getPackageName(), 0);
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return packageInfo;
  }

}
