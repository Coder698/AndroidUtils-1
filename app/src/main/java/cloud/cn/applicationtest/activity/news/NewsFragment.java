package cloud.cn.applicationtest.activity.news;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.viewpagerindicator.TabPageIndicator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cloud.cn.androidlib.activity.BaseFragment;
import cloud.cn.androidlib.interfaces.SuccessFailCallback;
import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.activity.LeftMenuFragment;
import cloud.cn.applicationtest.activity.MainActivity;
import cloud.cn.applicationtest.adapter.NewsPagerAdapter;
import cloud.cn.applicationtest.engine.NewsEngine;
import cloud.cn.applicationtest.entity.NewsMenuData;
import cloud.cn.applicationtest.entity.TabMessageEvent;

/**
 * Created by Cloud on 2016/4/5.
 */
@ContentView(R.layout.fragment_news)
public class NewsFragment extends BaseFragment {
    @ViewInject(R.id.tpi_news)
    private TabPageIndicator tpi_news;
    @ViewInject(R.id.vp_news)
    private ViewPager vp_news;
    @ViewInject(R.id.iv_news_next_page)
    private ImageView iv_news_next_page;
    @ViewInject(R.id.iv_menu)
    private ImageView iv_menu;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    private NewsPagerAdapter adapter;
    private List<NewsDetailFragment> detailFragments;
    private List<NewsMenuData> menuDatas;

    @Override
    protected void initVariables() {
        detailFragments = new ArrayList<>();
        adapter = new NewsPagerAdapter(getChildFragmentManager(), detailFragments);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        vp_news.setAdapter(adapter);
        tpi_news.setViewPager(vp_news);
        iv_news_next_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage();
            }
        });
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.toggleSlidingMenu();
            }
        });
    }

    private void nextPage() {
        int position = vp_news.getCurrentItem();
        position++;
        vp_news.setCurrentItem(position);
    }

    @Override
    protected void loadData() {
        NewsEngine.getCategories(new SuccessFailCallback<List<NewsMenuData>>() {
            @Override
            public void onSuccess(List<NewsMenuData> result) {
                MainActivity activity = (MainActivity)getActivity();
                LeftMenuFragment fragment = activity.getLeftMenuFragment();
                fragment.setMenuDatas(result);
                menuDatas = result;
                changeSubMenu(0);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }
        });
    }

    private void changeSubMenu(int position) {
        if(position < menuDatas.size()) {
            detailFragments.clear();
            List<NewsDetailFragment> fragments = createDetailFragments(menuDatas.get(position).getChildren());
            detailFragments.addAll(fragments);
            tv_title.setText(menuDatas.get(position).getTitle());
            adapter.notifyDataSetChanged();
            tpi_news.notifyDataSetChanged();
        }
    }

    private List<NewsDetailFragment> createDetailFragments(List<NewsMenuData> menuDatas) {
        List<NewsDetailFragment> fragments = new ArrayList<>();
        for(NewsMenuData menuData : menuDatas) {
            NewsDetailFragment fragment = new NewsDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("url", menuData.getUrl());
            bundle.putString("title", menuData.getTitle());
            //给fragment提供的参数不能通过构造函数传，只能通过setArguments，因为当fragment因旋转或资源不足销毁重建时
            //系统只会调用默认构造函数，且arguments是存在的
            fragment.setArguments(bundle);
            //当fragment一创建就需要用到title,所以需要传
            fragment.setTitle(menuData.getTitle());
            fragments.add(fragment);
        }
        return fragments;
    }

    @Subscribe
    public void onMenuChange(TabMessageEvent event) {
        changeSubMenu(event.getWhat());
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
