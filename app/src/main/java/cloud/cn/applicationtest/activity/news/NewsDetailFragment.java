package cloud.cn.applicationtest.activity.news;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cloud.cn.androidlib.activity.BaseFragment;
import cloud.cn.androidlib.interfaces.SuccessFailCallback;
import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.adapter.NewsAdapter;
import cloud.cn.applicationtest.adapter.TopNewsAdapter;
import cloud.cn.applicationtest.engine.NewsEngine;
import cloud.cn.applicationtest.entity.NewsDetail;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by Cloud on 2016/5/26.
 */
@ContentView(R.layout.pager_detail)
public class NewsDetailFragment extends BaseFragment{
    private String url;
    private String title;
    private ViewPager vp_top_news;
    @ViewInject(R.id.lv_news)
    private ListView lv_news;
    @ViewInject(R.id.rotate_header_list_view_frame)
    private PtrClassicFrameLayout rotate_header_list_view_frame;
    @ViewInject(R.id.load_more_list_view_container)
    private LoadMoreListViewContainer load_more_list_view_container;
    private TextView tv_top_news_title;
    private CirclePageIndicator cpi_indicator;
    private View headView;
    private TopNewsAdapter topNewsAdapter;
    private NewsAdapter newsAdapter;
    private NewsDetail newsDetail;
    private int page;

    @Override
    protected void initVariables() {
        url = getArguments().getString("url");
        title = getArguments().getString("title");
        topNewsAdapter = new TopNewsAdapter(null);
        newsAdapter = new NewsAdapter(null);
        page = 0;
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
        lv_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //position包含header view
                LogUtil.d("位置" + position);
                NewsDetail.News news = newsDetail.getNews().get(position - lv_news.getHeaderViewsCount());
                Intent intent = new Intent();
                intent.setClass(getActivity(), NewsReaderActivity.class);
                intent.putExtra("url", news.getUrl());
                startActivity(intent);
            }
        });
        rotate_header_list_view_frame.setLastUpdateTimeRelateObject(this);//显示上次更新时间
        rotate_header_list_view_frame.setLoadingMinTime(1000);//最少loading时间
        rotate_header_list_view_frame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, lv_news, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                LogUtil.d("开始刷新");
                refreshData();
            }
        });
        load_more_list_view_container.useDefaultHeader();
        load_more_list_view_container.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                LogUtil.d("加载更多");
                loadMore();
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
        rotate_header_list_view_frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                rotate_header_list_view_frame.autoRefresh(true);
            }
        }, 150);//自动更新，需要使用postDelayed
    }

    private String getNextUrl(String url) {
        int index = url.lastIndexOf("/");
        url = url.substring(0, index + 1) + "list_2.json";
        return url;
    }

    private void refreshData() {
        NewsEngine.getDetail(url, new SuccessFailCallback<NewsDetail>() {
            @Override
            public void onSuccess(NewsDetail result) {
                newsDetail = result;
                topNewsAdapter.setDataSource(newsDetail.getTopnews());
                //cpi_indicator.notifyDataSetChanged();
                tv_top_news_title.setText(newsDetail.getTopnews().get(0).getTitle());
                newsAdapter.setDataSource(newsDetail.getNews());
                rotate_header_list_view_frame.refreshComplete();
                load_more_list_view_container.loadMoreFinish(false, true);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                rotate_header_list_view_frame.refreshComplete();
            }
        });
    }

    private void loadMore() {
        String url = getNextUrl(this.url);
        NewsEngine.getDetail(url, new SuccessFailCallback<NewsDetail>() {
            @Override
            public void onSuccess(NewsDetail result) {
                newsDetail.getNews().addAll(result.getNews());
                newsAdapter.notifyDataSetChanged();
                load_more_list_view_container.loadMoreFinish(false, false);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                load_more_list_view_container.loadMoreFinish(false, false);
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
