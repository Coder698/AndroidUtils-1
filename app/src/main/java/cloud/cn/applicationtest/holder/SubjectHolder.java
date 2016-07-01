package cloud.cn.applicationtest.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.x;

import cloud.cn.androidlib.utils.UiUtils;
import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.entity.SubjectInfo;

/**
 * Created by Cloud on 2016/7/1.
 */
public class SubjectHolder extends BaseHolder<SubjectInfo>{
    private ImageView iv_pic;
    private TextView tv_des;

    @Override
    public View initView() {
        View view = UiUtils.inflate(R.layout.list_item_subject);
        iv_pic = UiUtils.findViewById(view, R.id.iv_pic);
        tv_des = UiUtils.findViewById(view, R.id.tv_des);
        return view;
    }

    @Override
    public void refreshView(SubjectInfo data) {
        x.image().bind(iv_pic, data.getUrl());
        tv_des.setText(data.getDes());
    }
}
