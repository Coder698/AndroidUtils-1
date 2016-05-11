package cloud.cn.applicationtest.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cloud.cn.androidlib.activity.BaseActivity;
import cloud.cn.androidlib.utils.PrefUtils;
import cloud.cn.androidlib.utils.ServiceUtils;
import cloud.cn.applicationtest.AppConstants;
import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.service.ShowAddressService;
import cloud.cn.applicationtest.ui.SettingItemView;

/**
 * Created by john on 2016/5/11.
 */
@ContentView(R.layout.activity_home_setting)
public class SettingActivity extends BaseActivity{
    @ViewInject(R.id.siv_auto_update)
    private SettingItemView siv_auto_update;
    @ViewInject(R.id.siv_show_address)
    private SettingItemView siv_show_address;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        //自动更新设置
        boolean isAutoUpdate = PrefUtils.getBoolean(AppConstants.PREF.IS_AUTO_UPDATE, true);
        siv_auto_update.setChecked(isAutoUpdate);
        siv_auto_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = !siv_auto_update.isChecked();
                siv_auto_update.setChecked(checked);
                PrefUtils.putBoolean(AppConstants.PREF.IS_AUTO_UPDATE, checked);
            }
        });
        //归属地设置
        siv_show_address.setChecked(ServiceUtils.isServiceRunning(ShowAddressService.class.getName()));
        siv_show_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = !siv_show_address.isChecked();
                siv_show_address.setChecked(checked);
                Intent intent = new Intent(getApplicationContext(), ShowAddressService.class);
                if(checked) {
                    startService(intent);
                } else {
                    stopService(intent);
                }
            }
        });
    }

    @Override
    protected void loadData() {

    }
}
