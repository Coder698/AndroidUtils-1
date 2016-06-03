package cloud.cn.applicationtest.activity.smartservice;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cloud.cn.androidlib.activity.BaseFragment;
import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.activity.MainActivity;

/**
 * Created by Cloud on 2016/4/5.
 */
@ContentView(R.layout.fragment_smartservice)
public class SmartServiceFragment extends BaseFragment {
    @ViewInject(R.id.dl_drawer)
    private DrawerLayout dl_drawer;
    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab tab = actionBar.newTab().setText("标签1").setTabListener(new MyTabListener());
        actionBar.addTab(tab);
        tab = actionBar.newTab().setText("标签2").setTabListener(new MyTabListener());
        actionBar.addTab(tab);
        tab = actionBar.newTab().setText("标签3").setTabListener(new MyTabListener());
        actionBar.addTab(tab);
        tab = actionBar.newTab().setText("标签4").setTabListener(new MyTabListener());
        actionBar.addTab(tab);
        actionBar.show();
        dl_drawer.openDrawer(Gravity.LEFT);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((MainActivity)getActivity()).getSupportActionBar().hide();
    }

    class MyTabListener implements ActionBar.TabListener {

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

        }
    }
}
