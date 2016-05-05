package cloud.cn.androidlib.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.xutils.x;

import java.util.Map;

/**
 * Created by Cloud on 2016/3/30.
 */
public class PrefUtils {
    private static String PREF_NAME = "config";

    public static boolean getBoolean(String key, boolean defValue) {
        SharedPreferences sharedPreferences = x.app().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defValue);
    }

    public static float getFloat(String key, float defValue) {
        SharedPreferences sharedPreferences = x.app().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(key, defValue);
    }

    public static int getInt(String key, int defValue) {
        SharedPreferences sharedPreferences = x.app().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defValue);
    }

    public static long getLong(String key, long defValue) {
        SharedPreferences sharedPreferences = x.app().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, defValue);
    }

    public static String getString(String key, String defValue) {
        SharedPreferences sharedPreferences = x.app().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defValue);
    }

    public static Map<String, ?> getAll() {
        SharedPreferences sharedPreferences = x.app().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getAll();
    }

    public static void putBoolean(String key, boolean value) {
        SharedPreferences sharedPreferences = x.app().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    public static void putFloat(String key, float value) {
        SharedPreferences sharedPreferences = x.app().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putFloat(key, value).commit();
    }

    public static void putInt(String key, int value) {
        SharedPreferences sharedPreferences = x.app().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(key, value).commit();
    }

    public static void putLong(String key, long value) {
        SharedPreferences sharedPreferences = x.app().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putLong(key, value).commit();
    }

    public static void putString(String key, String value) {
        SharedPreferences sharedPreferences = x.app().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).commit();
    }

    public static void clear() {
        SharedPreferences sharedPreferences = x.app().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }

    public static void remove(String key) {
        SharedPreferences sharedPreferences = x.app().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(key).commit();
    }
}
