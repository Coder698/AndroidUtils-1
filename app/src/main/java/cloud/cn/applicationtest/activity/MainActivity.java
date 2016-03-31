package cloud.cn.applicationtest.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cloud.cn.androidlib.activity.BaseActivity;
import cloud.cn.applicationtest.R;

/**
 * Created by Cloud on 2016/3/30.
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity{
    @ViewInject(R.id.vp_main)
    private ViewPager vp_main;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initSlidingMenu();
    }

    private void initSlidingMenu() {
        SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.menu_sliding_layout);
    }

    @Override
    protected void loadData() {

    }
}
