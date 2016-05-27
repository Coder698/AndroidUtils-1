package cloud.cn.applicationtest;

/**
 * Created by Cloud on 2016/3/30.
 */
public final class AppConstants {
    public final static class PREF {
        public static String GUIDE_DISPLAY = "GUIDE_DISPLAY";
        public static String SAFE_PASSWORD = "SAFE_PASSWORD";
        public static String SIM_SERIA_NUM = "SIM_SERIA_NUM";
        public static String SAFE_PHONE_NUM = "SAFE_PHONE_NUM";
        public static String IS_SAFE_OPEN = "IS_SAFE_OPEN";
        public static String IS_SAFE_GUIDE_FINISHED = "IS_SAFE_GUIDE_FINISHED";
        public static String IS_AUTO_UPDATE = "IS_AUTO_UPDATE";
        public static String STYLE_WHICH = "STYLE_WHICH";
        public static String LAST_X = "LAST_X";
        public static String LAST_Y = "LAST_Y";
        public static String IS_SHOW_ADDRESS = "IS_SHOW_ADDRESS";
    }
    public final static class MOBILE_API {
        public final static String ORIGIN = "http://192.168.40.132:8080/guard";
        public final static String UPGRADE_INFO = ORIGIN + "/apk.json";
        public final static String Z_ORIGIN = "http://192.168.40.132:8080/zhbj";
        public final static String CATEGORIES = Z_ORIGIN + "/categories.json";
    }
    public final static class EXTRA_KEY {
        public final static String CONTACT = "CONTACT";
    }
    public final static class DATA_SAVE_PATH {
        public final static String APK_NAME = "AndroidUtils.apk";
    }
}
