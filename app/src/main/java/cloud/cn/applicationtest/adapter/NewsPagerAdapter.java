package cloud.cn.applicationtest.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import cloud.cn.applicationtest.activity.news.NewsDetailFragment;

/**
 * Created by Cloud on 2016/4/5.
 */
public class NewsPagerAdapter extends FragmentPagerAdapter{
    private List<NewsDetailFragment> fragments;

    public NewsPagerAdapter(FragmentManager fm, List<NewsDetailFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getTitle();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
