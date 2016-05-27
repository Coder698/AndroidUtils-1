package cloud.cn.applicationtest.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.x;

import java.util.List;

import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.entity.NewsDetail;

/**
 * Created by Cloud on 2016/5/27.
 */
public class NewsAdapter extends BaseAdapter{
    private List<NewsDetail.News> newsList;

    public NewsAdapter(List<NewsDetail.News> newsList) {
        this.newsList = newsList;
    }

    public void setDataSource(List<NewsDetail.News> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(newsList == null) return 0;
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = View.inflate(x.app(), R.layout.item_news_title, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_pic = (ImageView)convertView.findViewById(R.id.iv_pic);
            viewHolder.tv_title = (TextView)convertView.findViewById(R.id.tv_title);
            viewHolder.tv_date = (TextView)convertView.findViewById(R.id.tv_date);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        NewsDetail.News news = newsList.get(position);
        x.image().bind(viewHolder.iv_pic, news.getListimage());
        viewHolder.tv_title.setText(news.getTitle());
        viewHolder.tv_date.setText(news.getPubdate());
        return convertView;
    }

    class ViewHolder {
        ImageView iv_pic;
        TextView tv_title;
        TextView tv_date;
    }
}
