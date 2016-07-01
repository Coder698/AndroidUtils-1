package cloud.cn.applicationtest.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.xutils.x;

import cloud.cn.androidlib.utils.UiUtils;
import cloud.cn.applicationtest.AppConstants;
import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.entity.AppInfo;

/**
 * Created by Cloud on 2016/6/30.
 */
public class HomeHolder extends BaseHolder<AppInfo>{
    private TextView tv_name;
    private RatingBar rb_ratingBar;
    private TextView tv_size;
    private TextView tv_desc;
    private ImageView iv_icon;

    @Override
    public View initView() {
        View view = UiUtils.inflate(R.layout.item_home_list);
        iv_icon = UiUtils.findViewById(view, R.id.iv_icon);
        tv_name = UiUtils.findViewById(view, R.id.tv_name);
        rb_ratingBar = UiUtils.findViewById(view, R.id.rb_ratingBar);
        tv_size = UiUtils.findViewById(view, R.id.tv_size);
        tv_desc = UiUtils.findViewById(view, R.id.tv_desc);
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        tv_name.setText(data.getName());
        rb_ratingBar.setRating(data.getStars());
        tv_size.setText(Formatter.formatFileSize(x.app(), data.getSize()));
        tv_desc.setText(data.getDes());
        x.image().bind(iv_icon, AppConstants.MOBILE_API.APP_ORIGIN + data.getIconUrl());
    }
}
