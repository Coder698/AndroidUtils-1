package cloud.cn.applicationtest.receiver;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Build;
import android.telephony.SmsMessage;

import org.xutils.common.util.LogUtil;

import cloud.cn.androidlib.utils.PrefUtils;
import cloud.cn.applicationtest.AppConstants;
import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.service.LocationService;

/**
 * Created by john on 2016/5/6.
 */
public class SmsReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[])intent.getExtras().get("pdus");
        String format = intent.getStringExtra("format");
        for(Object pdu : pdus) {
            SmsMessage smsMessage;
            if(Build.VERSION.SDK_INT < 23) {
                smsMessage = SmsMessage.createFromPdu((byte[])pdu);
            } else {
                smsMessage = SmsMessage.createFromPdu((byte[])pdu, format);
            }
            String sender = smsMessage.getOriginatingAddress();
            //如果不是来自安全号码则不做处理
            /*if(!sender.equals(PrefUtils.getString(AppConstants.PREF.SAFE_PHONE_NUM, ""))) {
                return;
            }*/
            String body = smsMessage.getMessageBody();
            if("#*location*#".equals(body)) {
                LogUtil.d("location");
                intent = new Intent(context, LocationService.class);
                context.startService(intent);
                abortBroadcast();
            } else if("#*alarm*#".equals(body)) {
                LogUtil.d("alarm");
                MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.ylzs);
                mediaPlayer.setVolume(1.0f, 1.0f);
                mediaPlayer.start();
                abortBroadcast();
            } else if("#*lockscreen*#".equals(body)) {
                LogUtil.d("lockscreen");
                //锁屏，需要管理员权限
                DevicePolicyManager dpm = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
                dpm.lockNow();
                abortBroadcast();
            } else if("#*wipedata*#".equals(body)) {
                abortBroadcast();
            }
        }
    }
}
