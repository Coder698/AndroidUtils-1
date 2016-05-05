package cloud.cn.applicationtest.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cloud.cn.androidlib.activity.BaseActivity;
import cloud.cn.androidlib.entity.ContactBean;
import cloud.cn.applicationtest.AppConstants;
import cloud.cn.applicationtest.R;

/**
 * Created by Cloud on 2016/4/21.
 */
@ContentView(R.layout.activity_safe)
public class SafeActivity extends BaseActivity{
    @ViewInject(R.id.btn_save_contact)
    private Button btn_save_contact;
    @Override
    protected void initVariables() {
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
    }

    @Override
    protected void loadData() {
    }
}
