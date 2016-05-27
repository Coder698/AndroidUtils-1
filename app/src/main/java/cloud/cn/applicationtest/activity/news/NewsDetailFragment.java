package cloud.cn.applicationtest.activity.news;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ListView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cloud.cn.androidlib.activity.BaseFragment;
import cloud.cn.androidlib.interfaces.SuccessFailCallback;
import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.adapter.TopNewsAdapter;
import cloud.cn.applicationtest.engine.NewsEngine;
import cloud.cn.applicationtest.entity.NewsDetail;

/**
 * Created by Cloud on 2016/5/26.
 */
@ContentView(R.layout.pager_detail)
public class NewsDetailFragment extends BaseFragment{
    private String url;
    private String title;
    @ViewInject(R.id.vp_top_news)
    private ViewPager vp_top_news;
    @ViewInject(R.id.lv_news)
    private ListView lv_news;
    private TopNewsAdapter topNewsAdapter;
    private List<NewsDetail.News> topNews;

    @Override
    protected void initVariables() {
        url = getArguments().getString("url");
        title = getArguments().getString("title");
        topNews = new ArrayList<>();
        topNewsAdapter = new TopNewsAdapter(topNews);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        vp_top_news.setAdapter(topNewsAdapter);
    }

    @Override
    protected void loadData() {
        NewsEngine.getDetail(url, new SuccessFailCallback<NewsDetail>() {
            @Override
            public void onSuccess(NewsDetail result) {
                topNews.clear();
                topNews.addAll(result.getTopnews());
                topNewsAdapter.notifyDataSetChanged();
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
