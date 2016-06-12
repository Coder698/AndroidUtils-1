package cloud.cn.applicationtest.activity.smartservice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;

import com.viewpagerindicator.TabPageIndicator;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cloud.cn.androidlib.activity.BaseFragment;
import cloud.cn.androidlib.utils.UiUtils;
import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.activity.MainActivity;

/**
 * Created by Cloud on 2016/4/5.
 */
@ContentView(R.layout.fragment_smartservice)
public class SmartServiceFragment extends BaseFragment {
    @ViewInject(R.id.tpi_tabs)
    private TabPageIndicator tpi_tabs;
    @ViewInject(R.id.vp_content)
    private ViewPager vp_content;
    private MyAdapter adapter;

    @Override
    protected void initVariables() {
        adapter = new MyAdapter(getChildFragmentManager());
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.show();
        vp_content.setAdapter(adapter);
        tpi_tabs.setViewPager(vp_content);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((MainActivity)getActivity()).getSupportActionBar().hide();
    }

    class MyAdapter extends FragmentPagerAdapter {
        private String[] titles;

        public MyAdapter(FragmentManager fm) {
            super(fm);
            titles = UiUtils.getStringArray(R.array.tab_names);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentFactory.createFragment(position);
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }
}
