package cloud.cn.applicationtest.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import cloud.cn.androidlib.utils.PrefUtils;
import cloud.cn.applicationtest.AppConstants;
import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.engine.AToolsEngine;

/**
 * Created by Cloud on 2016/5/11.
 */
public class ShowAddressService extends Service{
    private TelephonyManager tm;
    private PhoneStateListener listener;
    private OutCallReceiver outCallReceiver;
    private WindowManager wm;
    private View tv;
    //{"半透明", "活力橙", "卫士蓝", "金属灰", "苹果绿"}
    private static final int[] bgs = new int[]{R.drawable.call_locate_white, R.drawable.call_locate_orange,
            R.drawable.call_locate_blue, R.drawable.call_locate_gray, R.drawable.call_locate_green};
    int startX;
    int startY;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        listener = new MyPhoneStateListener();
        //监听拨进来的电话
        tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
        outCallReceiver = new OutCallReceiver();
        IntentFilter intentFilter = new IntentFilter("android.intent.action.NEW_OUTGOING_CALL");
        //监听外拨电话,四大组件中只有broadcase receiver可以在代码中动态注册
        registerReceiver(outCallReceiver, intentFilter);
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tm.listen(listener, PhoneStateListener.LISTEN_NONE);
        listener = null;
        unregisterReceiver(outCallReceiver);
    }

    public void showMyToast(String msg) {
        int which = PrefUtils.getInt(AppConstants.PREF.STYLE_WHICH, 0);
        tv = View.inflate(getApplicationContext(), R.layout.window_my_toast, null);
        tv.setBackgroundResource(bgs[which]);
        TextView tv_address = (TextView)tv.findViewById(R.id.tv_address);
        tv_address.setText(msg);
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.TOP | Gravity.LEFT;//需要指定左上角，x,y坐标才是相对于左上角的
        params.x = PrefUtils.getInt(AppConstants.PREF.LAST_X, 0);
        params.y = PrefUtils.getInt(AppConstants.PREF.LAST_Y, 0);
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE //不可获取焦点
                //| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE  //不可触摸
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON; //保持屏幕点亮
        params.format = PixelFormat.TRANSLUCENT; //支持半透明
        //params.type = WindowManager.LayoutParams.TYPE_TOAST; //类型为吐司,不响应点击事件
        //可以滑动的窗体类型,需要权限SYSTEM_ALERT_WINDOW,如果是6.0以上版本，在app store安装的版本可以用，否则还要去设置-应用中手动开启
        if (Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(getApplication())) {
            params.type = WindowManager.LayoutParams.TYPE_TOAST;
        } else {
            params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            tv.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch(event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            startX = (int)event.getRawX();
                            startY = (int)event.getRawY();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            int dx = (int)event.getRawX() - startX;
                            int dy = (int)event.getRawY() - startY;
                            startX = (int)event.getRawX();
                            startY = (int)event.getRawY();
                            params.x += dx;
                            params.y += dy;
                            wm.updateViewLayout(tv, params);//更新布局
                            break;
                        case MotionEvent.ACTION_UP:
                            break;
                    }
                    return true;
                }
            });
        }
        wm.addView(tv, params);
    }

    class MyPhoneStateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                //空闲状态
                case TelephonyManager.CALL_STATE_IDLE:
                    if(tv != null) {
                        wm.removeView(tv);
                        tv = null;
                    }
                    break;
                //响铃状态
                case TelephonyManager.CALL_STATE_RINGING:
                    String address = AToolsEngine.getLocation(incomingNumber);
                    showMyToast(address);
                    break;
                //接听状态
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
            }
        }
    }

    class OutCallReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String number = getResultData(); //获取外拨电话号码
            String address = AToolsEngine.getLocation(number);
            showMyToast(address);
        }
    }
}
