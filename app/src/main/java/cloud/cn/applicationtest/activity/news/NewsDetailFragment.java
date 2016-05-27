package cloud.cn.applicationtest.activity.news;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cloud.cn.androidlib.activity.BaseFragment;
import cloud.cn.androidlib.interfaces.SuccessFailCallback;
import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.adapter.NewsAdapter;
import cloud.cn.applicationtest.adapter.TopNewsAdapter;
import cloud.cn.applicationtest.engine.NewsEngine;
import cloud.cn.applicationtest.entity.NewsDetail;
import cloud.cn.applicationtest.ui.RefreshListView;

/**
 * Created by Cloud on 2016/5/26.
 */
@ContentView(R.layout.pager_detail)
public class NewsDetailFragment extends BaseFragment{
    private String url;
    private String title;
    private ViewPager vp_top_news;
    @ViewInject(R.id.lv_news)
    private RefreshListView lv_news;
    private TextView tv_top_news_title;
    private CirclePageIndicator cpi_indicator;
    private View headView;
    private TopNewsAdapter topNewsAdapter;
    private NewsAdapter newsAdapter;
    private NewsDetail newsDetail;

    @Override
    protected void initVariables() {
        url = getArguments().getString("url");
        title = getArguments().getString("title");
        topNewsAdapter = new TopNewsAdapter(null);
        newsAdapter = new NewsAdapter(null);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initHeadView();
        lv_news.addHeaderView(headView);
        vp_top_news.setAdapter(topNewsAdapter);
        lv_news.setAdapter(newsAdapter);
        cpi_indicator.setViewPager(vp_top_news);
        cpi_indicator.setSnap(false);
        cpi_indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_top_news_title.setText(newsDetail.getTopnews().get(position).getTitle());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initHeadView() {
        headView = View.inflate(getContext(), R.layout.header_news, null);
        vp_top_news = (ViewPager)headView.findViewById(R.id.vp_top_news);
        tv_top_news_title = (TextView)headView.findViewById(R.id.tv_top_news_title);
        cpi_indicator = (CirclePageIndicator)headView.findViewById(R.id.cpi_indicator);
    }

    @Override
    protected void loadData() {
        NewsEngine.getDetail(url, new SuccessFailCallback<NewsDetail>() {
            @Override
            public void onSuccess(NewsDetail result) {
                newsDetail = result;
                topNewsAdapter.setDataSource(newsDetail.getTopnews());
                //cpi_indicator.notifyDataSetChanged();
                tv_top_news_title.setText(newsDetail.getTopnews().get(0).getTitle());
                newsAdapter.setDataSource(newsDetail.getNews());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }
        });
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
