package cloud.cn.applicationtest.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import cloud.cn.androidlib.activity.BaseActivity;
import cloud.cn.androidlib.engine.ContactEngine;
import cloud.cn.androidlib.entity.ContactBean;
import cloud.cn.androidlib.net.SuccessCallback;
import cloud.cn.applicationtest.AppConstants;
import cloud.cn.applicationtest.R;

/**
 * Created by Cloud on 2016/4/21.
 */
@ContentView(R.layout.activity_save)
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

    @Event(R.id.btn_save_contact)
    private void saveBtnClicked(View view) {
        Intent intent = new Intent(this, ContactListActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ContactBean contactBean = (ContactBean)data.getSerializableExtra(AppConstants.EXTRA_KEY.CONTACT);
        LogUtil.d(contactBean.toString());
    }
}
