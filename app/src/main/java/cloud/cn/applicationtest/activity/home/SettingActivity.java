package cloud.cn.applicationtest.activity.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cloud.cn.androidlib.activity.BaseActivity;
import cloud.cn.androidlib.utils.DialogUtils;
import cloud.cn.androidlib.utils.PrefUtils;
import cloud.cn.androidlib.utils.SystemInfoUtils;
import cloud.cn.applicationtest.AppConstants;
import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.service.ShowAddressService;
import cloud.cn.applicationtest.view.SettingClickView;
import cloud.cn.applicationtest.view.SettingToggleView;

/**
 * Created by john on 2016/5/11.
 */
@ContentView(R.layout.activity_home_setting)
public class SettingActivity extends BaseActivity{
    @ViewInject(R.id.siv_auto_update)
    private SettingToggleView siv_auto_update;
    @ViewInject(R.id.siv_show_address)
    private SettingToggleView siv_show_address;
    @ViewInject(R.id.scv_address_style)
    private SettingClickView scv_address_style;
    private String[] styleItems = new String[]{"半透明", "活力橙", "卫士蓝", "金属灰", "苹果绿"};
    private int styleWhich;

    @Override
    protected void initVariables() {
        styleWhich = PrefUtils.getInt(AppConstants.PREF.STYLE_WHICH, 0);
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
        siv_show_address.setChecked(SystemInfoUtils.isServiceRunning(ShowAddressService.class.getName()));
        siv_show_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = !siv_show_address.isChecked();
                siv_show_address.setChecked(checked);
                Intent intent = new Intent(getApplicationContext(), ShowAddressService.class);
                PrefUtils.putBoolean(AppConstants.PREF.IS_SHOW_ADDRESS, checked);
                if(checked) {
                    startService(intent);
                    if (Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(getApplication())) {
                        Intent overlayIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                Uri.parse("package:" + getPackageName()));
                        startActivity(overlayIntent);
                    }
                } else {
                    stopService(intent);
                }
            }
        });

        scv_address_style.setDesc(styleItems[styleWhich]);
        scv_address_style.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showSingleChoiceDialog(SettingActivity.this, "选择提示框风格", android.R.drawable.btn_star_big_on,
                        styleItems, styleWhich, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                styleWhich = which;
                                scv_address_style.setDesc(styleItems[styleWhich]);
                                PrefUtils.putInt(AppConstants.PREF.STYLE_WHICH, styleWhich);
                                dialog.dismiss();
                            }
                        }, null);
            }
        });
    }

    @Event(R.id.scv_address_position)
    private void gotoDragView(View view) {
        Intent intent = new Intent(this, DragViewActivity.class);
        startActivity(intent);
    }

    @Override
    protected void loadData() {

    }
}
