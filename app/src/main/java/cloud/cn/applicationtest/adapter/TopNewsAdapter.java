package cloud.cn.applicationtest.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.xutils.x;

import java.util.List;

import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.entity.NewsDetail;

/**
 * Created by Cloud on 2016/5/26.
 */
public class TopNewsAdapter extends PagerAdapter{
    List<NewsDetail.News> topNews;

    public TopNewsAdapter(List<NewsDetail.News> topNews) {
        this.topNews = topNews;
    }

    public void setDataSource(List<NewsDetail.News> topNews) {
        this.topNews = topNews;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(topNews == null) {
            return 0;
        } else {
            return topNews.size();
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(x.app());
        x.image().bind(imageView, topNews.get(position).getTopimage());
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
