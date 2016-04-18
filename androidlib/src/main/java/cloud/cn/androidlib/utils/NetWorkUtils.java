package cloud.cn.androidlib.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.xutils.common.util.LogUtil;
import org.xutils.x;

public class NetWorkUtils {
  /**
   * 网络是否可用   可访问网络
   *
   * @return
   */
  public static boolean isNetWorkAvailable() {
    ConnectivityManager cm =
        (ConnectivityManager) x.app().getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = cm.getActiveNetworkInfo();
    return (networkInfo != null && networkInfo.isAvailable());
  }

  public static enum AvailableNetTypeEnum {
    // 当前没有可用的网络
    NO_AVAILABLE,
    // 通过WIFI上网
    WIFI,
    // 通过手机上网
    MOBILE
  }

  /**
   * 当前可用的网络类型
   */
  public static AvailableNetTypeEnum getAvailableNetType() {
    final ConnectivityManager connectivityManager =
        (ConnectivityManager) x.app()
            .getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    AvailableNetTypeEnum availableNetTypeEnum = AvailableNetTypeEnum.NO_AVAILABLE;

    // NetworkInfo: type: WIFI[], state: CONNECTED/CONNECTED, reason:
    // (unspecified), extra: (none), roaming: false, failover: false,
    // isAvailable: true
    final NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();

    do {
      if (null == activeNetInfo) {
        // 当前设备没有激活的网络可用(比如在飞行模式下, 这个 activeNetInfo 就为null
        availableNetTypeEnum = AvailableNetTypeEnum.NO_AVAILABLE;
        break;
      }
      if (!activeNetInfo.isAvailable()) {
        // 当前设备没有激活的网络可用
        break;
      }
      LogUtil.d("当前可用的网络类型 --> " + activeNetInfo.toString());

      // wifi 或者 cmnet等不使用 wap网络(也就是说, 在andorid手机中设置apn的地方,
      // 可以设置当前手机使用CMNET访问网络)
      if ("WIFI".equalsIgnoreCase(activeNetInfo.getTypeName())) {
        availableNetTypeEnum = AvailableNetTypeEnum.WIFI;
      } else {
        availableNetTypeEnum = AvailableNetTypeEnum.MOBILE;
      }
    } while (false);
    return availableNetTypeEnum;
  }

}
