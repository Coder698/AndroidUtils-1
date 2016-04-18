package cloud.cn.androidlib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;

import org.xutils.common.util.LogUtil;
import org.xutils.x;

public class DeviceInfoUtils {
  /*
   * 获取屏幕高度
   */
  public static int getScreenHeight(Activity context) {
    DisplayMetrics dm = new DisplayMetrics();
    if (Build.VERSION.SDK_INT < 17) {
      (context).getWindowManager().getDefaultDisplay().getMetrics(dm);
    } else {
      (context).getWindowManager().getDefaultDisplay().getRealMetrics(dm);
    }
    int height = dm.heightPixels;
    return height;
  }

  /*
   * 获取屏幕宽度
   */
  public static int getScreenWidth(Activity context) {
    DisplayMetrics dm = new DisplayMetrics();
    if (Build.VERSION.SDK_INT < 17) {
      //NEXUS 5 DisplayMetrics{density=3.0, width=1080, height=1776, scaledDensity=3.0, xdpi=442.451, ydpi=443.345}
      (context).getWindowManager().getDefaultDisplay().getMetrics(dm);
    } else {
      //NEXUS 5 DisplayMetrics{density=3.0, width=1080, height=1920, scaledDensity=3.0, xdpi=442.451, ydpi=443.345}
      (context).getWindowManager().getDefaultDisplay().getRealMetrics(dm);
    }
    int width = dm.widthPixels;
    return width;
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
