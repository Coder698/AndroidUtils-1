package cloud.cn.applicationtest.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cloud.cn.applicationtest.R;

/**
 * Created by Cloud on 2016/5/12.
 */
public class SettingClickView extends RelativeLayout{
    @ViewInject(R.id.tv_setting_title)
    private TextView tv_setting_title;
    @ViewInject(R.id.tv_setting_desc)
    private TextView tv_setting_desc;
    private final String NAMESPACE = "http://schemas.android.com/apk/res-auto";

    public SettingClickView(Context context) {
        super(context);
        initView();
    }

    public SettingClickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public void setTitle(String title) {
        tv_setting_title.setText(title);
    }

    public void setDesc(String desc) {
        tv_setting_desc.setText(desc);
    }

    private void initView() {
        initView(null);
    }

    private void initView(AttributeSet attrs) {
        //填充布局添加到该控件根节点
        View child = View.inflate(getContext(), R.layout.item_setting_clickview, this);
        x.view().inject(this, child);
        if(attrs != null) {
            String title = attrs.getAttributeValue(NAMESPACE, "title");
            String desc = attrs.getAttributeValue(NAMESPACE, "desc");
            setTitle(title);
            setDesc(desc);
        }
    }
}
