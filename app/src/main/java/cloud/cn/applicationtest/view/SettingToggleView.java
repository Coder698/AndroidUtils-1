package cloud.cn.applicationtest.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cloud.cn.applicationtest.R;

/**
 * Created by john on 2016/5/11.
 */
public class SettingToggleView extends RelativeLayout{
    @ViewInject(R.id.tv_setting_title)
    private TextView tv_setting_title;
    @ViewInject(R.id.tv_setting_desc)
    private TextView tv_setting_desc;
    @ViewInject(R.id.cb_setting_checked)
    private CheckBox cb_setting_checked;
    private String title;
    private String desc_on;
    private String desc_off;
    private final String NAMESPACE = "http://schemas.android.com/apk/res-auto";

    public SettingToggleView(Context context) {
        super(context);
        initView();
    }

    //xml定义的view会调用这个，传入attrs
    public SettingToggleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public void setTitle(String title) {
        tv_setting_title.setText(title);
    }

    public void setDesc(String desc) {
        tv_setting_desc.setText(desc);
    }

    public boolean isChecked() {
        return cb_setting_checked.isChecked();
    }

    public void setChecked(boolean checked) {
        cb_setting_checked.setChecked(checked);
        if(checked) {
            if(!TextUtils.isEmpty(desc_on)) {
                tv_setting_desc.setText(desc_on);
            }
        } else {
            if(!TextUtils.isEmpty(desc_off)) {
                tv_setting_desc.setText(desc_off);
            }
        }
    }

    private void initView() {
        initView(null);
    }

    private void initView(AttributeSet attrs) {
        //填充布局添加到该控件根节点
        View child = View.inflate(getContext(), R.layout.item_setting_toggleview, this);
        x.view().inject(this, child);
        if(attrs != null) {
            title = attrs.getAttributeValue(NAMESPACE, "title");
            desc_on = attrs.getAttributeValue(NAMESPACE, "desc_on");
            desc_off = attrs.getAttributeValue(NAMESPACE, "desc_off");
            setTitle(title);
        }
    }
}
