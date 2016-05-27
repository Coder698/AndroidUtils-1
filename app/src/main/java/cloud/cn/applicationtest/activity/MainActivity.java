package cloud.cn.applicationtest.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
    private LeftMenuFragment leftMenuFragment;
    private SlidingMenu slidingMenu;

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
        initMenuFragment();
        initSlidingMenu();
    }

    public LeftMenuFragment getLeftMenuFragment() {
        return leftMenuFragment;
    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_main_content, fragment);
        fragmentTransaction.commit();
    }

    private void initMenuFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        leftMenuFragment = new LeftMenuFragment();
        fragmentTransaction.replace(R.id.fl_menu, leftMenuFragment);
        fragmentTransaction.commit();
    }

    private void initSlidingMenu() {
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT); //侧边栏从哪边出
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//从哪边可以拖动，fullscreen表示全屏都可以拖动,margin表示一定区域内可拖动
        slidingMenu.setBehindOffset(200);//菜单弹出后内容显示多少
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.menu_sliding_layout);
    }

    public void toggleSlidingMenu() {
        slidingMenu.toggle();
    }

    @Override
    protected void loadData() {
        if(PrefUtils.getBoolean(AppConstants.PREF.IS_AUTO_UPDATE, true)) {
            UpgradeEngine.checkUpgrade(this);
        }
    }
}
