package cloud.cn.applicationtest;

/**
 * Created by Cloud on 2016/3/30.
 */
public final class AppConstants {
    public final static class PREF {
        public static String GUIDE_DISPLAY = "GUIDE_DISPLAY";
    }
    public final static class MOBILE_API {
        public final static String ORIGIN = "http://192.168.40.116:8080/guard";
        public final static String UPGRADE_INFO = ORIGIN + "/apk.json";
    }
    public final static class DATA_SAVE_PATH {
        public final static String APK_NAME = "AndroidUtils.apk";
    }
}
