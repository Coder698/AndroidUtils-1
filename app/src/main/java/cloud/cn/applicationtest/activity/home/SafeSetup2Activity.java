package cloud.cn.applicationtest.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cloud.cn.androidlib.activity.BaseActivity;
import cloud.cn.androidlib.utils.PrefUtils;
import cloud.cn.applicationtest.AppConstants;
import cloud.cn.applicationtest.R;

/**
 * Created by john on 2016/5/5.
 */
@ContentView(R.layout.activity_safe_setup2)
public class SafeSetup2Activity extends BaseActivity{
    @ViewInject(R.id.iv_safe_lock)
    private ImageView iv_safe_lock;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        syncLockState();
    }

    @Override
    protected void loadData() {

    }

    private void syncLockState() {
        if(PrefUtils.getString(AppConstants.PREF.SIM_SERIA_NUM, null) != null) {
            iv_safe_lock.setImageResource(R.drawable.lock);
        } else {
            iv_safe_lock.setImageResource(R.drawable.unlock);
        }
    }

    @Event(R.id.safe_setup_next_btn)
    private void next(View view) {
        Intent intent = new Intent(this, SafeSetup3Activity.class);
        startActivity(intent);
    }

    @Event(R.id.safe_setup_previous_btn)
    private void previous(View view) {
        Intent intent = new Intent(this, SafeSetup1Activity.class);
        startActivity(intent);
    }

    @Event(R.id.rl_safe_bind_sim)
    private void bindSim(View view) {
        String simSeriaNum = PrefUtils.getString(AppConstants.PREF.SIM_SERIA_NUM, null);
        if(simSeriaNum == null) {
            //如果没有绑定则绑定
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            simSeriaNum = telephonyManager.getSimSerialNumber();
            PrefUtils.putString(AppConstants.PREF.SIM_SERIA_NUM, simSeriaNum);
        } else {
            PrefUtils.remove(AppConstants.PREF.SIM_SERIA_NUM);
        }
        syncLockState();
    }
}
