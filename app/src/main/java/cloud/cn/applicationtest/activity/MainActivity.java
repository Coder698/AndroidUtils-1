package cloud.cn.applicationtest.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cloud.cn.androidlib.activity.BaseActivity;
import cloud.cn.androidlib.utils.PrefUtils;
import cloud.cn.applicationtest.AppConstants;
import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.activity.govern.GovernFragment;
import cloud.cn.applicationtest.activity.home.HomeFragment;
import cloud.cn.applicationtest.activity.news.NewsFragment;
import cloud.cn.applicationtest.activity.setting.SettingFragment;
import cloud.cn.applicationtest.activity.smartservice.SmartServiceFragment;
import cloud.cn.applicationtest.engine.UpgradeEngine;

/**
 * Created by Cloud on 2016/3/30.
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    private SparseArray<Fragment> fragments;
    @ViewInject(R.id.fl_main_content)
    private FrameLayout fl_main_content;
    @ViewInject(R.id.rg_main_navigation)
    private RadioGroup rg_main_navigation;
    @ViewInject(R.id.rb_main_home)
    private RadioButton rb_main_home;

    @Override
    protected void initVariables() {
        fragments = new SparseArray<>();
        fragments.put(R.id.rb_main_home, new HomeFragment());
        fragments.put(R.id.rb_main_news, new NewsFragment());
        fragments.put(R.id.rb_main_smartservice, new SmartServiceFragment());
        fragments.put(R.id.rb_main_govern, new GovernFragment());
        fragments.put(R.id.rb_main_setting, new SettingFragment());
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        rg_main_navigation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                changeFragment(fragments.get(checkedId));
            }
        });
        rb_main_home.setChecked(true);
        //initSlidingMenu();
    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_main_content, fragment);
        fragmentTransaction.commit();
    }

    private void initSlidingMenu() {
        SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setBehindOffset(200);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.menu_sliding_layout);
    }

    @Override
    protected void loadData() {
        if(PrefUtils.getBoolean(AppConstants.PREF.IS_AUTO_UPDATE, true)) {
            UpgradeEngine.checkUpgrade(this);
        }
    }
}
