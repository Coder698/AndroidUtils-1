package cloud.cn.applicationtest.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Cloud on 2016/4/5.
 */
public class NewsPagerAdapter extends PagerAdapter{
    private Context context;
    private String[] titles = {"国内", "国际", "体育", "军事","国内", "国际", "体育", "军事"};

    public NewsPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TextView view = new TextView(context);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setText(titles[position]);
        view.setTextSize(20);
        view.setTextColor(context.getResources().getColor(android.R.color.black));
        view.setLayoutParams(layoutParams);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
