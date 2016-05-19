package cloud.cn.androidlib.utils;

/**
 * Created by Cloud on 2016/5/19.
 */

import android.app.Activity;
import android.content.Intent;
import android.telephony.SmsManager;

/**
 * 电话相关服务，如短信，电话等
 */
public class PhoneUtils {
    /**
     * 发送短信到指定号码，需要android.permission.SEND_SMS权限
     * @param phoneNum
     * @param msg
     */
    public static void sendSms(String phoneNum, String msg) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNum, null, msg, null, null);
    }

    /**
     * 使用默认短信应用发送指定短信
     * @param activity
     * @param msg
     */
    public static void sendSms(Activity activity, String msg) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        activity.startActivity(intent);
    }
}
