package cloud.cn.applicationtest.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cloud.cn.androidlib.activity.BaseActivity;
import cloud.cn.androidlib.utils.PrefUtils;
import cloud.cn.applicationtest.AppConstants;
import cloud.cn.applicationtest.R;

/**
 * Created by Cloud on 2016/4/21.
 */
@ContentView(R.layout.activity_safe)
public class SafeActivity extends BaseActivity{
    @ViewInject(R.id.tv_safe_phone_num)
    private TextView tv_safe_phone_num;
    @ViewInject(R.id.iv_safe_lock)
    private ImageView iv_safe_lock;

    @Override
    protected void initVariables() {
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        if(!PrefUtils.getBoolean(AppConstants.PREF.IS_SAFE_GUIDE_FINISHED, false)) {
            //设置向导未完成
            Intent intent = new Intent(this, SafeSetup1Activity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        tv_safe_phone_num.setText(PrefUtils.getString(AppConstants.PREF.SAFE_PHONE_NUM, ""));
        if(PrefUtils.getBoolean(AppConstants.PREF.IS_SAFE_OPEN, false)) {
            iv_safe_lock.setImageResource(R.drawable.lock);
        } else {
            iv_safe_lock.setImageResource(R.drawable.unlock);
        }
    }

    @Event(R.id.tv_safe_enter_guide)
    private void enterSafeGuide(View view) {
        Intent intent = new Intent(this, SafeSetup1Activity.class);
        startActivity(intent);
    }

    @Override
    protected void loadData() {
    }
}
