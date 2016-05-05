package cloud.cn.applicationtest.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

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
@ContentView(R.layout.activity_safe_setup4)
public class SafeSetup4Activity extends BaseActivity{
    @ViewInject(R.id.ck_safe_open)
    private CheckBox ck_safe_open;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setSafeOpenState(PrefUtils.getBoolean(AppConstants.PREF.IS_SAFE_OPEN, false));
        ck_safe_open.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setSafeOpenState(isChecked);
                PrefUtils.putBoolean(AppConstants.PREF.IS_SAFE_OPEN, isChecked);
            }
        });
    }

    private void setSafeOpenState(boolean isOpen) {
        ck_safe_open.setChecked(isOpen);
        if(isOpen) {
            ck_safe_open.setText("防盗保护已经开启");
        } else {
            ck_safe_open.setText("防盗保护已经关闭");
        }
    }

    @Override
    protected void loadData() {

    }

    private void next(View view) {
    }

    @Event(R.id.safe_setup_previous_btn)
    private void previous(View view) {
        Intent intent = new Intent(this, SafeSetup3Activity.class);
        startActivity(intent);
    }
}
