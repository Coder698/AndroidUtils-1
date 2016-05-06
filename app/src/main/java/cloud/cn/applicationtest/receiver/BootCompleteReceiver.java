package cloud.cn.applicationtest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import cloud.cn.androidlib.utils.PrefUtils;
import cloud.cn.applicationtest.AppConstants;

/**
 * Created by john on 2016/5/6.
 */
public class BootCompleteReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String bindSim = PrefUtils.getString(AppConstants.PREF.SIM_SERIA_NUM, null);
        if(PrefUtils.getBoolean(AppConstants.PREF.IS_SAFE_OPEN, false) && bindSim != null) {
            //获取当前sim
            TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            String realSim = telephonyManager.getSimSerialNumber() + "abc";
            //如果sim变更则发送报警短信
            if(!bindSim.equals(realSim)) {
                String safeNumber = PrefUtils.getString(AppConstants.PREF.SAFE_PHONE_NUM, "");
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(safeNumber, null, "sim changed", null, null);
            }
        }
    }
}
