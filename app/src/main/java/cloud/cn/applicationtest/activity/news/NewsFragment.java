package cloud.cn.applicationtest.activity.news;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.viewpagerindicator.TabPageIndicator;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cloud.cn.androidlib.activity.BaseFragment;
import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.adapter.NewsPagerAdapter;

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

    @Override
    protected void initVariables() {
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        vp_news.setAdapter(new NewsPagerAdapter(getActivity()));
        tpi_news.setViewPager(vp_news);
        iv_news_next_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage();
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

    }
}
